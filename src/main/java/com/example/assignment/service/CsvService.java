package com.example.assignment.service;

import com.example.assignment.model.Lead;
import com.example.assignment.model.ScoreLead;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {
    private final List<Lead> leads = new ArrayList<>();
    private final List<ScoreLead> scoredLeads = new ArrayList<>();

    public List<Lead> getLeads() { return new ArrayList<>(leads); }
    public List<ScoreLead> getScoredLeads() { return new ArrayList<>(scoredLeads); }

    public void storeLeads(List<Lead> newLeads) {
        leads.clear();
        leads.addAll(newLeads);
    }

    public void storeScored(List<ScoreLead> results) {
        scoredLeads.clear();
        scoredLeads.addAll(results);
    }

    public List<Lead> parseCsv(MultipartFile file) throws IOException, CsvValidationException {
        List<Lead> out = new ArrayList<>();
        try (InputStream is = file.getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             CSVReader reader = new CSVReader(isr)) {

            String[] headers = reader.readNext();
            if (headers == null) throw new IOException("Empty CSV");

            int idxName = -1, idxRole = -1, idxCompany = -1, idxIndustry = -1, idxLocation = -1, idxBio = -1;
            for (int i = 0; i < headers.length; i++) {
                String h = headers[i].trim().toLowerCase();
                if (h.equals("name")) idxName = i;
                if (h.equals("role")) idxRole = i;
                if (h.equals("company")) idxCompany = i;
                if (h.equals("industry")) idxIndustry = i;
                if (h.equals("location")) idxLocation = i;
                if (h.equals("linkedin_bio") || h.equals("linkedinbio") || h.equals("bio")) idxBio = i;
            }

            if (idxName == -1 || idxRole == -1 || idxCompany == -1) {
                throw new IOException("CSV must include headers: name, role, company, industry, location, linkedin_bio");
            }

            String[] line;
            while ((line = reader.readNext()) != null) {
                out.add(new Lead(
                        safe(line, idxName),
                        safe(line, idxRole),
                        safe(line, idxCompany),
                        safe(line, idxIndustry),
                        safe(line, idxLocation),
                        safe(line, idxBio)
                ));
            }
        }
        return out;
    }

    private String safe(String[] arr, int idx) {
        if (idx < 0 || idx >= arr.length) return "";
        return arr[idx] == null ? "" : arr[idx].trim();
    }
}
package com.example.assignment.model;

import java.util.List;

public class Offer {
    private String name;
    private List<String> valueProps;
    private List<String> idealUseCases;

    public Offer() { }

    public Offer(String name, List<String> valueProps, List<String> idealUseCases) {
        this.name = name;
        this.valueProps = valueProps;
        this.idealUseCases = idealUseCases;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getValueProps() { return valueProps; }
    public void setValueProps(List<String> valueProps) { this.valueProps = valueProps; }

    public List<String> getIdealUseCases() { return idealUseCases; }
    public void setIdealUseCases(List<String> idealUseCases) { this.idealUseCases = idealUseCases; }
}
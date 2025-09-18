# 🚀 AI Lead Scoring Assignment

This project is an *AI-powered lead scoring system* designed to help sales and marketing teams prioritize their prospects effectively.

It combines *rule-based scoring* (role relevance, industry match, data completeness) with *AI-driven intent classification* (via OpenAI API) to assign a *final score (0–100)* for each lead.  
The system allows users to:

- 📌 *Define product offers* (value propositions, ideal use cases).
- 📂 *Upload leads in CSV format*.
- 🧮 *Run hybrid scoring* (Rules + AI).
- 📊 *Get ranked results* with reasoning for transparency.

This enables *faster outreach* and ensures teams focus on *high-intent decision-makers*, improving conversion rates.

Built with *Spring Boot, packaged via **Maven, deployed on **Render, and optionally runnable inside **Docker*.

---

## ⚙ Build & Run (Local)

```bash
# Clone the repo
git clone <your-repo-url>
cd <your-repo-name>

# Build project
mvn clean package

# Set your OpenAI API Key
export OPENAI_API_KEY="sk-xxxx"   # replace with your actual key

# Run application
java -jar target/assignment-0.0.1-SNAPSHOT.jar

Set Offer

curl -X POST https://assignment-9w39.onrender.com/offer \
  -H "Content-Type: application/json" \
  -d '{"name":"AI Outreach Automation","valueProps":["24/7 outreach","6x meetings"],"idealUseCases":["SaaS"]}'


Upload Leads

curl -X POST https://assignment-9w39.onrender.com/leads/upload \
  -F "file=@sample-leads.csv"


Run Scoring

curl -X POST https://assignment-9w39.onrender.com/score


Get Results

curl https://assignment-9w39.onrender.com/score/results

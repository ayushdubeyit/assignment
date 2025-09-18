## Lead Scoring Engine (Spring Boot)

## 🚀 Run Locally

### Prerequisites
- Java 21+
- Maven 3+

### Build & Run
```bash
mvn clean package
export OPENAI_API_KEY="sk-xxxx"   # replace with your actual key
java -jar target/assignment-0.0.1-SNAPSHOT.jar

Server will start at: http://localhost:8080


---

📂 API Endpoints

1. Set Offer

POST /offer

curl -X POST http://localhost:8080/offer \
  -H "Content-Type: application/json" \
  -d '{"name":"AI Outreach Automation","valueProps":["24/7 outreach","6x meetings"],"idealUseCases":["SaaS"]}'

2. Upload Leads (CSV)

POST /leads/upload

curl -X POST http://localhost:8080/leads/upload \
  -F "file=@sample-leads.csv"

3. Run Scoring

POST /score

curl -X POST http://localhost:8080/score

4. Get Results

GET /score/results

curl http://localhost:8080/score/results


---

📊 Sample Request & Response

Offer Request

{
  "name": "AI Outreach Automation",
  "valueProps": ["24/7 outreach", "6x meetings"],
  "idealUseCases": ["SaaS"]
}

Scored Lead Response

[
  {
    "name": "Ava Patel",
    "role": "Head of Growth",
    "company": "FlowMetrics",
    "industry": "SaaS",
    "location": "Bengaluru",
    "linkedinBio": "Head of Growth at FlowMetrics, scaling SaaS revenue.",
    "intent": "High",
    "score": 90,
    "reasoning": "RuleScore=40; AI: Matches ICP and role is decision-maker."
  }
]


---

🧮 Scoring Logic

Rule Layer (max 50 points)

Role relevance

Decision-maker → +20

Influencer → +10


Industry match

Exact match → +20

Adjacent → +10


Data completeness

All fields filled → +10



AI Layer (max 50 points)

Intent classification by AI

High → 50 pts

Medium → 30 pts

Low → 10 pts



➡ Final score = Rule layer + AI layer (max 100 points).


---

🤖 AI Prompt

You are a sales assistant. Given the product offer and a prospect's data, classify intent as High, Medium, or Low.
Output STRICT JSON with keys: intent, explanation.

Example:
{"intent":"High","explanation":"Matches ICP (SaaS mid-market) and role is decision-maker."}


---

🐳 Docker (Optional)

Build and run inside Docker:

mvn clean package
docker build -t assignment:latest .
docker run -e OPENAI_API_KEY="sk-xxxx" -p 8080:8080 assignment:latest


---

✅ Tests

Unit tests implemented for RuleScorer using JUnit 5.

Run with:

mvn test


---


### Build & Run
mvn clean package -DskipTests
java -jar target/assignment-0.0.1-SNAPSHOT.jar

Application will start at:
http://localhost:8080
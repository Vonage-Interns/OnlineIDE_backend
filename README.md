
# Online IDE - Backend

This is the backend service for the **Online IDE** project, built with **Spring Boot** and **Gradle**.  
It provides APIs for managing projects, folders, files, and executing code in javascript programming language.


## 🚀 Tech Stack
- **Java 17**
- **Spring Boot**
- **Gradle**
- **PostgreSQL**
- **Docker** (for code execution environments)


## 📦 Prerequisites
Before running the backend, make sure you have:
- [JDK 17](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)
`- [PostgreSQL](https://www.postgresql.org/download/) (for a database)



## Setup Instructions

#### Clone the repository
```bash
git clone https://github.com/Vonage-Interns/OnlineIDE_backend.git
cd OnlineIDE_backend
```

## Configure Database

#### Create a PostgreSQL database
```sql
CREATE DATABASE onlineIDE;
```
#### Update the database connection
Edit src/main/resources/application.properties and update with your credentials:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/onlineIDE
spring.datasource.username=your_username
spring.datasource.password=your_password
```
▶️ Run the backend
```bash
./gradlew bootRun
The backend will start on http://localhost:8081.
```

# Online Voting System
I built this backend service to handle secure election processes, ensuring that users can cast their ballots safely and that administrators can reliably track the results. 
The core focus of this project is security and data integrity. It prevents duplicate voting and uses role-based access control so that only authorized users can vote, and only administrators can view the live tally.

## What it does
* **Secure Voting:** Authenticated users can cast a single vote for their chosen candidate.
* **Duplicate Prevention:** The system checks the database to ensure no user can vote more than once in an election cycle.
* **Live Tallying:** Administrators can retrieve a real-time, counted tally of all votes directly from the database.

## The Technology Stack
I built this project using:
* Java 21
* Spring Boot 3 (Web, Data JPA, Security)
* MySQL 
* Hibernate (for database ORM)
* Maven

---

## How to run it locally

If you want to pull this code down and run it on your own machine, here is how to get it set up.

### 1. Set up your database
First, you will need to have MySQL installed and running. Open your SQL client and run this command to create the database:

### MySQL
CREATE DATABASE voting_db;

### Configure your credentials
Navigate to src/main/resources/ and create a file named application.properties. Paste the following text into it, making sure to replace YOUR_PASSWORD with your actual MySQL root password

* spring.application.name=voting
* spring.datasource.url=jdbc:mysql://localhost:3306/voting_db
* spring.datasource.username=root
* spring.datasource.password=YOUR_PASSWORD
* spring.jpa.hibernate.ddl-auto=update
* spring.jpa.show-sql=true

### Start the server
./mvnw spring-boot:run

## How to use the API
1. Cast a Vote (User Role)
   Send a POST request to http://localhost:8080/api/vote/cast.

    Authentication: Set up Basic Auth using username voter1 and password password123.

    JSON Body: {
    "candidateName": "Candidate Alpha"
    }
2. View Election Results (Admin Role)
   send a GET request to http://localhost:8080/api/admin/results.

    Authentication: Set up Basic Auth using username admin and password admin456.

    JSON Body: None required.

The server will return a live JSON summary of candidates and their total vote counts.

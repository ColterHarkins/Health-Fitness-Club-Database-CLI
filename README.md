# Health-Fitness-Club-Database-CLI
Health and Fitness Club application that allows you to sign up / in as a member, create goals and log health metrics, sign up for classes and track your progress.
Youtube Link: https://youtu.be/0uwxs4GT62I

How to set up the database:
- git clone https://github.com/ColterHarkins/Health-Fitness-Club-Database-CLI.git
- cd Health-Fitness-Club-Database-CLI
- Open PostgreSQL
- Run DDL file located in /sql/DDL.sql
- Run DML sample file located in /sql/DML.sql
- Update URL, USER and PASSWORD in /app/src/main/java/DatabaseConnection.java
- Build the project in maven with same dependencies in the pom.xml file
- Run main.java
- A CLI will start prompting you with options

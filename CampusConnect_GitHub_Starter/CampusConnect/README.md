# CampusConnect – Student Service Management System

A Java Swing + JDBC starter implementation for the CampusConnect final project.

## Important project note

This is a **working baseline**, not a verified merge of TSA1–TSA4. The only earlier TSA code available in the conversation was the Employee Payroll System (`Employee`, `Taxable`, `FullTimeEmployee`, `PartTimeEmployee`, `PayrollSystem`). That practical demonstrates abstraction, interfaces, inheritance, encapsulation, overriding, and polymorphism. Those OOP concepts are carried into this project's layered design, but the payroll classes are not renamed into unrelated student-service classes.

The exact CampusConnect categories, priority rules, time estimates, validation rules, and status transitions from your other TSAs have not been provided. This baseline therefore uses clearly identified starter values:
- Categories: `Academic`, `Facilities`, `IT Support`, `Student Records`, `Other`
- Priorities: `Low`, `Medium`, `High`
- Statuses: `Pending`, `In Progress`, `Completed`, `Cancelled`
- Process Next selects the oldest `Pending` request (FIFO).

**Before final submission, replace these starter values and transitions with the exact approved TSA rules.** Do not claim this is the final TSA-integrated version until you compare it with your earlier milestones.

## Requirements

- Java JDK 17 or newer
- Apache Maven
- MySQL Server 8.0 or compatible
- MySQL Workbench (optional, for running SQL and viewing tables)

## Database setup

1. Start MySQL.
2. Open `database/schema.sql` in MySQL Workbench and execute it.
3. Optionally execute `database/seed_data.sql` for sample records.
4. Configure the database connection using environment variables:

Windows PowerShell:
```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/campusconnect"
$env:DB_USER="root"
$env:DB_PASSWORD="your_mysql_password"
```

Windows Command Prompt:
```bat
set DB_URL=jdbc:mysql://localhost:3306/campusconnect
set DB_USER=root
set DB_PASSWORD=your_mysql_password
```

Linux/macOS:
```bash
export DB_URL="jdbc:mysql://localhost:3306/campusconnect"
export DB_USER="root"
export DB_PASSWORD="your_mysql_password"
```

The application defaults to `jdbc:mysql://localhost:3306/campusconnect` and user `root` if variables are not set. The password defaults to blank. Change these settings for your own computer. Never commit real passwords to GitHub.

## Build and run

From the project folder:
```bash
mvn clean test
mvn exec:java
```

If you use NetBeans, choose **Open Project**, select this folder, allow Maven to load dependencies, then run `edu.campusconnect.Main`.

## Current features

- Add, search, list/refresh, update status, and delete service requests
- FIFO Process Next for pending requests
- Category and status filters
- Sort table columns by clicking their headers
- Summary report by status
- MySQL persistence using JDBC and prepared statements
- Database operations run in `SwingWorker`, not directly on Swing's Event Dispatch Thread
- Basic input validation and confirmation before deletion

## GitHub upload

1. Create a new empty repository named `CampusConnect`.
2. Extract this folder.
3. In GitHub Desktop, choose **Add an Existing Repository** and select this folder, or use:
```bash
git init
git add .
git commit -m "Initial CampusConnect project"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/CampusConnect.git
git push -u origin main
```
Replace `YOUR-USERNAME` with your GitHub username.

## Project structure

```text
src/main/java/edu/campusconnect/
  Main.java
  database/DatabaseConnection.java
  exception/ValidationException.java
  model/ServiceRequest.java
  dao/ServiceRequestDao.java
  dao/JdbcServiceRequestDao.java
  service/RequestService.java
  view/CampusConnectFrame.java
src/test/java/edu/campusconnect/
  model/ServiceRequestTest.java
database/
  schema.sql
  seed_data.sql
docs/
  Final_Test_Report.md
  Traceability_Matrix.md
  Architecture.md
  AI_USE_LOG.md
```

## Troubleshooting

- `Communications link failure`: make sure MySQL Server is running and the URL/port are correct.
- `Access denied`: check `DB_USER` and `DB_PASSWORD`.
- `Unknown database`: run `database/schema.sql`.
- `No suitable driver`: open the project through Maven so it can download MySQL Connector/J.
- Port 3306 is already in use: change the port in `DB_URL` to match your MySQL configuration.

## AI assistance disclosure

This starter project was generated with AI assistance. Review every file, test it on your machine, adjust it to your TSA-approved requirements, and disclose the assistance according to your instructor's rules. You must be able to explain and modify the submitted code.

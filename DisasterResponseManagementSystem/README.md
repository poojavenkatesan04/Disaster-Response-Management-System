# Disaster Response Management System

A pure Java Swing disaster-response application.

## Main features

- Citizen-style **Report Emergency** button on the main window
- Automatic emergency priority calculation
- Emergency status lifecycle: REPORTED -> ASSIGNED -> IN_PROGRESS -> RESOLVED
- Emergency search and filtering by ID, reporter, location, type, status and priority
- Report ID tracking so a reporter can check the current status
- Dashboard statistics for emergencies and available resources
- Volunteers, emergency teams, vehicles, food, medicine and shelters
- Resource assignment and availability tracking
- Activity log and asynchronous notifications
- Input validation and JUnit 5 test sources

## Run on Windows PowerShell (without Maven)

Open PowerShell in the project folder and run:

```powershell
Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
New-Item -ItemType Directory out | Out-Null
javac -d out (Get-ChildItem -Recurse -Path src\main\java -Filter *.java).FullName
java -cp out com.disaster.Main
```

If you have not changed the Java source files since the last compilation, you only need:

```powershell
java -cp out com.disaster.Main
```

The `src\test\java` files use JUnit 5 and are not included in the normal `javac` command above. They require the JUnit dependency to run.

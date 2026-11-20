# CampusConnect Architecture

```text
Swing Presentation (CampusConnectFrame)
             |
             v
       RequestService
   (validation, workflow,
    FIFO selection, summary)
             |
             v
     ServiceRequestDao
          interface
             |
             v
   JdbcServiceRequestDao
   (PreparedStatement/JDBC)
             |
             v
        MySQL Database

ServiceRequest = model
ValidationException = exception
DatabaseConnection = connection configuration
```

## OOP connection to the Payroll TSA

The Payroll TSA demonstrated an abstract superclass, an interface, inheritance, overriding, and polymorphism. This CampusConnect baseline uses an interface (`ServiceRequestDao`) so the service layer depends on a contract rather than a concrete database implementation. `JdbcServiceRequestDao` implements that contract. The `ServiceRequest` model encapsulates request data.

This is a conceptual continuation of OOP learning, not a claim that the payroll domain itself is the CampusConnect domain.

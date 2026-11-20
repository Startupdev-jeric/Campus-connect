# Final Test Report – CampusConnect Starter

Record the actual result and date after running each test on your computer. These are test cases to execute; they are not claims that the database tests have already passed.

| No. | Test | Steps / Input | Expected result | Actual result |
|---:|---|---|---|---|
| 1 | Add valid request | Enter all valid fields and click Add | Record is saved and appears in table | Not yet run |
| 2 | Missing student name | Leave name blank | Validation message; no record added | Not yet run |
| 3 | Missing student number | Leave number blank | Validation message; no record added | Not yet run |
| 4 | Missing description | Leave description blank | Validation message; no record added | Not yet run |
| 5 | Invalid estimate | Try a value outside 1–365 | Spinner prevents invalid value | Not yet run |
| 6 | Valid category | Select a listed category | Request can be saved | Not yet run |
| 7 | Invalid category | Submit an unsupported category through service test | Validation exception | Not yet run |
| 8 | Search existing record | Search by student name or number | Matching row appears | Not yet run |
| 9 | Search no match | Search for unknown text | Empty result table | Not yet run |
| 10 | Refresh list | Click Refresh | Database records appear | Not yet run |
| 11 | Update status | Select Pending row and choose In Progress | Status is updated in database and table | Not yet run |
| 12 | Reject invalid transition | Try changing Completed to Pending | Validation message; status unchanged | Not yet run |
| 13 | Delete with confirmation | Select row and confirm deletion | Record is deleted | Not yet run |
| 14 | Cancel deletion | Select row and cancel confirmation | Record remains | Not yet run |
| 15 | Process Next | Have at least one Pending request | Oldest Pending request becomes In Progress | Not yet run |
| 16 | No pending requests | Ensure no Pending rows and click Process Next | Informational message | Not yet run |
| 17 | SQL injection-like text | Enter text such as `' OR '1'='1` in description | Treated as text, not SQL | Not yet run |
| 18 | Database outage | Stop MySQL and perform refresh | Error message; application remains open | Not yet run |
| 19 | Persistence | Add a row, close app, reopen app | Row remains in database | Not yet run |
| 20 | Repeated operations | Add, update, search, delete multiple records | UI continues to show committed database state | Not yet run |

## Execution evidence

Add screenshots or short notes here after testing. Do not mark a test as Passed until you have performed it.

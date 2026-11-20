# Traceability Matrix – Starter Draft

**Important:** Only the Employee Payroll System TSA source was available when this draft was created. The exact TSA1–TSA4 mapping is therefore incomplete. Fill in the TSA columns using your actual submitted milestone documents.

| Final feature / design | Source / concept currently evidenced | TSA origin | Test evidence |
|---|---|---|---|
| Abstract model / shared behavior | Payroll TSA: abstract `Employee` class | Confirm exact TSA number | OOP code review; add model tests |
| Interface-based behavior | Payroll TSA: `Taxable` interface | Confirm exact TSA number | OOP code review |
| Inheritance and overriding | Payroll TSA: `FullTimeEmployee`, `PartTimeEmployee` | Confirm exact TSA number | OOP code review |
| Polymorphic processing | Payroll TSA: `Employee[]` and `calculateSalary()` | Confirm exact TSA number | OOP code review |
| Add request | CampusConnect final-project brief | TSA origin to verify | Test 1 |
| Search and refresh | CampusConnect final-project brief | TSA origin to verify | Tests 8–10 |
| Status update | CampusConnect final-project brief | TSA origin to verify | Tests 11–12 |
| Delete | CampusConnect final-project brief | TSA origin to verify | Tests 13–14 |
| Process Next / FIFO | CampusConnect final-project brief | TSA origin to verify | Tests 15–16 |
| Sorting and filtering | CampusConnect final-project brief | TSA origin to verify | Table sort and status filter checks |
| JDBC persistence | CampusConnect final-project brief | TSA origin to verify | Tests 18–20 |
| Validation and parameterized SQL | CampusConnect final-project brief | TSA origin to verify | Tests 2–7 and 17 |

Replace “Confirm exact TSA number” and “TSA origin to verify” after checking the original TSA files. Do not invent milestone evidence.

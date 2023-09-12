# Tap Test

This project was made with Spring Boot 3.1.3 and Java 17
and in-memory h2 database

## How to Run

In any IDE run normally.
Or with the command $ mvn spring-boot:run from the directory.

### H2 Database console

Runs the app in the development mode.\
Open [http://localhost:8080/tap/api/h2-console/](http://localhost:8080/tap/api/h2-console/) to view it in your browser.

Username sa
Password password

The initial data script can be found in the resources folder

### WebServices

Can be called either via Postman or with the React JS UI.

http://localhost:8080/tap/api/

Are configured to utilize Basic Auth with the following credentials

Username user
Password taptestpassword

When running from postman please use the following header:
Content-Type application/json

### Other Comments

There are a couple of things i would have liked to improve:
- Better error handling in both the UI and Backend
- Improve validations
- Improvements in processing speed (Sometimes the UI will take a few seconds longer to load)

If there is any question or anything is unclear please feel free to reach out at any time.
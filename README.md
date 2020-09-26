# FinzlyLoanManagement
Backend of the loan management application

# Clone Repo:
git clone https://github.com/Boojith/FinzlyLoan.git

# Maven:
Run mvn build

# DataBase :
MySql has been used for DB.

Make sure DB contains a schema finzly.

Tables will automatically be created by spring (Loan,Customer,PaymentSchedule ).

Change spring.jpa.hibernate.ddl-auto: create-drop (or) update as needed in application properties.

Make sure to change DB credentials

spring.datasource.username=root

spring.datasource.password=password

# IDE:
Import the project in IDE of you choice (Eclipse is preferred)


# Run:
Run application as Spring Boot Applicaiton.
Applicaiton will be up and running in http://localhost:8080/

# Logging
Slf4j Logging has been  implemented.Find logs in console.

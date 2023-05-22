# Paths README


## Table of Contents
1. Prerequisites
2. Installation
3. Running the Application
4. Running the tests

---

### 1. Prerequisites
Before starting, make sure that the following software is installed on your local machine:


- Maven: [Download Maven](https://maven.apache.org/install.html)


---

### 2. Installation

Download the source code from the repository

---

### 3. Running the Application

```sh
mvn javafx:run
```

---

### 4. Running the tests
### Run Unit Tests with [jUnit](https://junit.org/junit5/)

```sh
mvn test
```

### Get test coverage with [JaCoCo](https://www.eclemma.org/jacoco/)
After running the commands locate the site folder in the target folder, and open the index.html file in you preferred browser

```sh
mvn jacoco:report
```
---






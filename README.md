# Automation Testing Framework

## Overview

This project is an automation testing framework designed for testing web applications. It utilizes Selenium WebDriver for browser automation, Cucumber for Behavior-Driven Development (BDD), JUnit for test execution, and Java as the programming language. The framework supports running tests on multiple operating systems (Windows, Linux, macOS).

## Features

- Integration with Cucumber for BDD.
- Selenium WebDriver for browser automation.
- JUnit for test execution.
- Maven for project management and build automation.
- Generation of Cucumber HTML reports

## Prerequisites

### Ensure the following software is installed on your system:

- Java 18 or higher installed
- Maven 3.6.3 or higher

## Configuration

### Dependencies used in this project:

- Selenium Java 4.22.0
- Cucumber Java 7.18.0
- Cucumber JUnit 7.18.0 (test scope)
- Maven Cucumber Reporting 5.8.1
- Log4j 2.17.2
- Hamcrest 1.3

## Setup

### Clone the Repository

Clone the repository and build the project using Maven:

```bash
git clone https://github.com/njvision/WebTestingATF.git
cd WebTestingATF
mvn clean install
```

## Running tests:

### Run tests using Maven

To execute tests, use the following Maven commands:

```bash
mvn clean test
```

You can also run tests directly from feature files using:

```bash
mvn test
```

## Generating Cucumber Reports

To generate Cucumber HTML reports after running tests:

```bash
mvn clean verify
```

The report will be generated in target/cucumber-report-html.


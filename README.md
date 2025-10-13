This project contains automated **UI and API tests** for the [y-r.by](https://y-r.by) website.  
The goal of the project is to verify the main user flows such as login, product search, and cart functionality using modern test automation tools.

---

## 📋 Project Overview

### ✅ UI Tests
Automated UI tests cover the following features:

- **Login Page** – verify login functionality.
- **Search Page** – test search input behavior and product results.
- **Cart Page** – test adding products to the cart and verifying cart contents.

### ✅ API Tests
API tests cover:

- **Login API** – validate successful and unsuccessful login responses.
- **Search API** – verify product search results with different query values.

---
## 🧰 Technologies and Tools

- **Java 21**
- **JUnit 5**
- **Selenium WebDriver**
- **Rest Assured** (for API testing)
- **Maven** (build and dependency management)
- **Allure** (test reporting)
- **ChromeDriver** (for browser automation)

---

## 🚀 How to Run Tests

### Prerequisites
- Java 21 or higher installed
- Maven installed
- Chrome browser and matching ChromeDriver version
- Allure Commandline installed (for report generation)

### Run all tests
bash
mvn clean test

### 📊 Test Results
After running the tests, you can view results in the Allure report.

---
## 🧑‍💻 Author
Liudmila Sukhadolskaya
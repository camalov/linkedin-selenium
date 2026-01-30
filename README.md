# LinkedIn

LinkedIn saytının login hissəsi üçün UI automation testləri - Selenium, TestNG, Cucumber, Allure ilə.

## 📁 Proyekt Strukturu

```
LinkedIn/
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   └── com/linkedin/
│   │   │       ├── base/
│   │   │       │   └── BaseTest.java
│   │   │       ├── pages/
│   │   │       │   └── LoginPage.java
│   │   │       ├── runner/
│   │   │       │   └── CucumberTestRunner.java
│   │   │       └── steps/
│   │   │           ├── LoginSteps.java
│   │   │           └── Hooks.java
│   │   └── resources/
│   │       ├── features/
│   │       │   └── login.feature
│   │       ├── testng.xml
│   │       └── cucumber.properties
├── pom.xml
└── README.md
```

## 🚀 Quraşdırma

### Tələblər

- Java 17+
- Maven 3.8+
- Chrome/Firefox/Edge browser

### Quraşdırma Addımları

1. **Dependencies quraşdırın:**
   ```bash
   mvn clean install
   ```

2. **Allure quraşdırın:**
   ```bash
   # macOS
   brew install allure
   
   # Linux
   wget https://github.com/allure-framework/allure2/releases/download/2.24.0/allure-2.24.0.tgz
   tar -zxvf allure-2.24.0.tgz
   sudo mv allure-2.24.0 /opt/allure
   export PATH=$PATH:/opt/allure/bin
   ```

## 🧪 Testləri İcra Etmək

### Bütün testləri icra etmək:

```bash
mvn test
```

### Xüsusi tag ilə test icrası:

```bash
# Smoke testlər
mvn test -Dcucumber.filter.tags="@Smoke"

# Valid login testləri
mvn test -Dcucumber.filter.tags="@ValidLogin"

# Invalid login testləri
mvn test -Dcucumber.filter.tags="@InvalidLogin"

# Error handling testləri
mvn test -Dcucumber.filter.tags="@ErrorHandling"
```

### Allure report yaratmaq:

```bash
# Testləri icra et və report yarat
mvn test
mvn allure:report

# Report-u açmaq
mvn allure:serve
```

## 📝 Feature Faylları

### Login Feature
- `src/test/resources/features/login.feature`
- LinkedIn login testləri:
  - Uğurlu login
  - Uğursuz login (yanlış username/password)
  - Boş field validasiyası
  - Error message yoxlanışı

## 🏷️ Tags

- `@LinkedIn` - Bütün LinkedIn testləri
- `@Login` - Login testləri
- `@Smoke` - Smoke testlər
- `@ValidLogin` - Uğurlu login testləri
- `@InvalidLogin` - Uğursuz login testləri
- `@ErrorHandling` - Error handling testləri

## 📊 Nəticələr

Test nəticələri:
- Allure: `target/allure-results/` və `target/allure-report/`
- Cucumber HTML: `target/cucumber-reports/`
- Cucumber JSON: `target/cucumber-reports/cucumber.json`
- TestNG: `target/surefire-reports/`

## 🔧 Konfiqurasiya

### Browser Seçimi

`testng.xml` faylında browser parametri təyin edilə bilər:

```xml
<parameter name="browser" value="chrome"/>
```

Mövcud browser-lar: `chrome`, `firefox`, `edge`

### Timeout

Default timeout: 15 saniyə. `BaseTest.java` faylında dəyişdirilə bilər.

### Headless Mode

`BaseTest.java` faylında headless mode aktivdir. Qeyd edin ki, LinkedIn bəzən bot detection istifadə edir, ona görə headless mode-da problemlər ola bilər.

## ⚠️ Qeydlər

- LinkedIn real hesab məlumatları ilə test etməyin (rate limiting və hesab bloklanması riski)
- Test məlumatları üçün test hesabları istifadə edin
- LinkedIn-in bot detection mexanizmi səbəbindən bəzi testlər uğursuz ola bilər

## 📚 Əlavə Məlumat

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [Cucumber Documentation](https://cucumber.io/docs)
- [TestNG Documentation](https://testng.org/doc/)
- [Allure Documentation](https://docs.qameta.io/allure/)

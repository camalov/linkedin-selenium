package com.linkedin.runner; // Package declaration - bu faylın hansı package-ə aid olduğunu göstərir

import io.cucumber.testng.AbstractTestNGCucumberTests; // AbstractTestNGCucumberTests - TestNG ilə Cucumber inteqrasiyası üçün
import io.cucumber.testng.CucumberOptions; // CucumberOptions annotation - Cucumber konfiqurasiyası üçün

@CucumberOptions( // CucumberOptions annotation - Cucumber test konfiqurasiyası
        features = "src/test/resources/features", // Feature fayllarının yolu - Cucumber feature faylları
        glue = { "com.linkedin.steps" }, // Glue code yolu - Step definitions package-i
        plugin = { // Plugin-lər - Report formatları və Allure inteqrasiyası
                "pretty", // Pretty plugin - Console-da gözəl format
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", // Allure plugin - Allure reporting üçün
                "html:target/cucumber-reports/cucumber-html-report", // HTML report - directory
                "json:target/cucumber-reports/cucumber.json", // JSON report - file
                "junit:target/cucumber-reports/cucumber.xml" // JUnit XML report - file
        }, tags = "@LinkedIn", // Tags - Yalnız @LinkedIn tag-li testləri icra et
        monochrome = true // Monochrome - Console output-da rəng kodlarını söndür
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests { // CucumberTestRunner class - TestNG ilə Cucumber
                                                                      // runner
    // Bu class TestNG ilə Cucumber testlərini icra etmək üçün istifadə olunur
    // AbstractTestNGCucumberTests-dən extend edir və TestNG test suite kimi işləyir
}

package com.linkedin.steps; // Package declaration - bu faylın hansı package-ə aid olduğunu göstərir

import io.cucumber.java.After; // After annotation - Cucumber After hook üçün
import io.cucumber.java.Before; // Before annotation - Cucumber Before hook üçün
import io.cucumber.java.Scenario; // Scenario class - Cucumber scenario məlumatları üçün
import io.qameta.allure.Allure; // Allure class - Allure reporting üçün
import org.openqa.selenium.OutputType; // OutputType enum - screenshot formatı üçün
import org.openqa.selenium.TakesScreenshot; // TakesScreenshot interface - screenshot almaq üçün
import org.openqa.selenium.WebDriver; // WebDriver interface - browser idarəetməsi üçün
import org.openqa.selenium.chrome.ChromeDriver; // ChromeDriver class - Chrome browser üçün
import org.openqa.selenium.chrome.ChromeOptions; // ChromeOptions class - Chrome parametrləri üçün
import org.openqa.selenium.support.ui.WebDriverWait; // WebDriverWait - explicit wait üçün
import io.github.bonigarcia.wdm.WebDriverManager; // WebDriverManager - driver avtomatik quraşdırma üçün

import java.time.Duration; // Duration - vaxt intervalı üçün

public class Hooks { // Hooks class - Cucumber hooks üçün (Before/After)

    private static WebDriver driver; // Static WebDriver instance - bütün testlər üçün paylaşılan driver

    @Before // Before hook - hər scenario-dan əvvəl icra olunur
    public void setUp(Scenario scenario) { // Before hook metodunun implementasiyası
        Allure.step("Starting scenario: " + scenario.getName()); // Allure report-a scenario başlanğıcı əlavə et
        System.out.println("Starting scenario: " + scenario.getName()); // Console-a scenario adını yazdır

        WebDriverManager.chromedriver().setup(); // ChromeDriver-i avtomatik quraşdır
        ChromeOptions options = new ChromeOptions(); // ChromeOptions obyekti yarat
        options.addArguments("--start-maximized"); // Browser-i maksimum ölçüdə aç
        options.addArguments("--disable-notifications"); // Bildirişləri söndür
        options.addArguments("--remote-allow-origins=*");
        // options.addArguments("--headless"); // Hər ehtimala qarşı headless mode
        // comment-də saxlanılır

        driver = new ChromeDriver(options); // ChromeDriver-i yarat
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize(); // Browser pəncərəsini maksimum ölçüdə et
    }

    @After // After hook - hər scenario-dan sonra icra olunur
    public void tearDown(Scenario scenario) { // After hook metodunun implementasiyası
        WebDriver currentDriver = getDriver(); // Cari driver-i al

        if (scenario.isFailed() && currentDriver != null) { // Əgər scenario uğursuz olubsa və driver varsa
            try { // Try-catch bloku - xəta yoxlanışı üçün
                final byte[] screenshot = ((TakesScreenshot) currentDriver) // Driver-dən screenshot al
                        .getScreenshotAs(OutputType.BYTES); // Screenshot-u byte array kimi al
                scenario.attach(screenshot, "image/png", "Screenshot"); // Cucumber scenario-ya screenshot əlavə et
                Allure.addAttachment("Screenshot on failure", // Allure report-a screenshot əlavə et
                        new java.io.ByteArrayInputStream(screenshot)); // Byte array-i InputStream-ə çevir
            } catch (Exception e) { // Əgər xəta baş verərsə
                System.err.println("Screenshot alına bilmədi: " + e.getMessage()); // Xəta mesajını yazdır
            }
        }

        if (currentDriver != null) { // Əgər driver varsa
            try { // Try-catch bloku - xəta yoxlanışı üçün
                currentDriver.quit(); // Driver-i bağla
            } catch (Exception e) { // Əgər xəta baş verərsə
                System.err.println("Driver bağlana bilmədi: " + e.getMessage()); // Xəta mesajını yazdır
            }
            setDriver(null); // Driver-i null et
        }

        Allure.step("Finished scenario: " + scenario.getName()); // Allure report-a scenario bitməsi əlavə et
        System.out.println("Finished scenario: " + scenario.getName()); // Console-a scenario adını yazdır
    }

    public static WebDriver getDriver() { // Driver-i almaq üçün static metod
        return driver; // Driver-i qaytar
    }

    public static void setDriver(WebDriver webDriver) { // Driver-i təyin etmək üçün static metod
        driver = webDriver; // Driver-i təyin et
    }
}

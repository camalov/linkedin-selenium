package com.linkedin.base; // Package declaration - bu faylın hansı package-ə aid olduğunu göstərir

import io.github.bonigarcia.wdm.WebDriverManager; // WebDriverManager - driver avtomatik quraşdırma üçün
import io.qameta.allure.Allure; // Allure class - Allure reporting üçün
import org.openqa.selenium.OutputType; // OutputType enum - screenshot formatı üçün
import org.openqa.selenium.TakesScreenshot; // TakesScreenshot interface - screenshot almaq üçün
import org.openqa.selenium.WebDriver; // WebDriver interface - browser idarəetməsi üçün
import org.openqa.selenium.chrome.ChromeDriver; // ChromeDriver class - Chrome browser üçün
import org.openqa.selenium.chrome.ChromeOptions; // ChromeOptions class - Chrome parametrləri üçün
import org.openqa.selenium.edge.EdgeDriver; // EdgeDriver class - Edge browser üçün
import org.openqa.selenium.edge.EdgeOptions; // EdgeOptions class - Edge parametrləri üçün
import org.openqa.selenium.firefox.FirefoxDriver; // FirefoxDriver class - Firefox browser üçün
import org.openqa.selenium.firefox.FirefoxOptions; // FirefoxOptions class - Firefox parametrləri üçün
import org.openqa.selenium.support.ui.WebDriverWait; // WebDriverWait - explicit wait üçün
import org.testng.annotations.AfterMethod; // AfterMethod annotation - TestNG AfterMethod hook üçün
import org.testng.annotations.BeforeMethod; // BeforeMethod annotation - TestNG BeforeMethod hook üçün
import org.testng.annotations.Parameters; // Parameters annotation - TestNG parametrləri üçün

import java.io.ByteArrayInputStream; // ByteArrayInputStream - byte array-i InputStream-ə çevirmək üçün
import java.time.Duration; // Duration - vaxt intervalı üçün

public class BaseTest { // BaseTest class - bütün testlər üçün base class
    
    protected WebDriver driver; // Protected WebDriver - subclass-larda istifadə üçün
    protected WebDriverWait wait; // Protected WebDriverWait - subclass-larda istifadə üçün
    private static final String BASE_URL = "https://www.linkedin.com/login"; // LinkedIn login URL-i - sabit dəyər
    
    @BeforeMethod // BeforeMethod hook - hər test metodundan əvvəl icra olunur
    @Parameters("browser") // Browser parametri - testng.xml-dən gəlir
    public void setUp(String browser) { // BeforeMethod hook metodunun implementasiyası
        driver = initializeDriver(browser); // Browser-ə görə driver yarat
        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // 15 saniyəlik wait yarat
        driver.manage().window().maximize(); // Browser pəncərəsini maksimum ölçüdə et
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // 10 saniyəlik implicit wait təyin et
        driver.get(BASE_URL); // LinkedIn login səhifəsinə get
    }
    
    private WebDriver initializeDriver(String browser) { // Browser-ə görə driver yaratmaq üçün private metod
        switch (browser.toLowerCase()) { // Browser adını kiçik hərflərə çevir və yoxla
            case "chrome": // Əgər Chrome seçilibsə
                WebDriverManager.chromedriver().setup(); // ChromeDriver-i avtomatik quraşdır
                ChromeOptions chromeOptions = new ChromeOptions(); // ChromeOptions obyekti yarat
                chromeOptions.addArguments("--start-maximized"); // Browser-i maksimum ölçüdə aç
                chromeOptions.addArguments("--disable-notifications"); // Bildirişləri söndür
                // chromeOptions.addArguments("--headless"); // LinkedIn bot detection üçün headless mode deaktivdir
                return new ChromeDriver(chromeOptions); // ChromeDriver-i yarat və qaytar
                
            case "firefox": // Əgər Firefox seçilibsə
                WebDriverManager.firefoxdriver().setup(); // FirefoxDriver-i avtomatik quraşdır
                FirefoxOptions firefoxOptions = new FirefoxOptions(); // FirefoxOptions obyekti yarat
                firefoxOptions.addArguments("--start-maximized"); // Browser-i maksimum ölçüdə aç
                return new FirefoxDriver(firefoxOptions); // FirefoxDriver-i yarat və qaytar
                
            case "edge": // Əgər Edge seçilibsə
                WebDriverManager.edgedriver().setup(); // EdgeDriver-i avtomatik quraşdır
                EdgeOptions edgeOptions = new EdgeOptions(); // EdgeOptions obyekti yarat
                edgeOptions.addArguments("--start-maximized"); // Browser-i maksimum ölçüdə aç
                return new EdgeDriver(edgeOptions); // EdgeDriver-i yarat və qaytar
                
            default: // Əgər heç biri seçilməyibsə
                WebDriverManager.chromedriver().setup(); // ChromeDriver-i avtomatik quraşdır (default)
                ChromeOptions defaultOptions = new ChromeOptions(); // ChromeOptions obyekti yarat
                defaultOptions.addArguments("--start-maximized"); // Browser-i maksimum ölçüdə aç
                return new ChromeDriver(defaultOptions); // ChromeDriver-i yarat və qaytar (default)
        }
    }
    
    @AfterMethod // AfterMethod hook - hər test metodundan sonra icra olunur
    public void tearDown(org.testng.ITestResult result) { // AfterMethod hook metodunun implementasiyası
        if (result.getStatus() == org.testng.ITestResult.FAILURE) { // Əgər test uğursuz olubsa
            takeScreenshot(result.getName()); // Screenshot al
        }
        if (driver != null) { // Əgər driver varsa
            driver.quit(); // Driver-i bağla
        }
    }
    
    private void takeScreenshot(String testName) { // Screenshot almaq üçün private metod
        if (driver instanceof TakesScreenshot) { // Əgər driver TakesScreenshot interface-ini implement edirsə
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES); // Screenshot-u byte array kimi al
            Allure.addAttachment("Screenshot: " + testName, // Allure report-a screenshot əlavə et
                new ByteArrayInputStream(screenshot)); // Byte array-i InputStream-ə çevir
        }
    }
    
    protected void navigateToUrl(String url) { // URL-ə getmək üçün protected metod
        driver.get(url); // Verilən URL-ə get
    }
}

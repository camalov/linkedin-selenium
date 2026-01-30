package com.linkedin.pages; // Package declaration - bu faylın hansı package-ə aid olduğunu göstərir

import org.openqa.selenium.WebDriver; // WebDriver interface - browser idarəetməsi üçün
import org.openqa.selenium.WebElement; // WebElement interface - web elementləri üçün
import org.openqa.selenium.support.FindBy; // FindBy annotation - element tapmaq üçün
import org.openqa.selenium.support.PageFactory; // PageFactory - Page Object Model üçün
import org.openqa.selenium.support.ui.ExpectedConditions; // ExpectedConditions - gözləyilən şərtlər üçün
import org.openqa.selenium.support.ui.WebDriverWait; // WebDriverWait - explicit wait üçün

import java.time.Duration; // Duration - vaxt intervalı üçün

public class LoginPage { // LoginPage class - LinkedIn login səhifəsi üçün Page Object Model
    
    private WebDriver driver; // WebDriver instance - browser driver üçün
    private WebDriverWait wait; // WebDriverWait instance - explicit wait üçün
    
    @FindBy(id = "username") // Username input field-i tapmaq üçün ID selector
    private WebElement usernameInput; // Username input elementi
    
    @FindBy(id = "password") // Password input field-i tapmaq üçün ID selector
    private WebElement passwordInput; // Password input elementi
    
    @FindBy(xpath = "//button[@type='submit' and contains(text(), 'Sign in')]") // Sign in button-u tapmaq üçün XPath
    private WebElement signInButton; // Sign in button elementi
    
    @FindBy(xpath = "//button[contains(@aria-label, 'Sign in')]") // Alternativ Sign in button selector
    private WebElement signInButtonAlt; // Alternativ Sign in button elementi
    
    @FindBy(xpath = "//div[@role='alert']") // Error message elementi tapmaq üçün XPath
    private WebElement errorMessage; // Error message elementi
    
    @FindBy(xpath = "//div[contains(@class, 'error')]") // Alternativ error message selector
    private WebElement errorMessageAlt; // Alternativ error message elementi
    
    @FindBy(xpath = "//a[contains(text(), 'Forgot password')]") // Forgot password link-i tapmaq üçün XPath
    private WebElement forgotPasswordLink; // Forgot password link elementi
    
    public LoginPage(WebDriver driver) { // Constructor - LoginPage obyektini yaratmaq üçün
        this.driver = driver; // Driver-i class field-inə təyin et
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // 15 saniyəlik wait yarat
        PageFactory.initElements(driver, this); // PageFactory ilə elementləri initialize et
    }
    
    public void enterUsername(String username) { // Username daxil etmək üçün metod
        wait.until(ExpectedConditions.visibilityOf(usernameInput)); // Username input görünənə qədər gözlə
        usernameInput.clear(); // Əvvəlki mətn varsa sil
        usernameInput.sendKeys(username); // Username-i yaz
    }
    
    public void enterPassword(String password) { // Password daxil etmək üçün metod
        wait.until(ExpectedConditions.visibilityOf(passwordInput)); // Password input görünənə qədər gözlə
        passwordInput.clear(); // Əvvəlki mətn varsa sil
        passwordInput.sendKeys(password); // Password-u yaz
    }
    
    public void clickSignIn() { // Sign in button-a klik etmək üçün metod
        try { // Try-catch bloku - xəta yoxlanışı üçün
            wait.until(ExpectedConditions.elementToBeClickable(signInButton)); // Sign in button klik edilənə qədər gözlə
            signInButton.click(); // Sign in button-a klik et
        } catch (Exception e) { // Əgər xəta baş verərsə
            // Fallback to alternative selector - alternativ selector istifadə et
            wait.until(ExpectedConditions.elementToBeClickable(signInButtonAlt)); // Alternativ button klik edilənə qədər gözlə
            signInButtonAlt.click(); // Alternativ button-a klik et
        }
    }
    
    public void login(String username, String password) { // Tam login prosesi üçün metod
        enterUsername(username); // Username daxil et
        enterPassword(password); // Password daxil et
        clickSignIn(); // Sign in button-a klik et
    }
    
    public boolean isLoginPageLoaded() { // Login səhifəsinin yükləndiyini yoxlamaq üçün metod
        return driver.getCurrentUrl().contains("linkedin.com/login") || // URL-də "login" varsa
               driver.getCurrentUrl().contains("linkedin.com/signin") || // Və ya URL-də "signin" varsa
               usernameInput.isDisplayed(); // Və ya username input görünürsə
    }
    
    public boolean isLoggedIn() { // Login olunub-olunmadığını yoxlamaq üçün metod
        return driver.getCurrentUrl().contains("linkedin.com/feed") || // URL-də "feed" varsa (uğurlu login)
               driver.getCurrentUrl().contains("linkedin.com/in/") || // Və ya URL-də "in/" varsa (profil səhifəsi)
               !driver.getCurrentUrl().contains("login") && // Və URL-də "login" yoxdursa
               !driver.getCurrentUrl().contains("signin"); // Və URL-də "signin" yoxdursa
    }
    
    public String getErrorMessage() { // Error mesajını almaq üçün metod
        try { // Try-catch bloku - xəta yoxlanışı üçün
            if (errorMessage.isDisplayed()) { // Əgər error message görünürsə
                return errorMessage.getText(); // Error message mətnini qaytar
            }
        } catch (Exception e) { // Əgər xəta baş verərsə
            // Try alternative selector - alternativ selector yoxla
            try { // İkinci try-catch bloku
                if (errorMessageAlt.isDisplayed()) { // Əgər alternativ error message görünürsə
                    return errorMessageAlt.getText(); // Alternativ error message mətnini qaytar
                }
            } catch (Exception ex) { // Əgər ikinci xəta baş verərsə
                // No error message found - error message tapılmadı
            }
        }
        return ""; // Boş string qaytar (error message yoxdursa)
    }
    
    public boolean isErrorMessageDisplayed() { // Error mesajının göründüyünü yoxlamaq üçün metod
        try { // Try-catch bloku - xəta yoxlanışı üçün
            return errorMessage.isDisplayed() || errorMessageAlt.isDisplayed(); // Hər iki error message-dan biri görünürsə true qaytar
        } catch (Exception e) { // Əgər xəta baş verərsə
            return false; // False qaytar
        }
    }
    
    public void clickForgotPassword() { // Forgot password link-inə klik etmək üçün metod
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)); // Link klik edilənə qədər gözlə
        forgotPasswordLink.click(); // Link-ə klik et
    }
}

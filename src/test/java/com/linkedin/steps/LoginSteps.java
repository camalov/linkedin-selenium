package com.linkedin.steps; // Package declaration - bu faylın hansı package-ə aid olduğunu göstərir

import com.linkedin.pages.LoginPage; // LoginPage class - login səhifəsi üçün
import io.cucumber.java.en.Given; // Given annotation - Cucumber Given step üçün
import io.cucumber.java.en.When; // When annotation - Cucumber When step üçün
import io.cucumber.java.en.Then; // Then annotation - Cucumber Then step üçün
import io.qameta.allure.Allure; // Allure class - Allure reporting üçün
import io.qameta.allure.Step; // Step annotation - Allure step üçün
import org.openqa.selenium.WebDriver; // WebDriver interface - browser idarəetməsi üçün
import org.openqa.selenium.support.ui.WebDriverWait; // WebDriverWait - explicit wait üçün
import org.testng.Assert; // Assert class - assertion üçün

import java.time.Duration; // Duration - vaxt intervalı üçün

public class LoginSteps { // LoginSteps class - Cucumber step definitions üçün

    private WebDriver driver; // WebDriver instance - browser driver üçün
    private WebDriverWait wait; // WebDriverWait instance - explicit wait üçün
    private LoginPage loginPage; // LoginPage instance - login səhifəsi üçün

    @Given("I am on LinkedIn login page") // Given step - LinkedIn login səhifəsində olmaq
    @Step("LinkedIn login səhifəsindəyəm") // Allure step annotation
    public void i_am_on_linkedin_login_page() { // Given step metodunun implementasiyası
        driver = Hooks.getDriver(); // Hooks-dan driver-i al
        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // 15 saniyəlik wait yarat

        driver.get("https://www.linkedin.com/login"); // LinkedIn login səhifəsinə get
        loginPage = new LoginPage(driver); // LoginPage obyektini yarat

        Allure.addAttachment("URL", driver.getCurrentUrl()); // Allure report-a URL əlavə et
    }

    @When("I enter username {string}") // When step - username daxil etmək
    @Step("Username '{string}' daxil edirəm") // Allure step annotation
    public void i_enter_username(String username) { // When step metodunun implementasiyası
        loginPage.enterUsername(username); // LoginPage-də username daxil et
        Allure.addAttachment("Action", "Entered username: " + username); // Allure report-a action əlavə et
    }

    @When("I enter password {string}") // When step - password daxil etmək
    @Step("Password '{string}' daxil edirəm") // Allure step annotation
    public void i_enter_password(String password) { // When step metodunun implementasiyası
        loginPage.enterPassword(password); // LoginPage-də password daxil et
        Allure.addAttachment("Action", "Entered password"); // Allure report-a action əlavə et
    }

    @When("I click on Sign in button") // When step - Sign in button-a klik etmək
    @Step("Sign in düyməsinə klik edirəm") // Allure step annotation
    public void i_click_on_sign_in_button() { // When step metodunun implementasiyası
        loginPage.clickSignIn(); // LoginPage-də Sign in button-a klik et
        Allure.addAttachment("Action", "Clicked Sign in button"); // Allure report-a action əlavə et
    }

    @When("I login with username {string} and password {string}") // When step - tam login prosesi
    @Step("Username '{string}' və password '{string}' ilə login olururam") // Allure step annotation
    public void i_login_with_username_and_password(String username, String password) { // When step metodunun
                                                                                       // implementasiyası
        loginPage.login(username, password); // LoginPage-də tam login prosesini icra et
        Allure.addAttachment("Action", "Attempted login with username: " + username); // Allure report-a action əlavə et
    }

    @Then("I should be logged in successfully") // Then step - uğurlu login yoxlanışı
    @Step("Uğurla login olmalıyam") // Allure step annotation
    public void i_should_be_logged_in_successfully() { // Then step metodunun implementasiyası
        try { // Try-catch bloku - xəta yoxlanışı üçün
            Thread.sleep(3000); // Page load üçün 3 saniyə gözlə
        } catch (InterruptedException e) { // Əgər interrupt olunarsa
            Thread.currentThread().interrupt(); // Thread-i interrupt et
        }

        boolean isLoggedIn = loginPage.isLoggedIn(); // Login olunub-olunmadığını yoxla
        String currentUrl = driver.getCurrentUrl(); // Cari URL-i al

        Allure.addAttachment("Current URL", currentUrl); // Allure report-a URL əlavə et
        Allure.addAttachment("Page Title", driver.getTitle()); // Allure report-a page title əlavə et

        Assert.assertTrue(isLoggedIn, // Assert - login olunub olunmadığını yoxla
                "Login uğursuz oldu. Current URL: " + currentUrl); // Xəta mesajı
    }

    @Then("I should see error message") // Then step - error mesajı yoxlanışı
    @Step("Xəta mesajı görməliyəm") // Allure step annotation
    public void i_should_see_error_message() { // Then step metodunun implementasiyası
        try { // Try-catch bloku - xəta yoxlanışı üçün
            Thread.sleep(2000); // Error message üçün 2 saniyə gözlə
        } catch (InterruptedException e) { // Əgər interrupt olunarsa
            Thread.currentThread().interrupt(); // Thread-i interrupt et
        }

        boolean hasError = loginPage.isErrorMessageDisplayed(); // Error mesajının göründüyünü yoxla
        String errorText = loginPage.getErrorMessage(); // Error mesajını al

        Allure.addAttachment("Error Message", errorText); // Allure report-a error mesajı əlavə et

        Assert.assertTrue(hasError, // Assert - error mesajının göründüyünü yoxla
                "Xəta mesajı görünmür. Error text: " + errorText); // Xəta mesajı
    }

    @Then("I should see error message containing {string}") // Then step - error mesajında mətn yoxlanışı
    @Step("Xəta mesajında '{string}' olmalıdır") // Allure step annotation
    public void i_should_see_error_message_containing(String expectedText) { // Then step metodunun implementasiyası
        try { // Try-catch bloku - xəta yoxlanışı üçün
            Thread.sleep(2000); // Error message üçün 2 saniyə gözlə
        } catch (InterruptedException e) { // Əgər interrupt olunarsa
            Thread.currentThread().interrupt(); // Thread-i interrupt et
        }

        String errorText = loginPage.getErrorMessage(); // Error mesajını al
        Allure.addAttachment("Error Message", errorText); // Allure report-a error mesajı əlavə et

        Assert.assertTrue(errorText.contains(expectedText), // Assert - error mesajında gözlənilən mətnin
                                                            // olub-olmadığını yoxla
                "Xəta mesajında '" + expectedText + "' yoxdur. Actual: " + errorText); // Xəta mesajı
    }

    @Then("I should still be on login page") // Then step - login səhifəsində olmaq yoxlanışı
    @Step("Hələ də login səhifəsində olmalıyam") // Allure step annotation
    public void i_should_still_be_on_login_page() { // Then step metodunun implementasiyası
        try { // Try-catch bloku - xəta yoxlanışı üçün
            Thread.sleep(2000); // 2 saniyə gözlə
        } catch (InterruptedException e) { // Əgər interrupt olunarsa
            Thread.currentThread().interrupt(); // Thread-i interrupt et
        }

        boolean isOnLoginPage = loginPage.isLoginPageLoaded(); // Login səhifəsində olub-olmadığını yoxla
        String currentUrl = driver.getCurrentUrl(); // Cari URL-i al

        Allure.addAttachment("Current URL", currentUrl); // Allure report-a URL əlavə et

        Assert.assertTrue(isOnLoginPage, // Assert - login səhifəsində olub-olmadığını yoxla
                "Login səhifəsində deyiləm. Current URL: " + currentUrl); // Xəta mesajı
    }

    @Then("I should see page title contains {string}") // Then step - page title yoxlanışı
    @Step("Səhifə başlığında '{string}' olmalıdır") // Allure step annotation
    public void i_should_see_page_title_contains(String expectedText) { // Then step metodunun implementasiyası
        String pageTitle = driver.getTitle(); // Page title-i al
        Allure.addAttachment("Page Title", pageTitle); // Allure report-a page title əlavə et
        Assert.assertTrue(pageTitle.contains(expectedText), // Assert - page title-də gözlənilən mətnin olub-olmadığını
                                                            // yoxla
                "Page title does not contain: " + expectedText); // Xəta mesajı
    }
}

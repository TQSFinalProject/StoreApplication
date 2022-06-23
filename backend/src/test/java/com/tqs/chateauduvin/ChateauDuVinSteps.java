package com.tqs.chateauduvin;

import io.cucumber.java.AfterAll;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChateauDuVinSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    
    @When("I navigate to {string}")
    public void i_navigate_to(String string) {
        driver = new FirefoxDriver();
        driver.get(string);
    }
    @When("I click on the Login button")
    public void i_click_on_the_login_button() {
        driver.findElement(By.cssSelector("#root > nav > a:nth-child(2)")).click();
    }
    @Then("I should see the {string} page")
    public void i_should_see_the_page(String string) {
        
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        assertThat(driver.findElement(By.tagName("h1")).getText()).isEqualTo(string);
    }
    @Then("I should fill the Username field with {string}")
    public void i_should_fill_the_username_field_with(String string) {
        WebElement from = driver.findElement(By.xpath("//*[@id=\"loginUsernameField\"]"));
        from.click();
        from.sendKeys(string);
    }
    @Then("I should fill the Password field with {string}")
    public void i_should_fill_the_password_field_with(String string) {
        WebElement from = driver.findElement(By.xpath("//*[@id=\"loginPasswordField\"]"));
        from.click();
        from.sendKeys(string);
    }
    @Then("I should click on the Submit button")
    public void i_should_click_on_the_submit_button() {
        driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div/div[1]/form/button")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @Then("I should be redirected to the {string} page")
    public void i_should_be_redirected_to_the_page(String string) {
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        assertThat(driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/span")).getText()).isEqualTo(string);
    }

    
}

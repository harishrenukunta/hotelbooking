package com.equalexperts.hotelbooking.config;

import io.cucumber.spring.ScenarioScope;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class HotelBookingConfig {

    @Bean
    @ScenarioScope
    @ConditionalOnProperty(name="browser", havingValue = "chrome")
    public WebDriver chromeDriver(){
        WebDriverManager.chromedriver().setup();
        final WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;

    }

    @Bean
    @ScenarioScope
    @ConditionalOnProperty(name="browser", havingValue = "firefox")
    public WebDriver firefoxDriver(){
        WebDriverManager.firefoxdriver().setup();
        final WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        return driver;
    }

}

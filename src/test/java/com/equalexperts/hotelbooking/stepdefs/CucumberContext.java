package com.equalexperts.hotelbooking.stepdefs;


import com.equalexperts.hotelbooking.config.HotelBookingConfig;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@CucumberContextConfiguration
@SpringBootTest
@Import(HotelBookingConfig.class)
public class CucumberContext {

    @Autowired
    ApplicationContext ac;

    @After
    public void afterScenario(final Scenario scenario) {
        final WebDriver dr = ac.getBean(WebDriver.class);
        dr.quit();
    }
}

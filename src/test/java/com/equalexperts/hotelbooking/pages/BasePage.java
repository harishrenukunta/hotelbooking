package com.equalexperts.hotelbooking.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public abstract class BasePage {

    @Autowired
    private WebDriver driver;

    @Value("${element.wait.time.insecs}")
    protected int elementWaitTime;

    @Value("${assert.timeout.insecs}")
    protected int assertTimeOut;

    private WebDriverWait wait;

    protected WebDriverWait getWebDriverWait(){
        if(wait == null){
            wait = new WebDriverWait(driver, elementWaitTime);
        }
        return wait;
    }

    protected WebElement waitForElementToDisplay(final By elementBy){
        return getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(elementBy));
    }

    protected WebElement getElement(final By elementBy){
        return waitForElementToDisplay(elementBy);
    }

    protected List<WebElement> getElements(final By elementsBy){
        return getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(elementsBy));
    }
}

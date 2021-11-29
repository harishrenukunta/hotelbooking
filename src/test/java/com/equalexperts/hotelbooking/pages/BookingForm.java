package com.equalexperts.hotelbooking.pages;

import com.equalexperts.hotelbooking.enums.Constants;
import io.cucumber.spring.ScenarioScope;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@ScenarioScope
@Slf4j
public class BookingForm extends BasePage{

    @Autowired
    private WebDriver driver;

    @Value("${base.url}")
    private String baseUrl;

    private By firstnameBy = By.cssSelector("#firstname");
    private By surnameBy = By.cssSelector("#lastname");
    private By priceBy = By.cssSelector("#totalprice");
    private By depositBy = By.cssSelector("#depositpaid");
    private By checkInBy = By.cssSelector("#checkin");
    private By checkOutBy = By.cssSelector("#checkout");
    private By saveBy = By.cssSelector("input[value*='Save']");
    private By firstnameDataBy = By.cssSelector("div:nth-child(1)");
    private By surnameDataBy = By.cssSelector("div:nth-child(2)");
    private By bookingsRowsBy = By.cssSelector("div#bookings div[id]");
    private By bookingsTableBy = By.cssSelector("div#bookings");
    private final int NO_RECORD_ROW_NO  = -1;

    public WebElement firstname(){
        return getElement(firstnameBy);
    }

    public WebElement surname(){
        return getElement(surnameBy);
    }

    public WebElement price(){
        return getElement(priceBy);
    }

    public WebElement deposit(){
        return getElement(depositBy);
    }

    public WebElement checkIn(){
        return getElement(checkInBy);
    }

    public WebElement checkOut(){
        return getElement(checkOutBy);
    }

    public WebElement save(){
        return getElement(saveBy);
    }

    public void open(){
        driver.get(baseUrl);
    }

    public void addBooking(final Map<String, String> bookingInfo){
        log.info("Start -> Add Booking form");
        bookingInfo.forEach((label, value) -> {
           switch(label.toLowerCase()){
               case "firstname":
                   firstname().sendKeys(value);
                   break;
               case "surname":
                   surname().sendKeys(value);
                   break;
               case "price":
                   price().sendKeys(value);
                   break;
               case "deposit":
                   final Select depositWE = new Select(deposit());
                   depositWE.selectByVisibleText(value.toLowerCase(Locale.ROOT));
                   break;
               case "checkin":
                   checkIn().sendKeys(value);
                   break;
               case "checkout":
                   checkOut().sendKeys(value);
                   break;
               default:
                   throw new Error(String.format("No element found with label:{}", label));
           }
        });
        save().click();
    }

    public int getBookingByFirstnameAndSurname(final String expFirstname, final String expSurname){
        waitForElementToDisplay(bookingsTableBy);
        log.info("Expected fn:<{}> sn:<{}>", expFirstname, expSurname);
        Integer bookingRowNo = -1;
        try {
                    bookingRowNo = new WebDriverWait(driver, assertTimeOut)
                    .ignoring(TimeoutException.class)
                    .until(d -> {
                        final List<WebElement> bookingRows = d.findElements(bookingsRowsBy);
                        final int bookingsCount = bookingRows.size();
                        for (int iRowNo = 0; iRowNo < bookingsCount; iRowNo++) {
                            final String firstname = bookingRows.get(iRowNo).findElement(firstnameDataBy).getText();
                            final String surname = bookingRows.get(iRowNo).findElement(surnameDataBy).getText();
                            log.info("fn:<{}> sn:<{}>", firstname, surname);
                            if (firstname.equalsIgnoreCase(expFirstname) && surname.equalsIgnoreCase(expSurname)) {
                                return iRowNo + 1;
                            }
                        }
                        return null;
                    });
        }catch(TimeoutException ex){
            log.info("No booking found for {} {}", expFirstname, expSurname);
        }
        return bookingRowNo != null ? bookingRowNo : Constants.NO_RECORDS_ROW_NO.getVal();
    }

    public void removeBookingByFirstnameandSurname(final String firstname, final String surname){
        final int rowNo = getBookingByFirstnameAndSurname(firstname, surname);
        if(rowNo == Constants.NO_RECORDS_ROW_NO.getVal()){
            throw new Error(String.format("Remove booking failed. No booking found for %s %s", firstname, surname));
        }
        final By deleteBookingsBy = By.cssSelector("div#bookings div[id] input[value='Delete']");
        getElements(deleteBookingsBy).get(rowNo - 1).click();
    }
}

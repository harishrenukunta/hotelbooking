package com.equalexperts.hotelbooking.stepdefs;

import com.equalexperts.hotelbooking.enums.Constants;
import com.equalexperts.hotelbooking.pages.BookingForm;
import com.equalexperts.hotelbooking.pages.ScenarioContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class HomePageStepDef extends BasePageStepDef{

    @Autowired
    private WebDriver driver;

    @Autowired
    private BookingForm bookingForm;

    @Autowired
    ScenarioContext sc;

    @Given("user is on hotel booking home page")
    public void iAmAtHomePage(){
        log.info("I am at home page");
        bookingForm.open();
    }

    @When("^add a booking with below details:$")
    public void addBooking(final DataTable dt){
        log.info("Am about to create booking");
        bookingForm.addBooking(dt.asMaps().get(0));
    }

    @Then("a booking is created with firstname {string} and surname {string}")
    public void verifyBookingByFirstnameAndSurname(final String firstname, final String surname){
        final int bookingRowNo = bookingForm.getBookingByFirstnameAndSurname(firstname, surname);
        Assertions.assertThat(bookingRowNo)
                .as("A booking should be present on '%s %s'", firstname, surname)
                .isNotEqualTo(Constants.NO_RECORDS_ROW_NO.getVal());
    }

    @When("remove booking with firstname {string} and surname {string}")
    public void removeBooking(final String firstname, final String surname){
        bookingForm.removeBookingByFirstnameandSurname(firstname, surname);
        sc.saveData("deleteFirstname", firstname);
        sc.saveData("deleteSurname", surname);
    }

    @Then("above booking should be successfully removed")
    public void checkBookingIsRemoved(){
        final String deletedBookingFirstname = sc.getData("deleteFirstname").toString();
        final String deletedBookingSurname = sc.getData("deleteSurname").toString();
        final int bookingRowNo = bookingForm.getBookingByFirstnameAndSurname(deletedBookingFirstname, deletedBookingSurname);
        Assertions.assertThat(bookingRowNo)
                .as("There should be no booking for %s %s", deletedBookingFirstname, deletedBookingSurname)
                .isEqualTo(Constants.NO_RECORDS_ROW_NO.getVal());

    }

}

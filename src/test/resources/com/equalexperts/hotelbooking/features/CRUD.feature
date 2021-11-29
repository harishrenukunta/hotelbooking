Feature: As a BA
  I would like to be able to create or delete hotel bookings
  so that this can be automated

  Scenario: Add booking
    Given user is on hotel booking home page
    When add a booking with below details:
      | Firstname | Surname | Price | Deposit | CheckIn    | CheckOut   |
      | John      | Doe     | 250   | false   | 2021-11-27 | 2021-11-29 |
    Then a booking is created with firstname 'John' and surname 'Doe'
    When remove booking with firstname 'John' and surname 'Doe'
    Then above booking should be successfully removed
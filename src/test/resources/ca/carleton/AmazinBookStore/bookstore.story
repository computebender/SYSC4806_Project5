Narrative:
As a user, I want to be able to log in with my valid credentials and be redirected to the home page with a welcome message.

Scenario: User successfully logs in with valid credentials
Given I am on the login page
When I enter valid credentials
And click the login button
Then I should be redirected to the home page
And I should see a welcome message

!-- Scenario: User fails to log in with invalid credentials
!-- Given I am on the login page
!-- When I enter invalid credentials
!-- And click the login button
!-- Then I should see an error message on the login page
!--
!-- Scenario: Add book to Shopping Cart
!--
!-- Given a logged in user
!-- When the user clicks the add to cart button
!-- Then the book should be added to the users shopping cart
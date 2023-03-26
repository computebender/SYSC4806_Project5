Narrative:
As a user, I want to be able to log in with my valid credentials and be redirected to the home page with a welcome message.

Scenario: User successfully logs in with valid credentials
Given I am on the login page
When I enter valid credentials
And click the login button
Then I should be redirected to the home page
And I should see a welcome message

Scenario: User fails to log in with invalid credentials
Given I am on the login page
When I enter invalid credentials
And click the login button
Then I should see an error message on the login page

Scenario: User adds book to shopping cart
Given I am on the login page
When I enter valid credentials
When click the login button
Then I should be redirected to the home page
When I click add to cart on a book
When I navigate to the shopping cart
Then I should see a book added in the shopping cart


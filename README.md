# SYSC4806 Project Team 5 - Amazin Online Bookstore
## Project Description
This group project is a web application that provides an online platform for selling and purchasing books. This application allows the Bookstore Owner to upload and edit Book information, including ISBN, picture, description, author, publisher, and inventory. The users can search for, browse through, and sort/filter the books based on the above information. The users can also purchase books by adding them to the Shopping Cart and proceeding to Checkout.
## Team Members
- Alex Cameron
- Josh King
- Ben Munro 
- Thomas Dunnigan
## Installation & Local Deployment
1. Clone the SYSC4806_Project5-main project from Github
2. Run maven install. A .jar file will be created in the project "./SYSC4806_Project5/target/..." directory.
3. Run the following command in command line/terminal: 
`java -jar path/to/your/jarfile/SYSC4806_Project5/target/AmazinBookStore-0.0.1-SNAPSHOT.jar fully.qualified.package.Application`
## Project Build/Deployment Status
[![sysc4806_project5-master Actions Status](https://github.com/computebender/SYSC4806_Project5/actions/workflows/maven.yml/badge.svg)](https://github.com/computebender/SYSC4806_Project5/actions)
## Database Schema
![ER Diagram](https://user-images.githubusercontent.com/8071151/230138065-a8ea9ccc-b099-4e58-916c-7e1a678c64ac.png)


## UML Class Diagram
![alt text](https://github.com/computebender/SYSC4806_Project5/blob/main/documentation/AmazinBookStore_UML_Class_Diagram.png?raw=true)

## Milestone 2 Project Kanban Status
| Todo        | In Progress |                                                               Done |
| :---        |    :----:   |-------------------------------------------------------------------:|
|    |        | Issue https://github.com/computebender/SYSC4806_Project5/issues/11 |
|    |      |  Issue https://github.com/computebender/SYSC4806_Project5/issues/2 |
|    |        |  Issue https://github.com/computebender/SYSC4806_Project5/issues/1 |
|   |        | Issue https://github.com/computebender/SYSC4806_Project5/issues/16 |
|      |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/14 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/10 |
|     |      |  Issue https://github.com/computebender/SYSC4806_Project5/issues/7 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/21 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/27 |
|     |      |  Issue https://github.com/computebender/SYSC4806_Project5/issues/3 |
|     |      |  Issue https://github.com/computebender/SYSC4806_Project5/issues/4 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/19 |
|     |      |  Issue https://github.com/computebender/SYSC4806_Project5/issues/5 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/26 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/26 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/32 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/29 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/34 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/33 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/29 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/36 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/44 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/42 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/43 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/13 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/74 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/55 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/58 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/60 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/69 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/5 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/12 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/76 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/84 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/85 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/89 |
|     |      | Issue https://github.com/computebender/SYSC4806_Project5/issues/100 |



## Milestone 1 Features
- Users can view the main Bookstore user interface
- Book component implementation includes retrieving a list of all books or a single book, creating a new book, deleting a book and updating information about an existing book.
- Author component implementation includes retrieving a list of all authors or a single author, creating a new author, deleting an author or updating information about an existing author. 
- Publisher component implementation includes retrieving a list of all publishers or a single publisher, creating a new publisher or deleting an publisher or updating information about an existing publisher.
- BookStore component implementation includes creating a new bookstore, retrieving information about a bookstore, creating and retrieving many or one listings and adding, updating or removing listings from a bookstore.
## Milestone 2 Project RoadMap:
- User authentication and login will be configured.
- Users can search, browse, sort/filter based on Book information (ISBN, name, description, author, publisher).
- User can select and add or remove books from a shopping cart and proceed to checkout/purchase the book if inventory is not exceeded.
- User can view book recommendations based on user past purchase history and similar purchases from other users.
- Persistent database configuration.
## Milestone 2 Features
- Users can register and login to application.
- Users are authenticated during login process.
- System can create bookstores, listings, books, authors, publishers, genres, and shopping carts.
- User can browse a collecton of books and view the book description.
- User can add a listing to their shopping cart. User can remove items, clear cart or checkout cart.
- User book recommendaton feature implemented using jaccard similarity calculations.
## Milestone 3 RoadMap
- Shopping Cart logic to grey out/prohibit quantity counter buttons when total listings reached.
- Once a user purchases a book, the associated listing quantity needs to be decreased.
- Shopping cart logic to display appropriate banner message if shopping cart is empty.
- Additonal authenticaton/authorization on endpoints.
- Persistent database configuration.
- User book, bookstore, listing configuration user interface
- View interface for search
- Logic to add bought books to purchase history
## Milestone 3 Features
- Shopping Cart feature expanded to allow for user to enter shipping and payment information.
- Shopping cart additional error message handling & listing quantities adjusted based on # of books purchased in checkout.
- Listing card component reflects if 0 copies of a book are available by indicating Out of Stock.
- Ability to create a bookstore and book in the application interface.
- Feature to search for books by filtering by bookstore, book, genre, author or publisher. 

## Environment Configurations
Java Version: Java 17

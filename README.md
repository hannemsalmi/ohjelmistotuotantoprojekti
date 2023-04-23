# Budget calculator app
This application is used for tracking expenses and calculating budget for the remaining month.
The app allows users to add expenses into self created categories and provides graphs and charts to help them understand their spending habits better.

## Team

* Wille Pulkkinen
* Hanne Salmi
* Minna Karhu
* Katja Peltonen

## General info
Desktop application that allows user to add expenses and track their spending. Some features of the application:
* Adding new users including nickname and maximum budget
* Choosing who is using the app
* Adding expenses including name, price, date, category, user and description 
* Adding new categories
* Modifying and removing expenses and categories
* Viewing expenses in chart and filtering expenses by category
* Viewing spending habits through graphs
* List of commonly purhcased items
* List of recurring expenses

## Technologies used:
* **Java**: The programming language used for the project.
* **JavaFX**: A set of libraries which allow the creation of a graphical user interface (GUI) for a desktop app.
* **Maven**: A build automation tool which manages dependencies and builds the app.
* **MariaDB**: A database used for storing the information.
* **Jakarta Persistence (JPA)**: A library which provides an object-relational mapping (ORM) framework for Java.
* **Hibernate**: A framework that simplifies the communication between a program and a database.
* **JUnit 5**: A framework used for unit testing.
* **Mockito**: A mocking framework used for testing.

## Installation and configuration

Installing MariaDB before running the application is necessary.
After installing, you need to create a new database for the expenses with the following credentials:
* Database Name: budjettisovellus
* Username: your_username
* Password: your_password

Update the persistence.xml file located in the META-INF folder with your database credentials:
* property: javax.persistence.jdbc.user -> your_username
* property: javax.persistence.jdbc.password -> your_password

After this the application can be run using Eclipse or other IDE. Class MainView contains the main class of the newest version of the application, so this is the class you should run.

Alternatively the application can be run through command line. However there has been some issues with this, so for now it's better to run the application using IDE.

To run the application through command line, ensure that Maven is installed and added to system's PATH environment variable. Open a command prompt or terminal and navigate to the root directory of the application. Then type the following commands:
*	mvn compile
*	mvn package
*	java -jar target/projekti-0.0.1-SNAPSHOT.jar


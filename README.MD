## UniEvents

### Overview
UniEvents is a Java-based application designed to manage event ticketing. It provides features for event creation, management, ticket sales, and coupon generation.

### Technologies
* **Programming Language:** Java 22
* **Framework:** Spring Boot
* **Build Tool:** Maven
* **User Interface:** JavaFX
* **Persistence:** Serialization

### Features
* **User Management:** Customer registration, login, and account activation.
* **Event Management:**
  * CRUD operations for events (create, read, update, delete)
  * Event search and filtering
  * Locality management with pricing and capacity
* **Coupon System:** Automatic welcome and first-purchase coupons, as well as administrator-created coupons.
* **Purchase Management:** Event search, ticket selection, checkout, coupon application, and purchase confirmation.
* **Email and QR Code Generation:** Sends purchase confirmations and QR codes to users.

### Design Patterns
The application employs several design patterns including Singleton, Observer, Builder, and Factory to enhance code structure and maintainability.

### Notes
The application leverages serialization for data persistence and JavaFX for the user interface.


# Programming II - Final Project

## Students:
 -  Edwin Viña R.
 -  Daniel Garcia

## University: Universidad del Quindío 2024-1


## Project Overview:

    UniEvents is a platform that facilitates ticket sales for concerts and events across Colombia. It caters to both customers and an administrator, offering comprehensive functionalities for managing events, purchasing tickets, and redeeming coupons.

## Generals Aspects

### 1. User Management:

    Customer Registration and Login:
    Customers can register with their personal details (ID, name, phone, email, password)
    Account activation requires email verification with a randomly generated code
    Administrator Login:
    Single administrator account with predefined credentials
### 2. Event Management:

    Administrator can:
    Create new events with details like name, city, description, type, image, date, address, and localities
    Modify existing events
    Search, list, and potentially delete events
### 3. Locality Management:

    Each event can have multiple localities with specific prices and capacity limits
### 4. Coupon System:

    Automatic Coupons:
    Welcome coupon (15% discount) upon registration
    First-purchase coupon (10% discount) after the first purchase
    Administrator-created Coupons:
    Administrator can create coupons for special dates with custom discounts
### 5. Purchase Management:

    Customers can:
    Search events by name, type, or city
    View event details, including localities and prices
    Select event, locality, and quantity of tickets
    Apply coupons (if available) during checkout
    Complete purchase: save purchase details, generate QR code, and receive email confirmation

- Programming Language: Java 21
- Build Tool: Maven
- User Interface: JavaFX
- Persistence: Serialization of objects
- Design Patterns:

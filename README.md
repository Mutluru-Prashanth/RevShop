🛒 RevShop - Enterprise Console-Based E-Commerce Application

RevShop is a secure, console-based Java e-commerce application designed for both buyers and sellers. It follows a layered N-Tier architecture and demonstrates enterprise-level software design using Java, JDBC, Oracle SQL, Log4J, JUnit, and Git.

The system supports product browsing, cart management, order processing, inventory control, and secure authentication with password recovery mechanisms.

=======================================================================================================================================================================================

📊 Entity Relationship Diagram (ERD): 
<img width="8191" height="4108" alt="Entity Relationship diagram" src="https://github.com/user-attachments/assets/4ed2abb1-b081-4642-95a2-692f7c1724ce" />
📌 The ERD represents core entities such as Buyers, Sellers, Products, Orders, Cart, Payments, Reviews, Categories, Notifications, and Security Questions along with primary keys and foreign key relationships.
=======================================================================================================================================================================================

🏛 Application Architecture (Layered / N-Tier Design)

![Application architecture](https://github.com/user-attachments/assets/993bf15a-5785-45a7-93eb-4511378a28e9)

📌 RevShop follows a modular layered architecture to ensure scalability, maintainability, and separation of concerns:

🔹 Architecture Layers

Presentation Layer (Console UI)

BuyerProductMenu

SellerProductManagement

BuyerSellerRegistration

Service Layer (Business Logic)

BuyerProductService

ProductService

OrderService

RegistrationService

DAO Layer (Data Access Layer)

BuyerProductDao

ProductDao

OrderDao

RegistrationDao

ReviewsDao

NotifyDao

DTO / Model Layer

BuyerDTO, SellerDTO, ProductDTO, OrderDTO

CartDTO, PaymentDTO, ReviewDTO, NotificationDTO, CategoryDTO

Database Layer

Oracle SQL Database (Oracle SQL Developer)

JDBC Connectivity

Cross-Cutting Concerns

Logging: Log4J

Testing: JUnit

Version Control: Git
=======================================================================================================================================================================================
🏗 Project Structure:
```
revshop/
├── ERD/                        # ERD diagrams
├── logs/                       # Application logs
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Dao/
│   │   │   │   ├── Impl/
│   │   │   │   │   ├── BuyerProductDaoImpl
│   │   │   │   │   ├── NotifyDaoImpl
│   │   │   │   │   ├── OrderDaoImpl
│   │   │   │   │   ├── ProductDaoImpl
│   │   │   │   │   ├── RegistrationDaoImpl
│   │   │   │   │   └── ReviewsDaoImpl
│   │   │   │   ├── BuyerProductDao
│   │   │   │   ├── NotifyDao
│   │   │   │   ├── OrderDao
│   │   │   │   ├── ProductDao
│   │   │   │   ├── RegistrationDao
│   │   │   │   └── ReviewsDao
│   │   │   ├── Dto/
│   │   │   │   ├── BuyerDTO
│   │   │   │   ├── CartDTO
│   │   │   │   ├── CartItemsDTO
│   │   │   │   ├── CategoryDTO
│   │   │   │   ├── FavouritesDTO
│   │   │   │   ├── LoginDTO
│   │   │   │   ├── NotificationDTO
│   │   │   │   ├── OrderAddressDTO
│   │   │   │   ├── OrderDTO
│   │   │   │   ├── OrderItemDTO
│   │   │   │   ├── PaymentDTO
│   │   │   │   ├── ProductDTO
│   │   │   │   ├── ReviewDTO
│   │   │   │   ├── SecurityQuestionDTO
│   │   │   │   ├── SellerDTO
│   │   │   │   └── SellerOrderDTO
│   │   │   ├── service/
│   │   │   │   ├── Impl/
│   │   │   │   │   ├── BuyerProductServiceImpl
│   │   │   │   │   ├── OrderServiceImpl
│   │   │   │   │   ├── ProductServiceImpl
│   │   │   │   │   └── RegistrationServiceImpl
│   │   │   │   ├── BuyerProductService
│   │   │   │   ├── OrderService
│   │   │   │   ├── ProductService
│   │   │   │   └── RegistrationService
│   │   │   ├── presentation/
│   │   │   │   ├── BuyerProductMenu
│   │   │   │   ├── BuyerSellerRegistration
│   │   │   │   └── SellerProductManagement
│   │   │   ├── enumeration/
│   │   │   │   └── DBConnection
│   │   │   └── Main/
│   │   │       └── RevShopFile
│   ├── test/
│   │   └── java/
│   │       └── service/
│   │           ├── BuyerProductServiceImplTest
│   │           ├── OrderServiceImplTest
│   │           ├── ProductServiceImplTest
│   │           └── RegistrationServiceImplTest
├── pom.xml                     # Maven configuration
├── .gitignore
└── README.md
```

🔄 Working Flow
1️⃣ Initialization & Authentication

App Start: RevShopFile initializes the database connection using JDBC and loads the main console menu.
Role Assignment: During registration, users choose between BUYER or SELLER roles.
Login: RegistrationService validates credentials against the Oracle database and routes users to their respective dashboards.
Password Recovery: Users can recover forgotten passwords using security questions or password hints.

2️⃣ Buyer Workflow (The Customer)

Product Discovery: Buyers browse products by category or perform keyword search using BuyerProductService.
Product Details: Users view product price, description, reviews, and ratings.
Cart Management: Add or remove products from the cart with quantity updates.
Checkout Process: Enter shipping and billing address and proceed to checkout.
Payment Simulation: Simulated payment processing using PaymentDTO.
Order Management: View order history and order status.
Personalization: Save products as favorites and submit product reviews and ratings.
Notifications: Receive in-app notifications when orders are placed.

3️⃣ Seller Workflow (The Vendor)

Seller Registration: Sellers register with email, password, and business details.
Product Management: Add, update, and delete products using ProductService.
Pricing Management: Set MRP and discounted price for products.
Inventory Control: Monitor stock levels and receive console alerts when stock is low.
Order Tracking: View all orders placed for their products.
Review Monitoring: View buyer reviews and ratings for their products.
Notifications: Receive alerts when buyers place new orders.
=======================================================================================================================================================================================
✨ Enterprise Features

🚀 Transactional Integrity: Critical operations like order placement and inventory updates use atomic JDBC transactions to prevent data inconsistency.

🔍 Product Search Engine: Unified search functionality for products by name, category, and keywords.

🛡 Secure Authentication: Password recovery using security questions and duplicate email validation during registration.

📦 Inventory Threshold Alerts: Console alerts when product stock reaches minimum threshold levels.

⭐ Review & Rating System: Buyers can rate and review purchased products.

🔔 Notification System: Buyers and sellers receive real-time in-app notifications for orders and system events.

📜 Professional Logging: Integrated Log4J for logging application activities, errors, and debugging information.

🧪 Unit Testing: Core business logic tested using JUnit test cases.
=======================================================================================================================================================================================
🛠 Technology Stack

Language: Java (Core Java & OOP)
Database: Oracle SQL Developer (Relational Database)
Persistence: JDBC (Java Database Connectivity)
Testing: JUnit (Unit Testing Framework)
Build System: Maven
Logging: Apache Log4J
Version Control: Git
=======================================================================================================================================================================================

�📝 About the Creator
Crafted by Mutluru Prashanth to demonstrate scalable system design, modular architecture, and professional Java development practices.

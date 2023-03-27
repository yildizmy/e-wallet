## How to test?

### Open API (Swagger) UI

For Open API (Swagger) Documentation UI and the endpoints, visit http://localhost:8080/swagger-ui/index.html after running the app.

<br/>

### Postman Collection

The [Postman Collection](postman/e-wallet.postman_collection.json) shared in the resources can be modified and used
for testing the endpoints.

<br/>

### API Endpoints

> **Note** <br/>
> All URIs are relative to *http://localhost:8080/api/v1*

<br/>

| Class            | Method                                          | HTTP request   | Description                                           |
|------------------|-------------------------------------------------|----------------|-------------------------------------------------------|
| *AuthController* | [**login**](http://localhost:8080/api/v1/auth)  | **POST** /auth | Authenticates users by their credentials              |
| *AuthController* | [**signup**](http://localhost:8080/api/v1/auth) | **POST** /auth | Registers users using their credentials and user info |

<br/>
<br/>

| Class           | Method                                                                     | HTTP request                                | Description                                             |
|-----------------|----------------------------------------------------------------------------|---------------------------------------------|---------------------------------------------------------|
| *WalletController* | [**findById**](http://localhost:8080/api/v1/wallets/{id})                  | **GET** /wallets/{id}                       | Retrieves a single wallet by the given id               |
| *WalletController* | [**findByIban**](http://localhost:8080/api/v1/wallets/iban/{iban})         | **GET** /wallets/iban/{iban}                | Retrieves a single wallet by the given iban             |
| *WalletController* | [**findAllByUserId**](http://localhost:8080/api/v1/wallets/users/{userId}) | **GET** /wallets/users/{userId}             | Retrieves all wallets based on the given userId         |
| *WalletController* | [**findAll**](http://localhost:8080/api/v1/wallets)                        | **GET** /wallets?page=0&size=10&sort=id,asc | Retrieves all wallets based on the given parameters     |
| *WalletController* | [**create**](http://localhost:8080/api/v1/wallets)                         | **POST** /wallets                           | Creates a new wallet using the given request parameters |
| *WalletController* | [**transferFunds**](http://localhost:8080/api/v1/wallets/transfer)                 | **POST** /wallets/transfer                          | Transfer funds between wallets                          |
| *WalletController* | [**addFunds**](http://localhost:8080/api/v1/wallets/addFunds)                 | **POST** /wallets/addFunds                          | Adds funds to the given wallet of the user              |
| *WalletController* | [**withdrawFunds**](http://localhost:8080/api/v1/wallets/withdrawFunds)                 | **POST** /wallets/withdrawFunds                          | Withdraws funds from the given wallet of the user       |
| *WalletController* | [**update**](http://localhost:8080/api/v1/wallets)                         | **PUT** /wallets                            | Updates wallet using the given request parameters       |
| *WalletController* | [**deleteById**](http://localhost:8080/api/v1/wallets/{id})                | **DELETE** /wallets                         | Deletes wallet by id                                    |

<br/>
<br/>

| Class           | Method                                                                                              | HTTP request                                | Description                                                  |
|-----------------|-----------------------------------------------------------------------------------------------------|---------------------------------------------|--------------------------------------------------------------|
| *TransactionController* | [**findById**](http://localhost:8080/api/v1/transactions/{id})                                      | **GET** /transactions/{id}                       | Retrieves a single transaction by the given id               |
| *TransactionController* | [**findByReferenceNumber**](http://localhost:8080/api/v1/transactions/references/{referenceNumber}) | **GET** /transactions/references/{referenceNumber}                | Retrieves a single transaction by the given reference number |
| *TransactionController* | [**findAllByUserId**](http://localhost:8080/api/v1/transactions/users/{userId})                     | **GET** /transactions/users/{userId}             | Retrieves all transactions based on the given userId         |
| *TransactionController* | [**findAll**](http://localhost:8080/api/v1/transactions)                                            | **GET** /transactions?page=0&size=10&sort=id,asc | Retrieves all transactions based on the given parameters     |

<br/>
<br/>

### Unit & Integration Tests
Unit and Integration Tests will be provided for services and controllers in the corresponding packages.

<br/>
<br/>
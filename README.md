# Order Management - Agent app

#### we need to set below property to true to load system credentials into DB
```shell
system.startup-event.enable=true
```

#### access rest APIs using swagger-ui
http://localhost:8080/dinehouse/api/v1/swagger-ui/index.html

### login request and response
```shell
http://localhost:8080/dinehouse/api/v1/login
{
  "userId": "admin",
  "pwd": "admin"
}
{
  "status": "SUCCESS",
  "data": {
    "token": "b1f88eec-4472-4bc1-96ce-1ec14b295a5e",
    "lifeTime": 360,
    "userId": "admin",
    "expiresOn": "2022-12-31T07:33:47.300+00:00"
  }
}
```

#### BaseInfo API 
```shell
http://localhost:8080/dinehouse/api/v1/baseInfo
{
  "status": "SUCCESS",
  "data": {
    "locations": [
      {
        "id": 0,
        "name": "TAB001",
        "status": true,
        "type": "dinein",
        "createdOn": "2023-01-02T23:43:38.000+00:00"
      }
    ],
    "items": [
      {
        "id": 193,
        "name": "Sweet Corn Soup",
        "status": "ACTIVE",
        "categoryId": 1,
        "categoryName": "Starters",
        "price": 100,
        "userId": "admin",
        "createdOn": "2022-12-31T01:48:06.000+00:00",
        "veg": true
      }
    ],
    "categories": [
      {
        "id": 1,
        "name": "Starters",
        "status": true,
        "createdOn": "2022-12-31T01:48:06.000+00:00"
      }
    ],
    "paymentMethods": [
      "PhonePe",
      "ZomatoPay",
      "Cash",
      "Card",
      "Paytm",
      "Pending"
    ],
    "tranGroups": [
      "Wages",
      "Rent",
      "Chicken",
      "Gas",
      "Vegetables",
      "Grocery",
      "Rice",
      "Maintenance",
      "AdvancePay",
      "Other",
      "Order"
    ],
    "servers": [
      "misc misc"
    ]
  }
}
```

#### Create order example 
```shell
POST - request
http://localhost:8080/dinehouse/api/v1/order
{
  "userId": "admin",
  "status": "OPEN",
  "type": "DINEIN",
  "address": "TAB001",
  "price": 300,
  "discount": 0,
  "payableAmount": 300,
  "discAmount": 0,
  "description": "Dinehouse Biryani",
  "orderItems": [
    {
      "itemId": 283,
      "quantity": 1,
      "price": 300,
      "status": "ACTIVE"
    }
  ]
}
```

#### Order update
```shell
PUT request 
http://localhost:8080/dinehouse/api/v1/order
{
  "id":100,
  "userId": "admin",
  "status": "OPEN",
  "type": "DINEIN",
  "address": "TAB001",
  "price": 600,
  "discount": 0,
  "payableAmount": 600,
  "discAmount": 0,
  "description": "Dinehouse Biryani",
  "orderItems": [
    {
      "itemId": 283,
      "quantity": 2,
      "price": 300,
      "status": "ACTIVE"
    }
  ]
}
```

#### Order list
```shell
Get - Orders - /dinehouse/api/v1/orders/{userId}
{
  "status": "SUCCESS",
  "data": [
    {
      "id": 1,
      "userId": "admin",
      "status": "PAID",
      "type": "DINEIN",
      "address": "T-A-01",
      "price": 200,
      "payableAmount": 200,
      "orderItems": [
        {
          "id": 1,
          "orderId": 1,
          "createdOn": "2023-01-10T13:29:03.000+00:00",
          "itemId": 1,
          "itemName": "Sweet Corn Soup",
          "quantity": 1,
          "price": 100,
          "offerId": null,
          "status": "ACTIVE"
        },
        {
          "id": 2,
          "orderId": 1,
          "createdOn": "2023-01-10T13:29:03.000+00:00",
          "itemId": 3,
          "itemName": "Manchow Soup",
          "quantity": 1,
          "price": 100,
          "status": "ACTIVE"
        }
      ]
    }
  ]
}
```

#### Get The Bill
```shell
Get - /bill/{orderId}

Response 
{
  "status": "Success",
  "data": true
}
```

#### Order payment
```shell
POST - dinehouse/api/v1/order-payment

Request
{
  "userId": "{userId}",
  "orderId": "3",
  "method": "PhonePe",
  "amount": 2000,
  "description": "Partial payment"
}

Response -
{
  "status": "SUCCESS",
  "data": {
    "id": 4,
    "userId": "admin",
    "orderId": "3",
    "paymentMethod": "PhonePe",
    "tranGroup": "Order",
    "type": "Cr",
    "amount": 2000,
    "description": "Partial payment - actual amount to pay 2200.0 but paid 2000.0"
  }
}
```

####  expenses capture
```shell 
POST - dinehouse/api/v1/expenses
[
  {
    "userId": "{userId}",
    "method": "Cash",
    "type": "Wages",
    "amount": 3600,
    "description": "Chefs and Cleaning staff"
  }
]

Response 
{
  "status": "SUCCESS",
  "data": [
    {
      "id": 3,
      "userId": "admin",
      "paymentMethod": "Cash",
      "tranGroup": "Wages",
      "type": "Dr",
      "amount": 3600,
      "description": "Chefs and Cleaning staff"
    }
  ]
}
```

```shell
mkdir /Users/[your_username]/Develop
mkdir /Users/[your_username]/Develop/mysql_data
mkdir /Users/[your_username]/Develop/mysql_data/8.0
```

```shell
docker network create dev-network
```

```shell
docker run --restart always --name mysql8.0 \
    --net dev-network -v /Users/[your_username]/Develop/mysql_data/8.0:/var/lib/mysql \
    -p 3306:3306 \
    -d -e MYSQL_ROOT_PASSWORD=Winthegame mysql:8.0
    
```

https://medium.com/@crmcmullen/how-to-run-mysql-in-a-docker-container-on-macos-with-persistent-local-data-58b89aec496a

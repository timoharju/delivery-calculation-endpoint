# Delivery calculation service

Simple service for calculating delivery fee for a single delivery using a single POST endpoint. <br>
This service was built with Spring Boot Framework and Kotlin.

The server can be run locally with provided gradle wrapper. Requires Java 17<br>
```
./gradlew bootRun
```

API Documentation for can be found [here](#api-reference) <br><br>
Dependencies and libraries used in the project can be found [here](#project-dependencies-and-libraries)

## Project Dependencies and Libraries

- **Gradle**: Chosen automation and library management tool.
- **Kotlin**: Primary programming language.
- **Spring Boot**: Framework for creating java based applications, particularly web apps, with minimal setup.
- **Kotlinx serialization**: Converts the application data to processable data to a format that can be transferred over a network.

_Note: Version details for each dependency can be found in the project's `build.gradle` file._


## API Reference

```
POST /v1/delivery/calculate
``` 
Calculates the cost of a single delivery using following parameters.
### Parameters
```
cart_value: Integer (Required)
This field represents the total value of the shopping cart, measured in cents.

delivery_distance: Integer (Required)
The distance in meters from the store to the customer's location.

number_of_items: Integer (Required)
The total number of items in the customer's shopping cart.

time: String (Required)
The time when the order was placed, provided in UTC and formatted in ISO 8601 standard.
```
### Example Request
```
{
"cart_value": 790, 
"delivery_distance": 2235, 
"number_of_items": 4, 
"time": "2024-01-15T13:00:00Z"
}
```

### Response
Returns the price in cents if the call is successful.
```
delivery_free: Integer
```
```
{
"delivery_fee": 710
}
```
Expected status codes:
```
200	OK	        The call was successful.
400	Bad Request	Something went wrong with the request. 
404	Not Found	The requested end point was invalid.
```

### Testing

To run all the unit and integration tests:

```
./gradlew test 
```

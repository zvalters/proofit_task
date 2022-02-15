## Task Desciption
Bus charter company wants to provide new service for travel agencies – draft ticket price.

To receive draft ticket price, following data must be provided:
- List of passengers
- List of luggage items of each passenger

Draft ticket price is calculated as following:
1. Base price for an adult is provided by already existing service returning number from
   database based on given route (bus terminal name).
2. Child passengers receive 50% discount.
3. Luggage price is 30% of base price.
4. Tax rates are provided by already existing service, which provides list of percentage
   rates on given day of purchase.

The result of calculation should contain both total price and prices for each individual item.

## Running the solution
The application is currently deployed to [`https://proofit.herokuapp.com/`](https://proofit.herokuapp.com/)  
Also, swagger is active and can be inspected under [`https://proofit.herokuapp.com/swagger-ui.html`](https://proofit.herokuapp.com/swagger-ui.html)

The application can be launched locally by running the following command in the root directory:
```text
mvn spring-boot:run
```
Spring will launch the application and it will be available under `http://localhost`

## Implementation
The draft ticket price can be obtained by sending a POST request to `/ticketing/draft` with `Content-Type: application/json` and request body:
```json
{
  "passengers": [
    {
      "destinationTerminalName": "Vilnius",
      "ageGroup": "ADULT",
      "luggageAmount": 2
    },
    {
      "destinationTerminalName": "Vilnius",
      "ageGroup": "CHILD",
      "luggageAmount": 1
    }
  ]
}
```

#### Request properties explained
* `passegers`: Array of passengers to calculate the ticket for
* `destinationTerminalName`: The destination terminal of the passenger
* `ageGroup`: An enum describing the age of the passenger. (ADULT or CHILD)
* `luggageAmount`: The amount of luggage the passenger will take with him/her on the journey

The response for the above request would be:
```json
{
  "total": 29.04,
  "currency": "EUR",
  "items": [
    {
      "description": "Ticket for adult",
      "cost": 12.10
    },
    {
      "description": "2 bag(s)",
      "cost": 7.26
    },
    {
      "description": "Ticket for child",
      "cost": 6.05
    },
    {
      "description": "1 bag(s)",
      "cost": 3.63
    }
  ]
}
```
#### Response properties explained
* `total`: The total to be paid for the requested journey with provided parameters
* `currency`: The currency the prices are stated in. Always returns EUR
* `items`: An array of items the ticket price consists of with their `description` and `cost` individually specified


### External APIs
The base price and tax rate web services have been mocked and will always return the same result for all calls.  
To connect a real API, delete the [`MockServerConfiguration`](src/main/java/lv/proofit/ticketing/rest/MockServerConfiguration.java) and populate [`application.yml`](src/main/resources/application.yml) `rest` properties with correct URIs.

#### Assumed endpoints
##### Tax rate service
The application is making a GET request to `/taxes` and always receives:
```json
{ "taxRates": [0.21] }
```
##### Base price service
The application is making a GET request to `/pricing/{terminal}` with `{terminal}` being substituted with the appropriate terminal and always receives:
```json
{ "price": 10 }
```

### Checkstyle
The codebase uses checkstyle to keep the code tidy. The used rules can be found in [`checkstyle.xml`](code/checkstyle.xml). It is derived from Google Java Style rules with slight alterations.  
To check if the codebase passes checkstyle rules locally run:
```text
mvn checkstyle:check
```

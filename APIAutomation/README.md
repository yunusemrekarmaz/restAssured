# Project Information
@Author: Yunus Emre Karmaz

@Java Version : 17

-----------------------------------------------------------------

## Essential Dependencies->

@junit 4.13.2

@io.rest-assured 5.3.0

@io.cucumber 7.12

----------------------------------------------------------------
## Run Test

Run tests locally in 2 ways
1. Right click the feature file and select "Run" or "Debug" to start the test.
2. Right click in RunnerTest class and select "Run" or "Debug" to start the test.

----------------------------------------------------------------
## Test Flow

1. Starting to create BaseToken in Feature File
2. Passing into first step of the scenario to call Booking create method
3. Calling request via RestUtil class in which has got required objects and controls for RestAssured Test
4. Passing into respectively steps of the scenario to test Booking microservice and its owns methods ,by end to end
5. Checking Status code, Validating JSON Body Object and its own values, by the help of RestUtil class.
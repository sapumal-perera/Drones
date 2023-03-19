# Drones Application

This application allows you to manage drones and their medications.

## Getting Started

To get started with the application, clone the repository and navigate to the project directory. The application is built using Java and Spring Boot, so you'll need to have Java installed on your machine.

### Prerequisites

* Java JDK 8 or higher
* Apache Maven

### Installation

1. Clone the repository: `git clone https://github.com/sapumal-perera/Drones.git`
2. Navigate to the project directory: `cd Drones`
3. Build the project: `mvn clean install`
4. Run the project: `java -jar target/Drones.jar`

The application will start running on `http://localhost:8080`.

###Swagger documentation is available on http://localhost:8080/swagger-ui/index.html


## Usage

### Adding a Drone

To add a drone, send a POST request to `/drones` with the following payload:

```json
{
  "serialNumber": "001",
  "model": "HEAVYWEIGHT",
  "weightLimit": 400,
  "batteryCapacity": 30,
  "state": "IDLE"
}
```
###### Allowed drone models:
    LIGHTWEIGHT
    MIDDLEWEIGHT
    CRUISERWEIGHT
    HEAVYWEIGHT

###### Allowed drone states:     
    IDLE
    LOADING
    LOADED
    DELIVERING
    DELIVERED
    RETURNING

### Viewing Drone Battery Capacity

To view battery capacity send a GET request to `/drones/{serialNumber}/battery` 

### Viewing available drones

To view available drones send a GET request to `/drones/available` 

### Viewing all drones

To view available drones send a GET request to `/drones/all`

### Viewing medication in a drone

To view medication in a drone send a GET request to `/drones/{serialNumber}/medication`

### To add medication to drone

To add medication in to a drone send a PUT request to `/drones/{serialNumber}/medication` with the following payload:

```json
{
  "name": "medication1",
  "weight": 50,
  "code": "MED002",
  "image": "http://example.com/image1.jpg"
} 
```
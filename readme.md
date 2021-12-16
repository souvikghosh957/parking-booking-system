**[Parking Booking System - Deployed in Heroku]**

***Tech Stack:*** 
Java, Spring Boot, REST API, Rest Template, MySQL(local), PostgreSQL(Heroku), Heroku for deployment, Spring Security, JPA, Devtool, Postman, Eclipse, MySQL Workbench, Gitbash
***Application Flow***
Register a User -> Add Parking Locations -> Get All Location -> Get Location by name -> Get Location By District (Bangalore) -> Update Location -> Remove Location->Book Ticket (Option to book in recurring  days, weeks and months.
**System Overview**
As per requirement a user with only admin access can add, remove, update a parking location. Both user with admin and user role can view a particular location, or can get locations by district name and book ticket if slot is available. Booking time should be always future date and entry time should be before exit time. Application is deployed in heroku and all sample request and response is provided below:
**Advanced Requirements**
Due to time constrains those feature is not implemented in the code.
1) We can integrate any external parking booking system easily as this application already talks to the external Govt. Post  Office API with url https://api.postalpincode.in/pincode/560037
2) We can scale the system vertically by implementing API gateway and ribbon for load balancing.
3) For invoice, after booking a ticket successfully we can charge according to the time and location and generate an invoice.
4) For security application currently using Spring Security
***How the ticket booking works?***
Once the API gets a request from a registered user:
1) It checks if requested time a future time and entry time is before the exit time.
2) Then it checks if the requested location is available in the system.
3) Finally it checks if the requested time slot is available for the location.
Once it pass all the checks it book the slot for the requested user. In the request optionally a user can choose to book the slot in recurring days/weeks/months.

***Request, Response and Database Schema:***
***Register a User:***
**No Authentication required for this api.
URL: 
POST: https://arcane-fortress-65741.herokuapp.com/user/registerUser
Request:
{
    "userId" : "navin",
    "password": "1234",
    "userRole": "ROLE_USER",
    "userFullName": "Navin G",
    "email": "nv@gmail.com",
    "phone": "8889799888",
    "userLocation": "Bengaluru"
}
Response:
{
    "message": "User created success fully"
}
**note: All below URL is accessible after successful authentication by a registered user. So user needs to be registered first. Its uses Basic Authentication. All the sample test was done from Postman.
The username and password is passed in the header from postman. 
***Add Location:***
URL: POST: https://arcane-fortress-65741.herokuapp.com/parking/admin/addParkingLocations
Request:
[
    {
            "area": "Agaram",
            "pincode": "560 007",
            "latitude": "12.8431",
            "longitude": "77.4863"
        },
        {
            "area": "Air Force Stn. Yelahanka",
            "pincode": "560 063",
            "latitude": "13.1048",
            "longitude": "77.5763"
        }
]
Response:
{
    "message": "Below locations added successfully.",
    "updatedIdAreaMap": {
        "2126199578": "Agaram",
        "1661588951": "Air Force Stn. Yelahanka"
    }
}
***Get All Locations/ All location in a district:***
URL1: GET: https://arcane-fortress-65741.herokuapp.com/parking/getAllLocations
URL2:GET:  https://arcane-fortress-65741.herokuapp.com/parking/getAllLocations?district=Bangalore
Response:
{
    "message": "All available location in district Bangalore",
    "parkingSpots": [
        {
            "id": "377409551",
            "area": "Chamrajpet West",
            "pincode": "560018",
            "latitude": 12.9586,
            "longitude": 77.5634,
            "district": "Bangalore"
        },
        {
            "id": "1661588951",
            "area": "Air Force Stn. Yelahanka",
            "pincode": "560063",
            "latitude": 13.1048,
            "longitude": 77.5763,
            "district": "Bangalore"
        },
        {
            "id": "353655812",
            "area": "Banashankari",
            "pincode": "560050",
            "latitude": 12.925453,
            "longitude": 77.54676,
            "district": "Bangalore"
        }
]
}
***Get location by location name/pincode/locationId:***
Url1: Get: https://arcane-fortress-65741.herokuapp.com/parking/getParkingLocation?areaName=Banashankari
Url2: Get: https://arcane-fortress-65741.herokuapp.com/parking/getParkingLocation?pincode=560050
Url3: Get:  https://arcane-fortress-65741.herokuapp.com/parking/getParkingLocation?locationId=353655812
Response:
{
    "message": "Find detailed location below: ",
    "parkingSpots": {
            "id": "353655812",
            "area": "Banashankari",
            "pincode": "560050",
            "latitude": 12.925453,
            "longitude": 77.54676,
            "district": "Bangalore"
    }
}
***Update a Location:***
URL:
PUT: https://arcane-fortress-65741.herokuapp.com/parking/getParkingLocation?locationId=1661588951
Request:
 {
            "area": "Agaram",
            "pincode": "560007",
            "latitude": 12.972442,
            "longitude": 77.0000,
            "district": "Bangalore"
        }
Response:
{
    "message": "Location updated successfully.",
    "idAreaMap": {
        "2126199578": "Agaram"
    }
}
***Remove a Location***
URL:
Delete: https://arcane-fortress-65741.herokuapp.com/parking/admin/removeLocations/Agaram
Response:
{
    "message": "Below location is deleted successfully.",
    "idAreaMap": {
        "2126199578": "Agaram"
    }
}
***Book Ticket:***
URL: 
POST: https://arcane-fortress-65741.herokuapp.com/parking/bookTicket
Request:
{
    "parkingArea": "Chamrajpet West",
    "bookFrom": "2021-12-16T20:11:00+05:30",
    "bookTo": "2021-12-16T20:15:00+05:30",
    "isDailyRecurring": false,
    "numberOfDays": 2,
    "isWeeklyRecurring": false,
    "numberOfWeeks": 5,
    "isMonthlyRecurring": false,
    "numberOfMonths": 5
}
Response:
{
    "message": "Your ticket has been booked! ",
    "bookingDetails": [
        {
            "bookingId": "302",
            "bookedTime": "Thu Dec 16 20:11:00 IST 2021 to Thu Dec 16 20:15:00 IST 2021",
            "location": "Chamrajpet West"
        }
    ]
}
***Book Ticket For recurring date:***
URL: 
POST: https://arcane-fortress-65741.herokuapp.com/parking/bookTicket
Request:
{
    "parkingArea": "Chamrajpet West",
    "bookFrom": "2021-12-16T21:16:00+05:30",
    "bookTo": "2021-12-16T21:55:00+05:30",
    "isDailyRecurring": true,
    "numberOfDays": 2,
    "isWeeklyRecurring": false,
    "numberOfWeeks": 5,
    "isMonthlyRecurring": false,
    "numberOfMonths": 5
}
Response:
{
    "message": "Your ticket has been booked! ",
    "bookingDetails": [
        {
            "bookingId": "304",
            "bookedTime": "Thu Dec 16 21:16:00 IST 2021 to Thu Dec 16 21:55:00 IST 2021",
            "location": "Chamrajpet West"
        },
        {
            "bookingId": "305",
            "bookedTime": "Fri Dec 17 21:16:00 IST 2021 to Fri Dec 17 21:55:00 IST 2021",
            "location": "Chamrajpet West"
        },
        {
            "bookingId": "306",
            "bookedTime": "Sat Dec 18 21:16:00 IST 2021 to Sat Dec 18 21:55:00 IST 2021",
            "location": "Chamrajpet West"
        },
        {
            "bookingId": "307",
            "bookedTime": "Sun Dec 19 21:16:00 IST 2021 to Sun Dec 19 21:55:00 IST 2021",
            "location": "Chamrajpet West"
        }
    ]
}

***When time slot is not available for a particular location:***
Response:
{
    "message": "The entered time slot is not available. Choose other time for this location.",
    "bookingDetails": null
}
***When entered time is a past time or chronology is not correct:***
Response:
{
    "message": "Entered times are either past time or not in correct chronology.",
    "bookingDetails": null
} 

***Database Schema:**
![enter image description here][1]{:target='_blank'}


  [1]: https://he-s3.s3.amazonaws.com/media/uploads/5c821a5.PNG
# MoussafirApp
This Application is a Booking App, that allows the user to book a bus ticket for traveling, the user can see available trips, choose destination and departure points,
manage his tickets, and view trip details.
The app was made with Kotlin & Jetpack compose, in addition to some android libraries & Kotlin features such as Navigation Component, Retrofit, Coroutines, ...

# Features
The app includes the following features:

- Authentication: Users can create profiles, sign-in, sign-up, and verify their phone numbers.

- Browse available trips: Users can view a list of available trips with information about the departure time, arrival time, duration, and price.

- Book tickets: Users can select a trip, choose the desired date and time of departure, and purchase tickets for the trip. The app uses Retrofit and a REST API to communicate with a backend server to handle the booking process.

- View booked tickets: Users can view a list of their booked tickets and see details about each trip, including the departure time, arrival time, duration, and price.

- Cancel booked tickets: Users can cancel any booked tickets they no longer need, and the app will handle the refund process.

- See nearby bus stations on a map: Users can view a map of their current location with markers for nearby bus stations. The app uses the Google Maps API to show the map and markers.

- Track the location of the bus for the next reserved trip: Users can track the real-time location of the bus for the next reserved trip on a map.

# Technologies Used:

- Kotlin: A modern programming language used for developing Android apps.

- Jetpack Compose: A modern toolkit for building native Android UIs with declarative Kotlin code.

- Retrofit: A type-safe HTTP client for Android and Java that makes it easy to consume RESTful web services.

- REST API: A web service that uses HTTP requests to GET, POST, PUT, and DELETE data.

- MVVM Architecture: A design pattern used for building Android apps that separates the business logic and presentation layers.

- Google Maps API: A service that provides access to Google's mapping technology and allows developers to embed maps into their applications.

- Firebase Phone Auth: a service that provides phone authentication and SMS codes.

- Coroutines: A library for writing asynchronous code that is more concise and easier to read than traditional callbacks or threads.

- Flows: A reactive stream library that allows developers to write asynchronous, non-blocking code that can react to changes in data.

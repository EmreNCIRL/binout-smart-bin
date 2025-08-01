BinOut – Smart Waste Management System

A simple distributed system project built in Java using gRPC and Swing, developed for a Distributed Systems course.
Overview

BinOut provides three core services to help users access and manage bin pickup schedules and recycling tips:

    User Profile Service: Retrieves user profiles with details like city and bin service provider.

    Bin Schedule Service: Offers bin pickup schedules by city and supports uploading or updating schedules via streaming.

    Recycling Advisor Service: Provides recycling tips based on bin color/type.

The client GUI lets users query these services interactively by entering user IDs, city names, or bin types, and displays the results in a straightforward interface.
Architecture and Features

    Services implemented in Java using gRPC with all four RPC types:

        Unary RPC (simple request/response)

        Server-side streaming

        Client-side streaming

        Bidirectional streaming

    Service registration and discovery handled via jmDNS for dynamic location of services.

    Basic authentication with metadata tokens in gRPC calls.

    Minimal error handling with clear messaging on missing data.

    GUI built using Swing with three panels to interact with each service:

        User profile lookup

        Bin schedule retrieval

        Recycling tips

Technology Stack

    Java 17

    gRPC and Protobuf 3

    jmDNS for service discovery

    Swing for GUI

    Maven for build and dependency management

How to Run

    Start the server on port 50051. It registers all three services via jmDNS.

    Launch the Swing client to connect to the server locally.

    Use the GUI to enter inputs and fetch service data.

Notes

    Data is stored in-memory with some hardcoded initial values for demo purposes.

    The app focuses on meeting assignment requirements with clear, basic implementations rather than advanced features.

    Supports all required RPC types and service registration but keeps logic simple to ensure clarity and ease of testing.

Author

Emre Ketme
Distributed Systems CA
x24191779@student.ncirl.ie
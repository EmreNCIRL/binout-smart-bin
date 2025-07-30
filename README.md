#binout-smart-bin

A distributed smart bin reminder app using Java, gRPC, and Swing – for Distributed Systems CA – NCIRL
#BinOut – Smart Bin Reminder App

Hi! This is a small project I built for my Distributed Systems module at NCI.
The goal is to help people stay on top of their bin collection days using a lightweight, automated setup.

It supports the United Nations SDG 11: Sustainable Cities and Communities by encouraging better waste‑management habits through a small‑scale automated environment.
#What It Does

    Lets users enter their profile (provider, city, zone, bin types)

    Stores profiles and bin schedules in separate services

    Simulates collection schedules with hardcoded dates

    “Next pickup” buttons show tomorrow’s date or “N/A”

    Simple Swing‑based GUI for registration and lookup

#Tech Stack

    Java 17 for all services and client

    gRPC (Protobuf 3 + grpc‑java) for inter‑service RPC

    Swing for the GUI

    Maven for build, Protobuf codegen, and execution

#Services

    Service Registry – dynamic discovery and registration of services

    User Profile Service – stores userId, name, email, bin provider, city/zone, bin types

    Bin Schedule Service – manages/retrieves bin pickup schedules

#GUI Overview

    Profile pane: enter name, email, provider, city, zone, and select bin types

    “Create/Update Profile” button saves both profile and schedule

    Four “Pickup” buttons (Green, Blue, Brown, Red) display next collection date in a dialog

    Status banner confirms when profile and schedule are saved

#Testing

    Unit tests for each RPC implementation

    Integration test covering full workflow: register → create profile → set schedule → get schedule

    Edge cases: missing profile, missing schedule

#Deployment

Everything runs locally on port 50051 to simulate a distributed environment.
No external/cloud dependencies—just Java, Maven, and your desktop.
Author

Emre Ketme
Student ID: 24191779
x24191779@student.ncirl.ie

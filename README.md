# binout-smart-bin
A distributed smart bin reminder app using Java, gRPC, and JavaFX – for Distributed Systems CA - NCIRL

# BinOut – Smart Bin Reminder App

Hi! This is a small project I built for my Distributed Systems module at NCI.  
The goal is to help people stay on top of their bin collection days using a lightweight, automated setup.

It supports the United Nation’s SDG 11: Sustainable Cities and Communities by encouraging better waste habits through small-scale automation.

## What It Does

- Lets users select their bin provider and bin types
- Simulates collection schedules based on hardcoded data
- Alerts users a day before pickup
- Flags upcoming holidays with a warning message
- Uses a clean JavaFX interface with colour-coded buttons

## Tech Stack

- Java (all services)
- gRPC for inter-service communication
- JavaFX for the GUI
- Maven for builds
- Netbeans

## Services

1. **Service Registry** – for dynamic service discovery  
2. **User Profile Service** – stores user location, provider, and bin types  
3. **Bin Schedule Service** – returns next pickup date based on static data  
4. **Alert Service** – checks which bins are due tomorrow and flags holidays

## GUI Overview

- Toggle buttons for setting up bin provider and bin types
- One button per bin, showing next pickup date
- Green = due tomorrow, Red = not due
- Banner appears if holiday warning is active

## Testing

Includes unit tests for each RPC and integration tests for the full workflow.  
Covers edge cases like missing schedule data and holiday overlaps.

## Deployment

Runs locally on separate ports to simulate a distributed system.  
No cloud setup — everything stays self-contained and simple for demo purposes.


## Author

Emre Ketme  
Student ID: 24191779  
x24191779@student.ncirl.ie

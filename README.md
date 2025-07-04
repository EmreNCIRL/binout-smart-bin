# binout-smart-bin
A distributed smart bin reminder app using Java, gRPC, and JavaFX – for Distributed Systems CA - NCIRL

# BinOut – Smart Bin Reminder App

Hi! This is a small project I built for my Distributed Systems module at NCI.  
The goal is simple: remind people which bin goes out and when — especially helpful if (like me) you keep forgetting and end up with a full black bin for a week.

It ties into the **UN Sustainable Development Goal 11 – Sustainable Cities and Communities**, by promoting better waste management through automation.

---

## What It Does

- Lets users pick their bin provider and the types of bins they have.
- Shows the next pickup date for each bin.
- Alerts users the day before pickup (turns the bin button green).
- Warns users during holiday weeks to check for changes.
- All powered by a set of Java microservices talking over gRPC.

---

## Tech Stack

- **Java** (all services)
- **gRPC** for communication
- **JavaFX** for the user interface
- **Eclipse IDE** for development
- **Maven** to manage dependencies and builds

---

## Microservices

There are 4 services in total:

### 1. `Service Registry`
Keeps track of all running services and lets them discover each other.

### 2. `User Profile Service`
Stores the user's location, bin provider, and selected bin types.

### 3. `Bin Schedule Service`
Simulates pickup dates (hardcoded per provider + location + bin type).

### 4. `Alert Service`
Runs every day and checks if anything is due tomorrow, or if there's a holiday warning to show.

---

## How It Looks

The GUI is simple on purpose. After setup, you just see buttons for your bins:
- **Green = pickup tomorrow**
- **Red = not due**
- Holiday warning banner if needed

Setup is all done via toggle buttons – no typing.

---

## Testing

- Each service has basic unit tests to check inputs and outputs.
- I also test the full flow (from setting up a profile to seeing alerts).
- I simulate edge cases like missing data or holiday overlaps.

---

## Deployment

For now, everything runs locally.  
Each service runs on its own port, and they all register/discover via the registry.  
No cloud or containers needed — keeps things simple for demo/testing.



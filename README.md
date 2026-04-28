# Energy Community Project

## Repository
https://github.com/phiwa94/energy-community.git

---

## How to Run

### 1. Start the REST API

Run the `EnergyCommunityRestApplication` class.

### 2. Start the GUI

Run the `EnergyApplication` class.

## API Endpoints

- `/energy/current`  
  Returns current energy data

- `/energy/historical?start=...&end=...`  
  Returns historical data filtered by date and time

---

## Features

- Fetch current energy data
- Filter historical data by date and time
- Input validation in the UI
- Error handling for invalid input and connection issues
---

## Notes

- The data is simulated as of right now (no database is used)
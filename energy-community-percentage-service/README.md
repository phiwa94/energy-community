# Energy Community Percentage Service

This component listens for usage update messages and calculates current percentage values.

## Queue consumed

- `usage_data_updated`

## Database tables

Reads from:

- `usage_data`

Writes to:

- `current_percentage`

## Calculations

```text
community_depleted = community_used / community_produced * 100
grid_portion = grid_used / (community_used + grid_used) * 100
```

Division by zero is handled by returning 0.

## Run

Start Docker infrastructure first from the project root:

```bash
docker compose up -d
```

Then start the percentage service:

```bash
./mvnw spring-boot:run
```

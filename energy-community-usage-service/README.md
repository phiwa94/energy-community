# Energy Community Usage Service

This component listens to RabbitMQ messages from the producer and user components and stores hourly usage data in the database.

## Queues consumed

- `community_energy_producer_out`
- `community_energy_user_out`

## Queue produced

- `usage_data_updated`

The `usage_data_updated` queue is meant for the later Current Percentage Service. It tells that service which hour changed and should be recalculated.

## Database table

The service creates/updates the `usage_data` table through JPA.

Important fields:

- `hour_start`
- `community_produced`
- `community_used`
- `grid_used`

## Logic

For producer messages:

```text
community_produced += kwh
```

For user messages:

```text
available community energy = community_produced - community_used
community_used receives as much of the requested kWh as possible
grid_used receives the rest
```

This keeps `community_used` from becoming greater than `community_produced`.

## Run

Start RabbitMQ first, then run:

```bash
./mvnw spring-boot:run
```

For now this component uses a local H2 database file at:

```text
./data/energy-community-usage-db
```

Later, this can be changed to PostgreSQL/MySQL by editing `application.properties`.

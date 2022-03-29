## Installation
1) Run the following command.

```bash
docker-compose up
```

| Endpoint | Method | Body | Description |
|:---:|:---:|:---:|:------:|
| http://localhost:8080/starships | GET | - | Fetch all starships in our db|
| http://localhost:8080/starships/{id} | PATCH | { "action":"INCREMENT", "count":"10" } |  Increments by 10 the count of starship. Possible actions are INCREMENT, DECREMENT, SET|
| http://localhost:8080/vehicles | GET | - | Fetch all vehicles in the db |
| http://localhost:8080/vehicles/{id} | PATCH | { "action":"INCREMENT", "count":"10" } |  Increments by 10 the count of vehicles.  Possible actions are INCREMENT, DECREMENT, SET|


docker pull timescale/timescaledb-ha:pg16

docker run -d \
    --name timescaledb \
    -p 5555:5432 \
    -e POSTGRES_PASSWORD=postgres \
    timescale/timescaledb-ha:pg16

docker exec -it timescaledb psql -U postgres

# CREATE DATABASE border_crossing_db;
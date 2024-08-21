CREATE EXTENSION IF NOT EXISTS timescaledb CASCADE;

SELECT * from create_hypertable('border_crossing', by_range('arrival_timestamp'), if_not_exists => TRUE);
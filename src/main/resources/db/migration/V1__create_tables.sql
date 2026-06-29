CREATE
EXTENSION IF NOT EXISTS postgis;

CREATE TABLE neighborhood
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    point geometry(Point, 4326) NOT NULL
);

CREATE TABLE warnings
(
    id              BIGSERIAL PRIMARY KEY,
    neighborhood_id BIGINT                   NOT NULL,
    timestamp       TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    quota           VARCHAR(50)              NOT NULL,

    CONSTRAINT fk_warning_neighborhood
        FOREIGN KEY (neighborhood_id)
            REFERENCES neighborhood (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_neighborhood_point ON neighborhood USING GIST (point);
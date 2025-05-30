#!/bin/bash

set -e

# Make sure the script is run from the project root directory
# (i.e. the directory containing the 'telemetry-service' root pom.xml)

echo "Starting infrastructure..."
cd infra
docker compose up -d
cd ..

echo "Waiting for infrastructure to initialize..."
sleep 10

echo "Starting telemetry service..."
mvn spring-boot:run -pl telemetry &
TELEMETRY_PID=$!

echo "Starting projection service..."
mvn spring-boot:run -pl projection &
PROJECTION_PID=$!

echo "Services started with PIDs: telemetry=$TELEMETRY_PID, projection=$PROJECTION_PID"
echo "Press [CTRL+C] to stop both."

trap "echo 'Stopping services...'; kill $TELEMETRY_PID $PROJECTION_PID" SIGINT

wait

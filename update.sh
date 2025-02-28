#!/bin/bash
set -e

sudo pkill java || true
sudo rm -rf /home/ubuntu/Backend/target/
sudo docker-compose down || true

git pull origin main
mvn clean package
sudo docker-compose up --build -d
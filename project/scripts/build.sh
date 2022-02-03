#!/bin/bash

# build manager
cd manager
chmod +x gradlew
sudo ./gradlew build
docker build -t manager:latest .
cd ..

# build generator
cd generator
chmod +x gradlew
sudo ./gradlew build
docker build -t generator:latest .
cd ..

# build mail-sender
cd mail-sender
chmod +x mvnw
sudo ./mvnw install
docker build -t mail-sender:latest .
cd ..

# install crawler requirements library
cd crawler
python3 -m venv .venv
sudo mv chromedriver/chromedriver_linux /usr/local/bin/chromedriver
source .venv/bin/activate
pip install -r requirements.txt
#!/bin/bash

# build manager
cd manager || exit
chmod +x gradlew
sudo ./gradlew build --exclude-task test
docker build -t manager:latest .
cd ..

# build generator
cd generator || exit
chmod +x gradlew
sudo ./gradlew build
docker build -t generator:latest .
cd ..

# build mail-sender
cd mail-sender || exit
chmod +x mvnw
sudo ./mvnw install
docker build -t mail-sender:latest .
cd ..

# install crawler requirements library
cd crawler || exit
python3 -m venv .venv
sudo mv chromedriver/chromedriver_linux /usr/local/bin/chromedriver
source .venv/bin/activate
pip install -r requirements.txt

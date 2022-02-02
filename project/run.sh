#!/bin/bash

sudo apt-get update && sudo apt-get -y upgrade
sudo apt-get install -y openjdk-11-jdk maven gradle

# build manager
cd manager
./gradlew clean build
cd ..

# build generator
cd generator
./gradlew clean build
cd ..

# build mail-sender
cd mail-sender
./mvnw clean install
cd ..


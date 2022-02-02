#!/bin/bash

# update & upgrade
sudo apt-get update && sudo apt-get -y upgrade

# jdk, buiild tool install
sudo apt-get install -y openjdk-11-jdk maven gradle

# docker install
sudo apt-get install -y docker-ce docker-ce-cli containerd.io

# docker as non-root user (docker-compose Prerequisites)
sudo groupadd docker
sudo usermod -aG docker yrcj
newgrp docker

# docker-compose install
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

# build manager
cd manager
chmod +x gradlew
./gradlew build
docker build -t manager:latest .
cd ..

# build generator
cd generator
chmod +x gradlew
./gradlew build
docker build -t generator:latest .
cd ..

# build mail-sender
cd mail-sender
chmod +x mvnw
sudo ./mvnw install
docker build -t mail-sender:latest .
cd ..


# run applications in the backgroud
docker-compose up -d
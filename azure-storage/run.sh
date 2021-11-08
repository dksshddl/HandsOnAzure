#!/bin/bash

sudo apt-get update && sudo apt-get -y upgrade
sudo apt-get install -y openjdk-11-jdk maven

#git clone https://github.com/dksshddl/HandsOnAzure.git

#cd HandsOnAzure/azure-storage

mvn clean install -DskipTests  # skip test
java -jar target/*.jar

# docker build -t handsonazure/azure-storage
# docker run -p 8080:8080 handsonazure/azure-storage

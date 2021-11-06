#!/bin/bash

sudo apt-get update && sudo apt-get upgrade

sudo apt-get -y install openjdk-11-jdk maven

git clone https://github.com/dksshddl/HandsOnAzure.git

cd azure-storage

mvn clean package install

jar_file = target/*.jar

java -jar ${jar_file}
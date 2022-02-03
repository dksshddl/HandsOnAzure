#!/bin/bash

# update & upgrade
sudo apt-get update && sudo apt-get -y upgrade

# jdk, buiild tool install
sudo apt-get install -y openjdk-11-jdk maven gradle

# python3
sudo apt-get install -y python3.8 python3.8-venv python3-pip

# docker install
sudo apt-get install -y \
   ca-certificates \
   curl \
   gnupg \
   lsb-release
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io

# docker as non-root user (docker-compose Prerequisites)
sudo getent group docker || sudo groupadd docker
sudo usermod -aG docker $USER

# docker-compose install
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-composes

# chrome install
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb 
sudo apt-get install -y ./google-chrome-stable_current_amd64.deb 
google-chrome --version
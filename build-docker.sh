#!/bin/bash
VER=0.12
gradle build
docker build --tag er-logger:$VER .
#docker push cherepakhin/receiver:$VER



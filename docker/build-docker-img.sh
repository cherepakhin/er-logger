#!/bin/bash
VER=0.10
cd ..
gradle build
#docker build --tag receiver:0.1 .
docker build --tag cherepakhin/receiver:$VER -f docker/Dockerfile-jar .
docker push cherepakhin/receiver:$VER
#echo $VER



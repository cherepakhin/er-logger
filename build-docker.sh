#!/bin/bash
VER=0.14
#gradle build
docker build --tag cherepakhin/er-logger:$VER .
docker push cherepakhin/er-logger:$VER



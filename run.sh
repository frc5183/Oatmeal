#!/bin/bash

####################################################
# THIS FILE READS THE TOKEN FOR THE BOT FROM .env
# TEMPLATE FOR .env FILE
# 
# TOKEN="PUT_YOUR_TOKEN_HERE"
# 
####################################################


set -a # automatically export all variables
source .env  # get enviroment variables from this file
set +a # add back in all old variables

SCRIPT_DIR=$(realpath "$(dirname "${BASH_SOURCE[0]}")")

WORKING_DIR="$SCRIPT_DIR/build/WORKING_DIR"

mkdir -p "$WORKING_DIR"

./gradlew shadowJar

cd "$WORKING_DIR"

java -jar "$SCRIPT_DIR/build/libs/oatmeal-1.0-all.jar"

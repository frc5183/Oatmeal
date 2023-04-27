#!/bin/bash

####################################################
# THIS FILE READS THE TOKEN FOR THE BOT FROM .env
# TEMPLATE FOR .env FILE
# 
# TOKEN="PUT_YOUR_TOKEN_HERE"
# 
####################################################

# You may copy this file and modify these paths 
#   to your liking to run the program
SCRIPT_DIR=$(realpath "$(dirname "${BASH_SOURCE[0]}")")

WORKING_DIR="$SCRIPT_DIR/build/WORKING_DIR"

JAR_FILE="$SCRIPT_DIR/build/libs/oatmeal-1.0-all.jar"


set -a # automatically export all variables
source .env  # get enviroment variables from this file
set +a # add back in all old variables

mkdir -p "$WORKING_DIR"

./gradlew shadowJar

cd "$WORKING_DIR"

java -jar "$JAR_FILE" "$TOKEN"

# Oatmeal
![workflow](https://github.com/frc5183/oatmeal/actions/workflows/build-gradle.yml/badge.svg)

This is the code repo for the Oatmeal discord bot in the 5183 Discord. You are welcome to use this code for whatever but no you won't get the token unless we screw up, so don't ask.

# Configure
## Set the bot token
Set an environment variable `TOKEN="yourtokenhere"`

OR

Run the .jar with the token as your argument.

OR

Change the value 
`token:"yourtokenhere"`
in the generated config.json

## Set database credentials
Set the following environment variables accordingly: `DATABASE_ADDRESS`, `DATABASE_PORT`, `DATABASE_USER`, `DATABASE_PASSWORD`, `DATABASE_NAME`
Currently only supports MariaDB (and it's forks), however more are planned to be added in the future, including SQLite.

# Build
```
gradlew shadowJar
```
Find the .jar in build/libs/oatmeal-(version)-all.jar

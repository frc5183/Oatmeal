FROM gradle:jdk17 as BUILD
WORKDIR /build
COPY --chown=gradle:gradle src /build/src
COPY --chown=gradle:gradle build.gradle settings.gradle /build/
RUN gradle --no-daemon shadowJar

FROM openjdk:17-slim
WORKDIR /app
COPY --from=BUILD /build/build/libs/oatmeal-1.0.jar oatmeal.jar
WORKDIR /app/work
ENTRYPOINT java -jar /app/oatmeal.jar
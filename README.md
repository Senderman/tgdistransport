# TgDisTransport
## A simple Discord -> Telegram message transport written in kotlin

### Setup

`git clone https://github.com/Senderman/tgdistransport.git`

Open src/main/resources/application.properties and edit values

You can use ${ENV_VAR_NAME} syntax to get values from environment variables

Also, you can create profiles. Just create `application-PROFILENAME.properties` file in the same directory

### Build

Windows:

`gradlew.bat shadowJar`

Unix:

`./gradlew shadowJar`

### Run
`java -jar build/libs/tgdistransport-1.0.0.jar`

If you want to use a profile, run

`java -Dtransport.profile=PROFILENAME -jar build/libs/tgdistransport-1.0.0.jar`
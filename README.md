# Swiss Hacks 2024

This project uses Quarkus, the Supersonic Subatomic Java Framework. Java 21 or above is required for building and
running.

**Learn more about Quarkus:** https://quarkus.io.

We've published a container image for easy local execution (replace `podman`
for [`docker` for Windows](https://docs.docker.com/desktop/install/windows-install)):

```shell
podman run ghcr.io/postfinance/swiss-hacks-2024:latest
```

See available versions [here](https://github.com/postfinance/Swiss-Hacks-2024/tags).

**Pre-created Login Credentials**

For your convenience, we've pre-created login credentials with some sample accounts and transactions:

| Username    | Password          | Role  |
|-------------|-------------------|-------|
| john.doe    | strong-password   | User  |
| jane.smith  | you-dont-guess-me | User  |
| jimmy.allen | secure-secret     | Admin |

## The Challenge

We challenge you to leverage Generative AI and transform the way APIs are tested in the banking sector. Imagine
innovative GenAI applications for end-to-end testing that utilize our provided resources:

Precise standards: [./src/main/resources/openapi/openapi.yml]
Use case definitions: [./spec/Requirements.md]

The goal is to develop AI that intelligently suggests improvements during the development/building/testing phase (any or
multiple).

## Local Development

1. Clone this repository:

```shell
git clone git@github.com:postfinance/Swiss-Hacks-2024.git
```

2. Navigate to the project directory:

```shell
cd Swiss-Hacks-2024
```

3. Build the application using Maven:

```shell
./mvnw package
```

### IDE Integration

For IntelliJ IDEA Community Edition, install the Quarkus Tools plugin from the
marketplace: https://plugins.jetbrains.com/plugin/13234-quarkus-tools.

### Running the Application

#### Dev Mode (Live Coding):

* Within your IDE or using:

```shell script
./mvnw compile quarkus:dev
```

* Access the Dev UI at http://localhost:8080/q/dev (available only in dev mode).

#### Packaged Application:

1. Package the application:

```shell script
./mvnw package
```

2. This creates `quarkus-run.jar` in `target/quarkus-app/`.

3. Run the application:

```shell
java -jar target/quarkus-app/quarkus-run.jar
```

#### Creating a Native Executable

1. Build the native executable:

```shell script
./mvnw package -Dnative
```

2. Alternatively, build it in a container if GraalVM is not installed:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

3. Run the native executable:

```shell
./target/swiss-hacks-2024-*-runner
```

If you want to learn more about building native executables, please
consult https://quarkus.io/guides/maven-tooling.

### OpenAI
For access to OpenAI, please ask the mentors to receive a token.

*Example:*
```python 
from openai import OpenAI
client = OpenAI(api_key="<YOUR_TOKEN>")

response = client.chat.completions.create(
  model="gpt-4-turbo",
  messages=[
    {"role": "system", "content": "You are a helpful assistant."},
    {"role": "user", "content": "Who won the world series in 2020?"},
    {"role": "assistant", "content": "The Los Angeles Dodgers won the World Series in 2020."},
    {"role": "user", "content": "Where was it played?"}
  ]
)
```


### FAQ
TBD

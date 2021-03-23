# HeiCheck Backend
- [Initialisation](#initialisation)

- [Docker](#docker)
    - [Deployment](#deployment)

- [GitLab](#GitLab-Link)

# Initialisation

## SonarQube

Run `StartSonar` -> Web Browser `http://localhost:9000` -> login admin:admin
-> Create Project -> generate token 

Edit `gradle.properties`

```gradle
// customize these settings
systemProp.sonar.login=979d72a66caddbca4566e3f419973f6946d2eb51
systemProp.sonar.projectKey=Test
```

After that run (with IntellJ and the gradle client , NOT powershell):

```powershell
gradlew bootJar
gradlew sonarqube
```

## Checkstyle

File -> Settings -> nach 'Checkstyle' suchen -> '+' Configuration File -> Description: awpHD ->
Use local Checkstyle file: browse to `awpChecks.xml` (in the project directory).

# Docker

The is a problem with docker-compose, it is only used in testing and deployment but not in production.
There is an issue considering the `datasoruce.url` from the `application.yml` file.

```docker
// url with local gradle wrapper
datasource:
  url: jdbc:postgresql://localhost:5432/example

// url with docker-compose
datasource:
  url: jdbc:postgresql://pgres:5432/example
```
docker-compose know how to resolve `pgres`, because it is passed as *external_link* in the `docker-compose.yml` file.
At this time you have to switch between these two domain names, it depends in which situation you are.

**Setup postgres container**:

```docker
docker run --name pgres -p 127.0.0.1:5432:5432 -e POSTGRES_PASSWORD=p -d postgres
docker exec -it pgres psql -U postgres
CREATE DATABASE example;
\q
```

If you are in *production* you are done here. Boot the microservice via `gradle bootRun`

**Setup docker network**:

Switch the url to `pgres` in `application.yml`.

```docker
// setup network
docker network create -d bridge heicheck
docker network connect heicheck pgres	
docker network inspect heicheck

// to build the docker image
./gradlew build docker
docker-compose up -d heicheck-keycloak
docker-compose up -d
```

If you have any difficulty building the container:
    
   - `Connection refused at postgres 5432`   -> booking-container cannot find postgres-container.
    Check if postgres-container is in *heicheck* network.
   
  - `Cannot create any JPA-configuration bean` -> clean palantir and gradle cashes and try again

## Deployment

### Manually

With *docker-compose* were going to deploy our services. For that aspect the directory `deployment` is essential.

Needed images:

- hska/heicheck
  
- postgres

- flyway/flyway

- jboss/keycloak

- hska/website

In the `application.yml`:

```yml
// url with docker-compose
datasource:
  url: jdbc:postgresql://pgres:5432/heicheck
```

Executed the following commands (later there will be a deploy-script):

```docker
cd deployment
// First you'll have to start the authorizationserver
docker-compose up -d heicheck-keycloak
// After this you could start the remaining containers
docker-compose up -d 

// stop, remove all containers, networks, volumes which were created by docker-compose.yml
docker-compose down
```

What will be created?

- Network: heicheck
  
- Container: postgres container as db **pgres**

- Container: flyway executing migrationsscripts against the postgres db

- Container: booking, the microservice access the postgres db **booking**

- Container: heicloak, the authorization server for securing the endpoints of container **booking**

- Container: website, the website for uploading the excel files into the database

### Via Deployment-Script

For automated deployment the project has now the new directory `./deployment/upload`.<br>
In this folder you'll find a bash-script calld DockerToTAR.sh.<br> <br>

Now you could start this script via your integrated bash:
```powershell
bash
cd /deployment/upload
chmod a+x DockerToTAR.sh
./DockerToTAR.sh
``` 

Now the script leads you through the way of saving your local version of the docker
image in a tar.gz-archive and deploy it via `scp` command line tool on the sandbox. <br> <br>

An additional function is the transfer of the whole deployment folder to the server.
This will be helpful, if the `docker-compose.yml` has changed, etc.

#### On the Sandbox Server

In the home (`~`) directory you'll find a `Scripts` folder now. In this folder all 
bash scripts are located. <br> <br>

With the script `DockerImport.sh` you could automate the import of the transfered 
docker file to the server and extract a new deployment folder.

To run this script, you'll have to connect via `ssh` to the server.
In the `deployment/upload` directory the script `SandboxConnection.sh` will be your 
entry to the server. <br> <br>

Use the following commands to connect to the server and run the automation scripts:
```powershell
bash
cd /deployment/upload
chmod a+x SandboxConnection.sh
./SandboxConnection.sh

-------- Now you will be connected --------

(Now on the server)

DockerImport.sh
```
The script leads you to the process and finally start `docker-compose up` to run the 
imports.

To exit the Sandbox Server:
```bash
quit

-------- Now you will be disconnected --------

(back on your machine)

exit

(now you are back in the powershell)
```

# GitLab-Link
For accessing the project on GitLab use the following [Link](https://gitlab.com/JulianBuecher/heicheck-backend.git)
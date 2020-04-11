# Development and CI/CD Aspects

## Motivation
One of the essential learning goals of the PoC is to test a **MicroService** in a cluster environment.
The first choice should be to host **Kubernetes** in a cloud environment like AWS, Azure or GCP.
For learning purposes it is probably better to start with an environment fully under control to see the effects of operational behaviour directly, e.g. to switch off and on Kubernetes nodes and test the deployment strategies if a Raspi is switched off accidentially.

The other learning goal is to test the full cycles of **CI/CD** process to prepare **DevOps**
This goal is interesting as the full environment is quite complex.
Just as an overview of used tools in the PoC must be managed in DevOps:

![PoC Development](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-development.jpg "PoC Development")

## The Software Stack
The key components per usage:

**Usage** | **Tool**  
--------------------------- | ------------------------
Development IoT | Arduino IDE C-code
Development IDE | Visual Studio Code (Java, Python, JavaScrip code)
Programming Languages | Java, Python, JavaScript
MicroService Framework | Quarkus
Automatic Build and Packaging | Maven
Source Control | Git, GitHub
Deployment Container Staging| DockerHub
Container Orchestration | Kubernetes
CI/CD | Jenkins
Database | MariaDB
Database GUI | Heidi-SQL
Monitoring | Poseidon
Dashboards | Grafana


## Challenges
There are some challenges to cover in the POC:
* Hardware limitations (Raspi 3 has a memory limit of 1G, too less for build process)
* Cross platform architecture (AMD64 vs. Arm64)
* Complex deployment chain (GitHub, DockerHub, ..)
* Complex Network Setup
* Microservices configuration

## The Quarkus App
The heart of the Exercise is a MicroService to run in a Kubernetes Cluster.
The choice is fallen on Quarkus as this framework is running natively in Kubernetes and fulfil modern JEE MicroProfile specifications.
[Quarkus](https://quarkus.io/ "Quarkus")
Another comparable framework is SprinBoot but Quarkus has made significant advantages to run natively with the GraalVM (not applicable on arm64).

#### Architecture
The Quarkus-Logger application: logger-server follows the basic patterns in definition of
* REST Endpoints
a generic Endpoint ```@Path("/generic)" ```
a DHT Endpoint ```@Path("/dht")```
a version tracker ```@Path("/version")```
Remark: there is common path prefix defined in configuration ```/logger```
* Domain Logic
a domain object to save LoggerEntry into the timeseries database
different objects to read the JSON objects (Generic and DHT)
* Service
a LoggerService to manage the business logic workflow
* DAO
for using **Hibernate** ORM mapper

#### Calling the App
A call to the app could so look like:

```
curl -X POST -H 'Content-Type: application/json' -i 'http://192.168.10.107:8080/logger/generic' --data '{
    "Header": { "Timestamp": "2020-02-16T23:40:00.000000", "Device": "DHTL-T"},
    "Body": {"measurement":"temperature", "value":22.50, "unit":"gradC", "aggr":0}
}'
```

#### Configuration Remarks
The application.properties looks like
```
# Configuration file
# key = value

quarkus.resteasy.path=/logger

## DB-Properties
quarkus.datasource.driver=org.mariadb.jdbc.Driver
quarkus.datasource.username=logger
quarkus.datasource.url=jdbc:mysql://localhost:3306/logger
quarkus.datasource.password=data!logger
quarkus.datasource.max-size=8
quarkus.datasource.min-size=2

%dev.quarkus.hibernate-orm.database.generation = drop-and-create
#%dev.quarkus.hibernate-orm.database.generation = none
%dev.quarkus.datasource.url=jdbc:mysql://localhost:3306/logger1
```
To remark there is a flexible configuration for the database access via Environment variables in Docker and Kubernetes later on.

## CI/CD

#### Preparation
The automation flow is based on some basic elements:
* Integrated Development Environment (VSC)
* Source code control (Git, GitHub)
* Build and Packaging (Maven)
* CI/CD orchestration (Jenkins)
* Docker Registry (DockerHub)

A challenge to develop for Raspberries is the ARM technology. One fundamental principle in DevOps using modern technologies is the 'shift left', means that the developers are defining the application in his context like OS dependencies, realized in containers (Docker, Kubernetes). The operational work is taking care about the container orchestration (e.g. Kubernetes) but not the inside of the containers itself.
In consequence this means that the target build must created in an environment similar to the production circumstances.
Means that 
* the development in Windows isn't like a Linux environment on a Raspi
* the development in a Linux environment on an Intel/AMD isn't like an Arm64 as on Raspi

The selected solution is as following:
* The development is done in Linux under a VM (VMWare Player) running XUbuntu
* The build process is done on an emulated Raspberry (via QEMU) in the Linux VMWare

The advantage of this solution is to have the complete build environment on one Windows Laptop, inclusive a Raspberry (emulated and therefore with unlimited RAM for the build process)

#### The Workflow

![PoC Development Flow](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-software-stack.jpg "PoC Development Flow")

#####1) Development
The build and unit tests are done in VSC on XUbuntu in VMWare
The resulting code is commited and pushed to GitHub

#####2) Integration test, packaging and staging
The integration tests are done in the emulated Raspi by **Jenkins**
The code is finally build, integration tested and packaged
As a final step the package will be included in a Docker image and been tagged

The Jenkins pipeline:

```
pipeline {
    agent { label 'arm64' }
    environment { 
        QUARKUS_DATASOURCE_URL = 'jdbc:mysql://192.168.10.107:3306/logger1'
    }
    tools {
        maven 'Maven 3.6.0 QEMU-Raspi'
    }
    stages {
        stage("Checkout") {
            steps {
                //git branch: 'develop', url: 'https://github.com/hdwinkel/quarkus-logger.git'
                checkout([$class: 'GitSCM',
                branches: [[name: 'origin/develop']],
                extensions: [[$class: 'WipeWorkspace']],
                userRemoteConfigs: [[url: 'https://github.com/hdwinkel/quarkus-logger.git']]
                ])
            }
        }
        stage("Compile") {
            steps {
                dir("logger-server") {
                    sh "pwd"
                    sh "mvn compile"
                }
            }
        }
        stage("Unit test") {
            steps {
                dir("logger-server") {
                    sh "pwd"
                    sh "mvn test"
                }
            }
        }
        stage("Package") {
            steps {
                dir("logger-server") {
                    sh "pwd"
                    sh "mvn package -DskipTests"
                }
            }
        }
        stage ('Docker image build'){
            steps {
                echo 'Building docker image'
                dir("logger-server") {
                    sh "pwd"
                    sh 'docker build -f src/main/docker/Dockerfile.jvm.arm64 -t quarkus/logger-server-jvm .'
                }
            }       
        }
    }
    post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'This will run only if successful'
        }
        failure {
            echo 'This will run only if failed'
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}
```

#####3) Staging
The builded Docker image, representing the Quarkus App, will be tagged and published to DockerHub (in this case **v4**)

```
# re-tagging quarkus image
docker tag quarkus/logger-server-jvm hdwinkel/logger-server-jvm-arm64:v4

# run container with tagged image
docker run -i --rm -e QUARKUS_DATASOURCE_URL='jdbc:mysql://192.168.10.130:3306/logger' -p 8080:8080 hdwinkel/logger-server-jvm-arm64:v4

# docker login and push
docker login
docker push hdwinkel/logger-server-jvm-arm64:v4

```

#####4) Deployment
On the Kubernetes master the deployment has to be updated (**v4**)
after this it can be deployed
```
sudo kubectl apply -f logger-deployment.yaml
```
**Remark:**
The different deployment strategies can be studied e.g.:
[Kubernetes Deployment Strategies](https://blog.container-solutions.com/kubernetes-deployment-strategies
 "Kubernetes Deployment Strategies")


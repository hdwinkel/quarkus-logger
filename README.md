# quarkus-logger

![Übersicht POC](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-overview-poc.jpg "PoC")

This repo shall host all activities to create a quarkus based IoT logging landscape
It consist of some different parts:
* Some IoT **DHT** sensors connected with an **ESP8266** to a logging controller
* A Logging controller realized in **MQTT** and **NodeRed** 
(installed in a **Docker** environment on a **Raspberry Pi**)
* A Raspberry Pi **Cluster** based on an **arm64** Ubuntu running a **Kubernetes** Cluster K3S,
hosting a **Quarkus** application
* A **MariaDB** database installed in a Docker container on a Raspberry Pi
* **Prometheus** and **Grafana** installed in a Docker container on a Raspberry Pi

## What is it for?
It is a project for self-learning different aspects of modern development:

* How to build IoT landscapes?
* Understanding modern IT Architectures including Microservices and Kubernetes Clusters
* How to manage modern development processes including CI/CD, DevOps

#### Content:

[IoT and Data Aspects](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/IoT/README.md "IoT and Data Aspects") The IoT side of the PoC (Producing/Consuming Sensor and other data from SmartHome)

[Architecture, Quarkus, Kubernetes](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/Architecture/README.md "Architecture, Quarkus, Kubernetes") The Architecture with a Controller and a Rapberry Cluster hosting Docker and Kubernetes and running Middleware and a MicroService application (Quarkus-App)

[Development Aspects](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/Development/README.md "Development Aspects") The Development aspects including CI/CD

#### Complexity:

To be able to understand the whole lifecycle in a real life simulation the complexity is quite high. It spans from Arduino for sensors until Docker and Kubernetes to host Quarkus Microservices applications.

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
Message Orchestration | Node Red
Message Broker MQTT | Mosqitto
OS for Development | XUbuntu in VMWare Player
OS for Deployment | Arm64 Ubuntu (emulated in QEMU)




## What isn't it?
The repo is NOT a ready to use package for production e.g. at home.
The ideas are still valid and all sceanarios runs but the code quality isn't expected as world class, just to understand the scenarios.
I will improve the code quality and reduce the technical debts when I've a bit more time for it :-)

**For Documentation see the doc folder**
[Documentation](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/README.md "Documentation")

For questions around the scenarios and technologies you can contact me of course:
winkel[at]egladil.de

**Disclaimer:**
If I have made some mistakes or have I violated copyright notices, please let me know so that I can correct it.



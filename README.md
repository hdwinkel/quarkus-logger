# quarkus-logger
This repo shall host all activities to create a quarkus based IoT logging landscape
It consist of some different parts:
* Some IoT DHT sensors connected with an ESP8266 to a logging controller
* A Logging controller realized in MQTT and NodeRed 
(installed in a Docker environment on a Raspberry Pi)
* A Raspberry Pi Cluster based on an arm64 Ubuntu running a Kubernetes Cluster K3S,
hosting a Quarkus application 
* A MariaDB database installed in a Docker container on a Raspberry Pi
* Prometheus and Grafana installed in a Docker container on a Raspberry Pi

## What is it for?
It is a project for self-learning different aspects of modern development:

* How to build IoT landscapes?
* Understanding modern IT Architectures including Microservices and Kubernetes Clusters
* How to manage modern development processes including CI/CD, DevOps

##What isn't it?
The repo is NOT a ready to use package for production e.g. at home.
The ideas are still valid and the full sceanarios runs but the code quality isn't expected as world class, just to understand the scenarios.
I will improve the technical debts when I've a bit more time

**For Documentation see the doc folder**

For questions around the scenarios and technologies you can contact me of course:
winkel[at]egladil.de





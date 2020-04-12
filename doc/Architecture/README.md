# Architecture, Environment and Provisioning Aspects of PoC
The overall architecture of the PoC can be grouped along some different networks according an IoT edge infrastructure simulation:

![PoC Architecture](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-Architecture.jpg "PoC Architecture")

1. The Logger Net
**[Logger Controller](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/Architecture/Controller.md "Logger Controller")**
This network segment is simulating a Sensor Edge, it demonstrates that in bigger environments the sensors and actors are decentralized and decoupled from the central edge network like LoRaWan communicating devices or just MODBUS connected meters in Building Management Systems
Typically the communication is controlled by specific controllers to enable it in the Edge network. The Controller acts in this sense as an application and network router
2. The Cluster Net
**[Logger Cluster](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/Architecture/Cluster.md "Logger Cluster")**
This network segment simulates an Edge Cloud to process all the different sensor and meter data. The cluster is realized with some Raspberry Pi.
Following the intention of the PoC on some Raspberries will be created a Kubernetes Cluster, hosting a Microservices platform application (Quarkus App)
In addition some remaining elements have to be installe like a database (MariaDB) and the monitoring dashboard (Prometheus, Grafana). These additional elements will be installed in Docker containers.

3. The Home Net
**[Home Environment](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/Architecture/Home.md "Home Environment")**
There are some reflections around the practical usage in the HomeSmart and operational aspects of the PoC



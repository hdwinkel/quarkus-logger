# The Home Environment
## Use Smart-Home
The Home environment is a bit bigger as needed for the Raspi itself
In addition there is a central **BananaPi-M64** (2GByte) responsible for the SmartHome and have a **Fedora-31** installation using **Podman** instaed of Docker to host
* MQTT-Mosquitto
* Node-Red
* Home Assistant

**Remark**: Fedora isn't supporting Raspi-4 currently even the Fedora system itself has some significant advantages over Ubuntu-Server
Another critical point is that **Podman** isn't supporting **Kubernetes** currently

## Prepare CI/CD Jenkins

Another additional software is essential to run the PoC completely and this is **Jenkins**
The Jenkins server isn't installed on a Raspi but on an ordinary Intel-Server.
The Raspis can be controlled via Jenkins remote agents (what in fact isn't more than a SSH access)

For the installation instructions of installing Jenkins CI/CD tool in Docker/Podman there are tutorials available.
**Remark:** If the Jenkins server is used in an agent mode the Jenkins Environment must be configured to support **Java-JDK** and **Apache-Maven** properly. There is no check in Jenkins itself if this runs.
To avoid version conflicts it is highly recommended to specify the Environment in the Jenkins pipeline scripts directly.
See Jenkinsfile


# The Raspi-Controller

![PoC Controller](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-logger-controller.jpg "PoC Controller")

The idea behind the Logger Controller is to manage
1. The edge IoT data and make them available for the edge-cloud (Raspi-Cluster)
2. Act as a network router to decouple the meter and sensor data from the edge-cloud

In my SmartHome there work different Controllers
* one controller access the Solax inverter and thje power meter and transfer data to the cluster (done via **Python** scripts)
All data will be collected and saved to a local file-system (an SSD on a Raspberry and will be sent further)
* two controllers are taken care about the temperature and humidity sensors
All data will be collected first on a file-system (an SSD on Raspi and will sent further)

## Installation

#### Selection of the OS and Software to install
There are different options here and I decided to use a Raspberry Pi 3 instead of a Raspberry Pi 4 as the performance and memory usage isn't critical for the purpose (Raspi-3 has just 1 GByte mem, Raspi-4 can have up to 4 GByte mem).
First of all it has to be decided in what mode the Raspberry Pi should run, 32 bit or 64 bit, means in **armhf** or **arm64** mode.
On a first view it is a bit confusing what mode could be selected as some different descriptions are used according the processor architecture:
As a starter it is enough to know that armhf could be used for arm32v6 (Raspi-2), arm32v7 (Raspi-3) and arm64v8 (Raspi-3, Raspi-4) computers in their 32 bit mode.
The Raspi-3 and Raspi-4 can run in both modes.
The most common operating system for the Raspberry Pi is Raspbian and available for 32 bit mode only.
Ubuntu offer a 64 bit OS.
[Ubuntu arm64 Download](https://ubuntu.com/download/server/arm "Ubuntu arm64 Download")
As not all software is running on 32 bit any longer (e.g. MongoDB) the choice is fallen on the Ubuntu arm64 OS but the original Raspbian can be taken as well.
(In fact an installation of the original Raspbian has one small advantage: the network routing is much easier than in Ubuntu)

Another consideration should be made as most of the software shall run in a **Docker** container on the Raspi.
Therefore some kernel settings have to be made before running the OS the first time

#### Installation process
**1.**

The Ubuntu-Image from the Ubuntu Download side has to be flashed to an SD card with an image writing software
I've taken [balena Etcher](https://www.balena.io/etcher/ "balena Etcher") for that purpose on a Win 10 laptop

**2.**

On the created file-system on the SD card 

create an empty file
``` 
/boot/firmware/ssh
```
add to the kernel parameter line in  /boot/firmware/nobtcmd.txt
```
  cgroup_enable=cpuset cgroup_memory=1 cgroup_enable=memory
``` 
create an empty file   
```
/boot/firmware/wpa_supplicant.conf
```
add content:
```
ctrl_interface=DIR=/var/run/wpa_supplicant GROUP=netdev
update_config=1
country=DE
 
network={
ssid="<SSID-Name>"
psk="<SSID-Password>"
key_mgmt=WPA-PSK
}
```

3.

Boot, change network settings in router. I prefer DHCP with a fix configured address in the Internet-router but some fix address on the Raspi itself will work as well of course.
Connect and logon with 
```
# User ubuntu ; Password ubuntu
  ssh ubuntu@<IP>
```
Setting a fix ip-address
```
sudo nano /etc/netplan/01-network-eth.yaml
# check:
sudo netplan --debug generate
# apply
sudo netplan apply
```
Set hostname
```
sudo hostnamectl set-hostname <hostname>
# example: sudo hostnamectl set-hostname cluster-ubuntu-s1
# change /etc/hosts
sudo nano /etc/hosts
#  127.0.0.1 <hostname>
# apply to router
```

#### Install Docker
The **Docker** installation process is described in 
[Install Docker Engine on Ubuntu](https://docs.docker.com/engine/install/ubuntu/ "Install Docker Engine on Ubuntu")

**Remark:** select the right Docker install repository. For Ubuntu 19.10 there is no stable Docker engine but can be taken from 18.04 as well.

```
sudo apt-get update
sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common
    
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
# to check fingerprint:
sudo apt-key fingerprint 0EBFCD88
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable"
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io
```
Put the ubuntu user to the docker group to allow docker start without sudo:
```
sudo usermod -aG docker ubuntu
```

To check the Docker installation after reboot:
```
docker info # get the installed Docker version
docker run hello-world
```

In addition I would recommend to install docker-compose via the normal repository functions
```
sudo apt install docker-compose
```

#### Install Node Red and MQTT

1. Create a directory on the mounted SSD e.g.
```
/mnt/extssd/nfs/nodered/data
```
to have a permanent staging directory surviving the Docker container rebuilds.

2. Create a 
```
docker-compose.yml
``` 
file with the content

```
version: "3.3"

services:
  node-red:
    image: nodered/node-red:latest
    environment:
      - TZ=Europe/Berlin
    ports:
      - "1880:1880"
    networks:
      - node-red-net
    volumes:
      - /mnt/extssd/nfs/nodered/data:/data

networks:
  node-red-net:
```

3. Start the container
```
sudo docker-compose up -d
```
4. Check if the container runs
```
docker ps --All
```


Alternatively the Docker container can be created with a Dockerfile as well or just on the command line:

```
docker run -dt --user="1000:1000" -p 1880:1880 -v /mnt/extssd/nfs/nodered/data:/data --name mynodered nodered/node-red:latest
```

The installation of **MQTT** is similar to it:
```
docker run -dt -p 1883:1883 -p 9001:9001 -v /mnt/docker-cluster/mosquitto/config/mosquitto.conf:/mosquitto/config/mosquitto.conf -v /mnt/docker-cluster/mosquitto/config/passwd.txt:/mosquitto/etc/passwd.txt -v /mnt/docker-cluster/mosquitto/data:/mosquitto/data -v /mnt/docker-cluster/mosquitto/log:/mosquitto/log --name mymosquitto eclipse-mosquitto
```
**Remark:** The MQTT installation should be made secure with at least user/password access. To do so the mosquitto.conf must exists and a password must be created.
An approach could be to create a Docker container first and apply the needed MQTT relevant configuration in a second run.

To test the MQTT a publish and subscribe could be done:
```
docker exec -it mymosquitto mosquitto_sub -u <user> -P <passwd> -h localhost -t test
docker exec -it mymosquitto mosquitto_pub -u <user> -P <passwd> -h localhost -t test -m "Hello World"
```




# The Raspi-Cluster

![PoC Cluster](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-Raspi-cluster.jpg "PoC Cluster")

The idea behind the (Raspi-) Cluster is to understand a **Kubernetes** environment hosting a **MicroService** (In my case a **Quarkus** App)
In addition to learn more about some operational elements like **Dashboards** for computer monitoring and the sensor/meter data
A **time-series database** is needed as well

### Install the Raspi-Cluster
The installation of the OS for the Raspi-cluster is quite similar to the installation of the Controller:
[Install Controller](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/Architecture/Controller.md "Install Controller")
As OS it is selected as well the arm64 version of Ubuntu in the headless version.

### Install Kubernetes on Raspi-Cluster
After different tests with K8s on Raspberries with relatively complex installation procedure I decided to go with **K3S** for Ubuntu arm64.
[Install Kubernetes K3S](https://blog.alexellis.io/test-drive-k3s-on-raspberry-pi/ "Install Kubernetes K3S")

The Installation of K3S is surprisingly easy with this setup.
**Remark:** For the cluster manager a Raspberry Pi 3 isn't a valid choice due the memory limitations. Here it's better to start directly with a Raspberry Pi 4.

**Execute on on master:**
```
sudo curl -sfL https://get.k3s.io | sh -
# check service:
sudo systemctl status k3s
# get the token for nodes setup
sudo cat /var/lib/rancher/k3s/server/node-token
# result on system e.g.:  K10238cd1d108fb1206ff5a89cf1b0d27cca5f9196f12f2a6c142d8b41b8cc06a2c::server:4193948658388d9fcc0efa27e86294a3
```

**Execute on nodes:**
```
export K3S_URL="https://192.168.10.211:6443"
# export the key from master:
export K3S_TOKEN="K10238cd1d108fb1206ff5a89cf1b0d27cca5f9196f12f2a6c142d8b41b8cc06a2c::server:4193948658388d9fcc0efa27e86294a3"
# install K3S and let the node connect to master
sudo curl -sfL https://get.k3s.io | sh -
```

**Execute on master:**
(it will be an application be deployed, discussed in the development chapter of the PoC) logger-server-jvm-arm64
```
sudo kubectl get node -o wide

# create the service and deployment
# nano logger-deployment.yaml
# nano logger-service.yaml
sudo k3s kubectl create -f logger-deployment.yaml,logger-service.yaml
# check
sudo k3s kubectl get services
sudo k3s kubectl get deployment logger-deployment
sudo kubectl get pods -l app=logger -o wide

# get deployment info
sudo kubectl get deployments logger-deployment
sudo kubectl describe deployments logger-deployment

# get replicaset info
sudo kubectl get replicasets
sudo kubectl describe replicasets

# exposing the service
sudo kubectl expose deployment logger-deployment --type=LoadBalancer --name=logger-server-jvm-arm64
# check:
sudo kubectl get services logger-server-jvm-arm64
# check the pod status
sudo kubectl get pods -l app=logger -o wide

# scale replicaset to 3
sudo kubectl scale --replicas=3 -f logger-deployment.yaml
```

### Install the database (MariaDB)
Not as part of the Raspi-Cluster itself the time-series database have to be installed.
For that purpose another Raspberry Pi 4 is selected. The reason for the Raspi-4 is to have more than 1 GByte RAM available (4 GByte).
Currently a normal MariaDB is installed.
See docker-compose.yml for a Docker-Compose install of MariaDB

Remark: There is an option to give the MariaDB in the installation process an init script as root user. This can be perfectly used to create an additional schema to distinguish between a Development and a Test environment.


### Install Prometheus and Grafana
The Prometheus and Grafana installation is straightforward with Docker-Compose but must be prepared for the monitoring of the cluster itself.

#### Install Node Exporter on any Raspi:
```
cd /opt
sudo wget https://github.com/prometheus/node_exporter/releases/download/v0.18.1/node_exporter-0.18.1.linux-arm64.tar.gz
sudo tar xfz node_exporter-0.18.1.linux-arm64.tar.gz
sudo ln -s /opt/node_exporter-0.18.1.linux-arm64/node_exporter /usr/local/bin/node_exporter

# start node_exporter
sudo node_exporter &
```

write in crontab for starting up at boot-time
```
crontab -e
# write line
----------------------------------------------------
@reboot /usr/local/bin/node_exporter
----------------------------------------------------
# enable cron
sudo systemctl enable cron
# check metrics
curl localhost:9100/metrics | less
```

#### Install Prometheus
In addition to Docker-Compose a Scrape Config has to be applied:

```
# edit scrape config
cd ~/work/docker/prometheus
sudo docker create --name prom_empty prom/prometheus
sudo docker cp prom_empty:/etc/prometheus/prometheus.yml ./prometheus.yml
sudo chmod a+rw prometheus.yml

#add scrape configs for any raspi
----------------------------------------------------
scrape_configs:
  - job_name: 'ubuntu-s2'
    static_configs:
      - targets: ['192.168.10.83:9100']
  - job_name: 'ubuntu-c1'
    static_configs:
      - targets: ['192.168.10.84:9100']
  - job_name: 'ubuntu-c2'
    static_configs:
      - targets: ['192.168.10.62:9100']
  - job_name: 'aml'
    static_configs:
      - targets: ['192.168.10.130:9100']	  
----------------------------------------------------

```


# IoT - Data producing/consuming part of the PoC
This chapter describes the environment and development aspects of the PoC related to the data producing and consuming side
## Intentions
The main practical goal of the PoC is to collect and monitor data from different existing and planned sources:
* different **Electrical Power** values (the consuming and producing power)
There is no standard way (e.g. SmartGrid devices) to produce these data at my home, must be read with optical readers
* SOLAX Inverter values from the **Solar** Panels
These data are sent to a Solax-Cloud (if an internet connection is foreseen) and with a vendor app the data could be read in a 5 minutes frequency but unfortunately there is no standard out of the box API exposed
* different **Temperature** and **Humidity** values in- and outside home
(this is as well a good example to start with)
For PoC purposes temperature/humidity logger is built on breadboards

Later on these types of data could be used for some more time-series evaluations using ML, but this is outside of this investigations.

In the IoT chapters you can find some detailed info about the three data providers:

* **[ESP8266 Sensors and Actors](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/IoT/SensorData.md "ESP8266 Sensors and Actors")**
* **[Power Meter](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/IoT/PowerData.md "Power Meter")**
* **[Solax SolarPanels](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/IoT/SolaxData.md "Solax SolarPanels")**

## PoC Data Flow

The data flow in the PoC is described as:

![PoC Data Flow](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-Data-Flow.jpg "PoC Data Flow")

The idea behind is to simulate an industry IoT edge landscape where typically different edge stages are involved:

1. Sensors/Actuators
2. Gateways, Data Acquisitions, Controlers
3. Edge IT, pre-processing
4. Data Centres Cloud

see e.g. [IoT Architecture Explained](https://medium.com/datadriveninvestor/4-stages-of-iot-architecture-explained-in-simple-words-b2ea8b4f777f "IoT Architecture Explained")


## Data Format

Inspired by a JSON format for a SolarPanel inverter monitoring I selected a datastructure that can be applied for different types of sensors as well to other time series values. I started with a data structure for a combined temperature/humidity sensor (DHT-22).
The request should be in line with the REST-JSON patterns and should be able to trigger a MicroService.

#### First approach: A specific call for a DHT device
The first approach was to define a generic Header/Body structure as Payload in a JSON call

#####Request

POST:
`http://192.168.10.106:1880/TempHum`
`Request Header:`
`Content-Type: application/json`
Remark: This is a request to a controller (in my case a Node Red) middleware to further extend.

#####Payload
`{`
`    "Header": { "Timestamp": "2019-12-30T15:51:28.494652", "Device": "DHTL-01"},`
`     "Body": {"temperature":27.50,"humidity":35.70}`
`}`
`
This payload structure is generic according a timestamp and a device. I decided not to specify some different types of device in the beginning just to able to start and learn.
But there is a fundamental problem with this format:
It is bound to a specific sensor and must be changed if there is another device with other measurements.

#### Second approach: A generic call for a single measurement of a device
The second approach was to define a generic Header/Body structure as Payload in a JSON call as well but with a generic Body part (device agnostic)

#####Request

POST:
`http://192.168.10.107:58080/logger/generic`
`Request Header:`
`Content-Type: application/json`
Remark: This is a request to cluster, responsible for saving the data in a database
#####Payload
`{`
`    "Header": { "Timestamp": "2020-02-16T22:40:00.000000", "Device": "DHTL-T"},`
`     "Body": {"measurement":"temperature", "value":22.50, "unit":"gradC", "aggr":0}`
`}`
`
This structure is more generic and could be applied for other types of sensors.
the meaning:
* measurement: is the measurement to collect, e.g. temperature or humidity or pressure
* value: is the value
* unit: is the unit of measurement
* aggr: is a flag that indicates that a value is pre-aggregated (e.g. an ongoing summary) aggr:1 or it isn't aggr:0

But there is another problem with this format:
For combined measurement sensors the sensor has to call different JSON requests but should be send a list of generic measurements.
For time reasons it isn't implemented further but there is a workaround:
The sensor is calling a combined measurement (first approach) and the IoT controller is split it in several calls to the application (cluster).

## The Database

Normally all measurements should end up in a specific Time Series Database like **InfluxDB** or **Prometheus**
This isn't realized in the PoC because the time series databases are normally supposed to save data time limited (see Prometheus) or are not fully compatible with the application setup (InfluxDB and Quarkus)
As result I selected a **MariaDB** database even it shouldn't be the first choice for other reasons.

![PoC DataFormat](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-Database.jpg "PoC DataFormat")




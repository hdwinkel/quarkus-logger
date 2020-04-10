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

![PoC Data Flow](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-Data-Flow.jpg "PoC Data Flow)

The idea behind is to simulate an industry IoT edge landscape where typically different edge stages are involved:

1. Sensors/Actuators
2. Gateways, Data Acquisitions, Controlers
3. Edge IT, pre-processing
4. Data Centres Cloud

see e.g. [IoT Architecture Explained](https://medium.com/datadriveninvestor/4-stages-of-iot-architecture-explained-in-simple-words-b2ea8b4f777f "IoT Architecture Explained")


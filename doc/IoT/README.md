# IoT / Data producing part of the PoC
This chapter describes the environment and development aspects of the PoC related to the data producing side
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

## Let's start with the sensors
The essential element in the IoT parts of the PoC is the ESP-8266
It is used in two different scenarios:
![The IoT side of the PoC](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-IoT-overview.jpg "IoT side of PoC")
1. ESP-8266-01 to collect data produced by a DHT-22 temperature/humidity sensor, flashed with a self written software (see code)
2. ESP-8266-12 as an actor inside a WiFi power plug (I used an OBI with an ESP inside, flashed with the
[Tasmota Firmware](https://github.com/arendst/Tasmota "Tasmota Git repository")

The procedure for the DHT-22 is quite common:
1. connect the DHT sensor to the ESP on a breadboard
2. use the Arduino Development software to flash the code
There are some more professional soldered solutions but for the prototype it is 'good enough'


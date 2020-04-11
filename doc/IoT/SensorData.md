## Let's start with the sensors
The essential element in the IoT parts of the PoC is the ESP-8266
It is used in two different scenarios:
![The IoT side of the PoC](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-IoT-overview.jpg "IoT side of PoC")
1. ESP-8266-01 to collect data produced by a DHT-22 temperature/humidity sensor, flashed with a self written software (see code)
2. ESP-8266-12 as an actor inside a WiFi power plug (I used an OBI with an ESP inside, flashed with the
[Tasmota Firmware](https://github.com/arendst/Tasmota "Tasmota Git repository")

####The procedure for the **DHT-22** is quite common:
1. connect the DHT sensor to the ESP on a breadboard
2. use the Arduino Development software to flash the code
There are some more professional soldered solutions but for the prototype it is 'good enough'
![The DHT-22 sensor plugged to the ESP8266-01](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-Sensor.jpg "The DHT-22 sensor plugged to the ESP8266-01")

There are a lot of examples how to use the DHT-22
e.g.
[ESP8266-01 and DHT-22](https://i2.wp.com/randomnerdtutorials.com/wp-content/uploads/2019/05/dht22-esp-01.png?ssl=1 "ESP8266-01 and DHT-22")

**To remark:**
The practical tests with 5 different similar sensors over some weeks showed that the reliability and stability isn't really good. There were significant differences in the sensor as well in the ESP8266 quality.
One of the root causes is definetely the power supply - the best values showed some regulated ones. For the voltage regulation it is a good idea to use step-down modules.

The sensor data of the Temperature and Humidity data from DHT22 can be made visible with a **Grafana** dashboard:

![Grafana Sensor Data](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-Grafana-Sensor-Values.jpg "Grafana Sensor Data")

####The power plug OBI
There are some different cheap WiFi controlled power plugs on the market. Mostly all of them have the problem to call in to a Chines web server what for privacy reasons isn't a good idea.
A better solution is the option to flash the Tasmota software on the embedded ESP8266-12.
The procedure isn't so common but described in different tutorials e.g.
[OBI and Tasmota](https://joergnapp.de/sonoff-tasmota-obi-steckdose-flashen-ohne-loetarbeiten/ "OBI and Tasmota")

The result of soldered pins could look like:
![OBI with ESP8266](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-OBI.jpg "OBI with ESP8266")




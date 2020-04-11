## Solax Data unlocking
I have on my roof Solar panels, controlled by an inverter from the vendor Solax.

![Solax WiFi stick](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-Solax.jpg "Solax WiFi stick")

The values from the inverter are transmitted to the vendors Solax-Cloud and cannot be read directly from the Solax cloud

Some experiments with a network sniffer showed that there is an option to unlock these data with a local call to the Inverter as he acts in a WiFi mixed mode (as a WiFi client and AP)

The **Python** code to access the Solax inverter looks like
```
#! /usr/bin/python3

import requests

custom_header = {'user-agent': 'Mozilla/5.0 (Linux; Android 9; AOSP on IA Emulator Build/PSR1.180720.117; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/69.0.3497.100 Mobile Safari/537.36', 'Content-Type': 'application/x-www-form-urlencoded', 'X-Requested-With': 'com.solaxcloud.starter'}

r = requests.post('http://5.8.8.8/?optType=ReadRealTimeData', headers=custom_header)

response = r.json()
```

As a result you get a JSON format:
```
{"type":"X1-Hybiyd-G3","SN":"SWRPSFVXXX","ver":"2.32.6","Data":[0.0,0.0,0.0,0.0,3.4,234.8,831,30,4.0,198.1,-6,0,0,108.60,-8.00,-874,25.0,0.59,0.0,138.3,0.0,87.00,0,0,0,0.0,0.0,0.0,0.0,0,0,0,0.0,0.0,0.0,0.0,0,0,0,0.0,0.0,276.02,1419.23,0,0,0,0,0,0,0,49.99,0,0,0.0,0.0,0,0.00,0,0,0,0.00,0,8,0,0,0.00,0,8,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0.00,0.00],"Information":[3.680,3,"X1-Hybiyd-G3","H1E372F2028XXX",1,3.11,0.00,3.13,1.05]}
```
The values of interest are in the Data-List

* Data[21]: Battery Level
* Data[15]: Battery Capacity
* Data[10]: Grid Feed In
* Data[6]: Inverter Current
* Data[9]: Solar Total



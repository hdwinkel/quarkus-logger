## Power-Meter Data Unlocking

In Germany there are very often used DDR3 Power Meters, in my case an EBZ:
[DDR3 Electric Meter](https://www.ebzgmbh.de/fileadmin/ebz_de/content/downloads/datenblatt_dd3.pdf "DDR3 Electric Meter")
The problem with the Power Meter is the fact that there is no direct digital API to access but an optical IR interface
The transmission protocol is defined and can be read with an IR reader e.g. with 
[Emlog Reader](https://https://shop.weidmann-elektronik.de/media/files_public/9d73b590bf0752a5beff32d229d4497d/HowToRaspberryPi.pdf "Emlog Reader")

Applied it looks like this:
![Emlog Reader applied](https://github.com/hdwinkel/quarkus-logger/blob/develop/doc/pictures/DL-Power.jpg "Emlog Reader applied")

the **Python** code for initializing the serial interface could look like:
```
#! /usr/bin/python3

import serial
import datetime
import pathlib

ser = serial.Serial(port='/dev/ttyUSB0', baudrate=9600, bytesize=7, parity='E', stopbits=1, timeout=None, xonxoff=0, rtscts=0)
s = ser.read(1000)       # read up to one hundred bytes
                         # or as much is in the buffer
print(str(s))
```
But be aware that the serial interface could be different for different types of meters.
As a result the reader will extract output like:
```
b'\x00\n
/EBZ5DD32R06ETA_107
\r\n\r\n
1-0:0.0.0*255(1EBZ0100539XXX)
\r\n
1-0:96.1.0*255(1EBZ0100539XXX)
\r\n
1-0:1.8.0*255(000491.31445555*kWh)
\r\n
1-0:2.8.0*255(000113.19516081*kWh)
\r\n
1-0:16.7.0*255(000959.36*W)
\r\n
1-0:36.7.0*255(000156.02*W)
\r\n
1-0:56.7.0*255(000241.27*W)
\r\n
1-0:76.7.0*255(000562.07*W)
\r\n
1-0:96.5.0*255(001C0104)
\r\n
0-0:96.8.0*255(005D0997)
\r\n!\r\n
```
where the values are the 
* import from grid (1.8.0)
* export to grid (2.8.0)
* current consumption (16.7.0)
* different phase values

Remark: Normally the data are not with this higher precision. To get the detailed values there have to be applied an ID.
The needed ID can be requested by the power provider for free.

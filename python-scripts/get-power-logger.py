#! /usr/bin/python3

import serial
import datetime
import pathlib

import json
import requests
import os

# node-red server
url = 'http://192.168.10.210:1880/EBZ'

isodate = datetime.datetime.now().isoformat()
device = "EBZ5DD32R06ETA_XXX"
initdir = "/media/pi/extern500/logger" # must exist

# send payload to server
def send_to_server(payload):
    headers = {'Content-type': 'application/json'}
#   data = json.dumps(payload)
    print("json: " + payload);
    response = requests.post(url, payload, headers=headers)


body_text="{ \"Values\": ["

ser = serial.Serial(port='/dev/ttyUSB0', baudrate=9600, bytesize=7, parity='E', stopbits=1, timeout=None, xonxoff=0, rtscts=0)
s = ser.read(1000)       # read up to one hundred bytes
                         # or as much is in the buffer

record = (str(s).split("/EBZ5DD32R06ETA_XXX"))[1] # print the 2. record in buffer
record = record.replace('\\r\\n\\r\\n' ,'')
record = record.replace('\\r\\n!\\r\\n' ,'')

field_array = record.split('\\r\\n')
for i in range(len(field_array)):
	body_text = body_text + "\"" + field_array[i] + "\""
	if(i<len(field_array)-1):
		body_text = body_text + ","

body_text = body_text + "] } "

# print(body_text) # 



transfer = "{\n    \"Header\": { \"Timestamp\": \"" + isodate + "\", \"Device\": \"" + device + "\"},\n    \"Body\": " + body_text +"\n}"

# print(transfer)

today = datetime.date.today()
dirname = device + "-" + today.strftime('%Y-%m-%d')
pathlib.Path(initdir + "/" + dirname).mkdir(parents=True, exist_ok=True) 

filename = device + "-" + isodate + ".json"
data_file = open(initdir + "/" + dirname + "/" + filename, 'w')

with open(initdir + "/" + dirname + "/" + filename, 'w') as f:
    f.write(transfer)

send_to_server(transfer)


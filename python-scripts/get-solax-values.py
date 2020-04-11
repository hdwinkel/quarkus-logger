#! /usr/bin/python3

import requests
import datetime
import pathlib

isodate = datetime.datetime.now().isoformat()
device = "Solax-X1"
initdir = "/media/pi/extern500/logger" # must exist

custom_header = {'user-agent': 'Mozilla/5.0 (Linux; Android 9; AOSP on IA Emulator Build/PSR1.180720.117; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/69.0.3497.100 Mobile Safari/537.36', 'Content-Type': 'application/x-www-form-urlencoded', 'X-Requested-With': 'com.solaxcloud.starter'}

r = requests.post('http://5.8.8.8/?optType=ReadRealTimeData', headers=custom_header)

response = r.json()
# print(r.text)

transfer = "{\n    \"Header\": { \"Timestamp\": \"" + isodate + "\", \"Device\": \"" + device + "\"},\n    \"Body\": " + r.text +"\n}"

# print(transfer)

today = datetime.date.today()
dirname = device + "-" + today.strftime('%Y-%m-%d')
pathlib.Path(initdir + "/" + dirname).mkdir(parents=True, exist_ok=True) 

filename = device + "-" + isodate + ".json"
# data_file = open("/media/logger/"+ dirname + "/" + filename, 'w')

with open(initdir + "/" + dirname + "/" + filename, 'w') as f:
    f.write(transfer)


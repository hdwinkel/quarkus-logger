#!/usr/bin/python3

# read a filetree and send file bodies to Node-Red for further processing
import json
import requests
import os

# node-red server
url = 'http://192.168.10.128:1880/EBZ'

# travers the file-tree starting in the current directory
def process_file_tree():
    for root, dirs, files in os.walk(".", topdown = False):
        for name in files:
            print(os.path.join(root, name))
            filename = os.path.join(root, name)
            read_file_and_send(filename)


# send payload to server
def send_to_server(payload):
    headers = {'Content-type': 'application/json'}
#   data = json.dumps(payload)
    print("json: " + payload);
    response = requests.post(url, payload, headers=headers)



# read content of files and send as payload to server
def read_file_and_send(filename):
    payload=""
    try:
        f = open(filename, "r")
        for x in f:
            print(x) 
            payload=payload + x
        f.close()
        send_to_server(payload)
    except:
        print("error with file: "+file)
            
    else:
        return True

process_file_tree()


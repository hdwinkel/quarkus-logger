import requests

payload = {}

custom_header = {'user-agent': 'Mozilla/5.0 (Linux; Android 9; AOSP on IA Emulator Build/PSR1.180720.117; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/69.0.3497.100 Mobile Safari/537.36', 'Content-Type': 'application/x-www-form-urlencoded', 'X-Requested-With': 'com.solaxcloud.starter'}

r = requests.post('http://5.8.8.8/?optType=ReadRealTimeData', headers=custom_header)

response = r.json()
# print(r.json())
# print(response['Data'])

# print(r.text)

data = response['Data']
for i in range(len(data)):
	print(data[i])



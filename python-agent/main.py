import requests
import time
import psutil

BACKEND_URL = "http://localhost:8080/system/issue"

def check_system():
    cpu = psutil.cpu_percent()

    if cpu > 0:
        issue = {
            "type": "HIGH_CPU",
            "severity": "HIGH",
            "status": "DETECTED",
            "message": f"CPU usage is {cpu}%",
            "timestamp": int(time.time())
        }

        requests.post(BACKEND_URL, json=issue)

while True:
    check_system()
    time.sleep(5)
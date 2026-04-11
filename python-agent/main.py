from fastapi import FastAPI
import requests
import time
import psutil
import threading

app = FastAPI()

BACKEND_URL = "http://localhost:8080/system/issue"

# 🔥 Background monitoring
def check_system():
    while True:
        cpu = psutil.cpu_percent(interval=0)

        if cpu > 80:
            issue = {
                "type": "HIGH_CPU",
                "severity": "HIGH",
                "status": "DETECTED",
                "message": f"CPU usage is {cpu}%",
                "timestamp": int(time.time())
            }

            try:
                res = requests.post(BACKEND_URL, json=issue)
                print("✅ Issue sent:", res.status_code)
            except Exception as e:
                print("❌ Error:", e)

        time.sleep(5)

# 🔥 API endpoint
@app.get("/system/stats")
def stats():
    print("📊 Stats API called")  # DEBUG
    return {
        "cpu": psutil.cpu_percent(interval=0),
        "memory": psutil.virtual_memory().percent,
        "disk": psutil.disk_usage('/').percent
    }

# 🔥 Start background thread
@app.on_event("startup")
def startup_event():
    thread = threading.Thread(target=check_system)
    thread.daemon = True
    thread.start()
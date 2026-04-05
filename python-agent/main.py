from fastapi import FastAPI
import psutil

app = FastAPI()

@app.get("/")
def root():
    return {"message": "System Health API running"}

@app.get("/system/stats")
def get_stats():
    return {
        "cpu": psutil.cpu_percent(),
        "memory": psutil.virtual_memory().percent
    }
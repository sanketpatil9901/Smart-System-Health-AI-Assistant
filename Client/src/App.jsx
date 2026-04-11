import { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { getHealth, fixIssue } from "./services/api";

import StatCard from "./components/StatCard";
import HealthScore from "./components/HealthScore";
import IssueList from "./components/IssueList";

function App() {
  const [stats, setStats] = useState({});
  const [issues, setIssues] = useState([]);

  // 🔥 Fetch health data
  const loadHealth = async () => {
    const res = await getHealth();
    setStats(res.data);
  };

  // 🔥 WebSocket setup
  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");

    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
    });

    client.onConnect = () => {
      client.subscribe("/topic/issues", (msg) => {
        const issue = JSON.parse(msg.body);

        setIssues((prev) => [issue, ...prev]);
      });
    };

    client.activate();

    return () => client.deactivate();
  }, []);

  // 🔥 Load stats every 5 sec
  useEffect(() => {
    loadHealth();
    const interval = setInterval(loadHealth, 5000);
    return () => clearInterval(interval);
  }, []);

  const handleFix = async (id) => {
    await fixIssue(id);
  };

  return (
    <div style={{ padding: "20px", fontFamily: "Arial" }}>
      <h1>🧠 System Health Dashboard</h1>

      <div style={{ display: "flex", gap: "20px" }}>
        <StatCard title="CPU" value={stats.cpu || 0} />
        <StatCard title="Memory" value={stats.memory || 0} />
        <StatCard title="Disk" value={stats.disk || 0} />
      </div>

      <br />

      <HealthScore score={stats.healthScore || 0} />

      <br />

      <IssueList issues={issues} onFix={handleFix} />
    </div>
  );
}

export default App;
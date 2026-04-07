import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { useEffect } from "react";

function App() {
  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");

    const client = new Client({
      webSocketFactory: () => socket,
      debug: (str) => console.log("STOMP Debug:", str),
      reconnectDelay: 5000,
    });

    client.onConnect = () => {
      console.log("✅ Connected to WebSocket");

      client.subscribe("/topic/issues", (message) => {
        const issue = JSON.parse(message.body);
        console.log("🚨 Issue received:", issue);
        alert(`Issue: ${issue.type}`);
      });
    };

    client.onStompError = (frame) => {
      console.error("❌ Broker error:", frame);
    };

    client.activate();

    return () => client.deactivate();
  }, []);

  return <div>System Health Dashboard</div>;
}

export default App;
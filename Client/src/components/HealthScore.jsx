export default function HealthScore({ score }) {
  return (
    <div style={{
      padding: "20px",
      background: "#4CAF50",
      color: "white",
      borderRadius: "10px",
      textAlign: "center"
    }}>
      <h2>Health Score</h2>
      <h1>{score}</h1>
    </div>
  );
}
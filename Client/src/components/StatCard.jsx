export default function StatCard({ title, value }) {
  return (
    <div style={{
      padding: "20px",
      background: "#1e1e2f",
      color: "white",
      borderRadius: "10px",
      width: "150px",
      textAlign: "center"
    }}>
      <h3>{title}</h3>
      <p style={{ fontSize: "24px" }}>{value}%</p>
    </div>
  );
}
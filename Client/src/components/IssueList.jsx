export default function IssueList({ issues, onFix }) {
  return (
    <div>
      <h2>Issues</h2>
      {issues.map((issue) => (
        <div key={issue.id} style={{
          margin: "10px",
          padding: "10px",
          background: "#ff4d4d",
          color: "white",
          borderRadius: "5px"
        }}>
          <p>{issue.type}</p>
          <p>{issue.message}</p>
          <p>💡 {issue.suggestion}</p>

          <button onClick={() => onFix(issue.id)}>
            Fix
          </button>
        </div>
      ))}
    </div>
  );
}
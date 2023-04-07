import React from "react"

export const Title: React.FC<{ title: string }> = ({ title }) => {
  return (
    <p style={{
      width: "100%",
      color: "#ECF4FF",
      fontSize: "2rem",
      fontWeight: "bold",
      textAlign: "center",
      margin: "2% 0%"
    }}>
      {title}
    </p>
  );
}
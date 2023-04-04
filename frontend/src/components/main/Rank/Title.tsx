import React from "react"

export const Title: React.FC<{ title: string }> = ({ title }) => {
  return (
    <p style={{color: "#ECF4FF", fontWeight: "bold", width: "100%"}}>
      {title}
    </p>
  );
}
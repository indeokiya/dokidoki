import styled from 'styled-components';
const InfoCard: React.FC<{ icon: string; text: string; primary: boolean }> = (props) => {
  const StyledDiv = styled.div`
    height: 300px;
    box-size: border-box;
    text-align: center;
    padding: 30px;
  `;

  const Describe = styled.p`
    padding: 0px;
    margin: 0px;
  `;

  return (
    <StyledDiv style={props.primary ? { color: 'white', backgroundColor: '#3A77EE' } : {}}>
      <h2>{props.primary}</h2>
      <h1>{props.icon}</h1>
      <Describe>{props.text}</Describe>
    </StyledDiv>
  );
};

export default InfoCard;

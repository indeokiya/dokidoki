import styled from 'styled-components';

type Info={
  icon:string,
  title:string,
  text:string
}


const InfoCard: React.FC<{info:Info, primary:boolean}> = (props) => {
  const {icon, title, text} = props.info
  return (
    <StyledDiv style={props.primary ? { color: 'white', backgroundColor: '#3A77EE' } : {}}>
      <h2>{props.primary}</h2>
      <StyleIcon src={icon}/>
      <h3>{title}</h3>
      <Describe>{text}</Describe>
    </StyledDiv>
  );
};

export default InfoCard;

const StyleIcon = styled.img`
  width:50px;
  height:50px;
    margin-bottom:20px;

`


const StyledDiv = styled.div`
height: 400px;
box-sizing: border-box;
text-align: center;
padding: 30px;
`;

const Describe = styled.p`
padding: 0px;
margin: 0px;
`;

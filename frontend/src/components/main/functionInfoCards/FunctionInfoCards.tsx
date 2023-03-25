import { Grid } from '@mui/material';
import InfoCard from './InfoCard';

const FunctionInfoCards = () => {
  const cardList = [
    {
      icon: 'icon',
      text: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.",
    },
    {
      icon: 'icon',
      text: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.",
    },
    {
      icon: 'icon',
      text: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.",
    },
  ];

  return (
    <Grid container spacing={3}>
      {cardList.map((data, i) => {
        return (
          <Grid item xs={4}>
            <InfoCard primary={i === 1 ? true : false} key={i} icon={data.icon} text={data.text} />
          </Grid>
        );
      })}
    </Grid>
  );
};

export default FunctionInfoCards;

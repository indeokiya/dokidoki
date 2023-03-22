import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import sampleImg from '../../../assets/image/phone1.png'
import { useState } from 'react';


const Content = () => {

    const [isHovered, setIsHovered] = useState(false); 
    

//마우스가 올라가면 raised 옵션을 주고 싶다. 
  return (
    <Card 
        onMouseOver={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)} 
        raised={isHovered}
        sx={ isHovered? {cursor:'pointer'}:{}}
       
        >
        
      <CardMedia
        component="img"
        alt="green iguana"
        height="250"
        image={sampleImg}
        
      />
      <CardContent>
        
        <Typography gutterBottom variant="h5" component="div">
          게시글 제목~
        </Typography>
        <Typography variant="body2" color="text.secondary" align="left">
          Lizards  
        </Typography>
        <Typography variant="body2" color="text.secondary" align="right">
          Lizards  
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small">Share</Button>
        <Button size="small">Learn More</Button>
      </CardActions>
    </Card>
  );
}

export default Content;
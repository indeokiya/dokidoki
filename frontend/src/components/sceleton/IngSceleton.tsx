import Skeleton from '@mui/material/Skeleton';
import Grid from '@mui/material/Grid';



//로딩되기전 
const IngSceleton = () =>{
    const page= [1,2,3,4,5,6,7,8,9,10,11,12];

    return ( 
    <Grid container spacing={2} paddingLeft={2} mt={3} maxWidth="100%">
    {page.map((num) =>(
        <Grid key={num} item xs={4} mb={4} height={415}>
            <Skeleton variant="rectangular"  height={215} />
            <Skeleton variant="text" sx={{ fontSize: '2rem', mt:4 }} />
            <Skeleton variant="text" sx={{ fontSize: '1rem',mt:1 }} />
            <Skeleton variant="text" sx={{ fontSize: '1rem' }} />
            <Skeleton variant="text" sx={{ fontSize: '1rem' }} />
            <Skeleton variant="text" sx={{ fontSize: '2rem', mt:1 }} />
        </Grid>
    ))}
    </Grid>
    )

}

export default IngSceleton
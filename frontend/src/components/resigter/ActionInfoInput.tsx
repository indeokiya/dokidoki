import {
  Paper,
  Grid,
  Typography,
  Divider,
  TextField,
  Input,
} from "@mui/material";
import { useState } from "react";
import AddressInput from "./AddressInput";
import InputAdornment from "@mui/material/InputAdornment";
import MapIcon from "@mui/icons-material/Map";

const ActionInfoInput = () => {
  const [address, setAddress] = useState("");
  const [addressInputVisible, setAddressInputVisible] = useState(false);

  return (
    <>
      <Paper elevation={3} sx={{ width: 600, padding: 10 }}>
        <Grid container>
          <Grid item xs={12}>
            <Typography variant="h5" fontWeight="bold" color={"blue"}>
              경매 정보
            </Typography>
            <Divider sx={{ margin: "2rem 0px" }} />
          </Grid>
          <Grid item xs={2}>
            <Typography variant="subtitle1">시작 가격 : </Typography>
          </Grid>
          <Grid item xs={10} mb={2}>
            <TextField
              id="outlined-basic"
              variant="outlined"
              fullWidth
              type="number"
            />
          </Grid>

          <Grid item xs={2}>
            <Typography variant="subtitle1">겅매 단위 : </Typography>
          </Grid>
          <Grid item xs={10} mb={2}>
            <TextField
              id="outlined-basic"
              variant="outlined"
              fullWidth
              type="number"
            />
          </Grid>

          <Grid item xs={2}>
            <Typography variant="subtitle1">종료 시간 : </Typography>
          </Grid>
          <Grid item xs={10} mb={2}>
            <TextField
              id="outlined-basic"
              variant="outlined"
              fullWidth
              type="date"
            />
          </Grid>

          <Grid item xs={2}>
            <Typography variant="subtitle1">거래 장소 : </Typography>
          </Grid>
          <Grid item xs={10} mb={2}>
            <TextField
              id="outlined-basic"
              fullWidth
              type="text"
              value={address}
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <MapIcon
                      onClick={() => {
                        setAddressInputVisible(!addressInputVisible);
                      }}
                    />
                  </InputAdornment>
                ),
              }}
            />

            {addressInputVisible && <AddressInput setAddress={setAddress} />}
          </Grid>
        </Grid>
      </Paper>
    </>
  );
};

export default ActionInfoInput;

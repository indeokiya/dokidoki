import { Button } from '@mui/material';
import DaumPostcode from 'react-daum-postcode';

const AddressInput: React.FC<{
  setVisible: (data: boolean) => void;
  dataRef: any;
}> = (props) => {
  const handleComplete = (data: any) => {
    console.log("addressData >>> ",data)
    let fullAddress = data.address;
    let extraAddress = '';

    // if (data.addressType === 'R') {
    //   if (data.bname !== '') {
    //     extraAddress += data.bname;
    //   }
    //   if (data.buildingName !== '') {
    //     extraAddress += extraAddress !== '' ? `,${data.buildingName}` : data.buildingName;
    //   }
    //   fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
    // }
    
    props.dataRef.current.meeting_place = fullAddress;
    // console.log("fullAddress >> ", props.dataRef.current.meetingPlace);
    props.setVisible(false);
  };

  return (
    <>
      <div>
        <DaumPostcode onComplete={handleComplete} />
        <Button
          sx={{ mt: 3 }}
          fullWidth
          onClick={() => {
            props.setVisible(false);
          }}
          variant="outlined"
          color="error"
        >
          close
        </Button>
      </div>
    </>
  );
};

export default AddressInput;

import { useState} from "react";
import { Button } from "@mui/material";
import DaumPostcode from "react-daum-postcode";

const AddressInput:React.FC<{setAddress:(address:string)=>void}> = (props) => {
  
  const handleComplete = (data: any) => {
    let fullAddress = data.address;
    let extraAddress = "";

    if (data.addressType === "R") {
      if (data.bname !== "") {
        extraAddress += data.bname;
      }
      if (data.buildingName !== "") {
        extraAddress +=
          extraAddress !== "" ? `,${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== "" ? ` (${extraAddress})` : "";
    }
    props.setAddress(fullAddress);
  };

  return (
    <>
        <div>
          <DaumPostcode onComplete={handleComplete} />
        </div>
    </>
  );
};

export default AddressInput

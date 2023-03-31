import React, { useState, useEffect } from 'react';
import { Map, MapMarker, CustomOverlayMap } from 'react-kakao-maps-sdk';
import Badge from '@mui/material/Badge';
import Button from '@mui/material/Button';
import styled from 'styled-components';

// showMap={showMap} location={meeting_place}
const MeetingPlace: React.FC<{ location: string }> = (props) => {
  let { location } = props;
  // let showMap = true;
  // let location = "사당로 16바길 48"
  let [showMap, setShowMap] = useState(false);

  const [mapCenter, setMapCenter] = useState({ lat: 37.5665, lng: 126.978 });
  const [markerPosition, setMarkerPosition] = useState({ lat: 37.5665, lng: 126.978 });

  const handleSearch = () => {
    setShowMap(!showMap);
    const geocoder = new kakao.maps.services.Geocoder();
    geocoder.addressSearch(location, (result, status) => {
      if (status === kakao.maps.services.Status.OK) {
        let { y: lat, x: lng } = result[0];
        setMarkerPosition({ lat: Number(lat), lng: Number(lng) });
        setMapCenter({ lat: Number(lat), lng: Number(lng) });
      } else {
        alert('주소 검색에 실패했습니다.');
      }
    });
  };

  return (
    <StyledDiv>
      <Button
        variant="outlined"
        onClick={handleSearch}
        sx={{ width: '100%', display: 'block', margin: '0 auto' }}
      >
        지도 보기
      </Button>
      {showMap && (
        <Map
          center={mapCenter}
          style={{ width: '100%', height: '400px', marginBottom: '3rem' }}
          draggable={true}
          zoomable={false}
        >
          <MapMarker position={markerPosition} />
          <CustomOverlayMap position={markerPosition}>
            <div style={{ backgroundColor: '#fff', color: '#000' }}>
              <Badge color="primary" badgeContent={location} />
            </div>
          </CustomOverlayMap>
        </Map>
      )}
    </StyledDiv>
  );
};

export default MeetingPlace;

const StyledDiv = styled.div``;

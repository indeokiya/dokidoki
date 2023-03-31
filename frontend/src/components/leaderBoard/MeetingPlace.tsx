import React, { useState, useEffect } from 'react';
import { Map, MapMarker } from 'react-kakao-maps-sdk';


// showMap={showMap} location={meeting_place}
const MeetingPlace:React.FC<{location:string}> = (props) => {
  let { location} = props;
  // let showMap = true;
  // let location = "사당로 16바길 48"
  let [showMap ,setShowMap] = useState(false);


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
    <div>
      <button onClick={handleSearch}>지도보기</button>
      {showMap &&
        <Map center={mapCenter} style={{ width: '400px', height: '400px' }}>
          <MapMarker position={markerPosition} />
        </Map>
        }
      
    </div>
  );

};

export default MeetingPlace;

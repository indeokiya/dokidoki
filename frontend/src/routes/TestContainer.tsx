import styled, { keyframes } from 'styled-components';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Tooltip from '@mui/material/Tooltip';
import Chip from '@mui/material/Chip';
import { useRecentPopularInfo } from 'src/hooks/recentPopularInfo';
import { KafkaLog } from 'src/datatype/datatype';
import bidImgSrc from '../assets/image/bid.jpg';
import clickImgSrc from '../assets/image/click.jpg';
import React from 'react';
import { useNavigate } from 'react-router-dom';
import Box from '@mui/material/Box';

function numberFormat(price: number | null) {
  return price?.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ',') + ' 원';
}

const TestContainer = () => {
 return<></>
  
};

export default TestContainer;

import { useState, useCallback, useRef } from 'react';
import Grid from '@mui/material/Grid';
import axios from 'axios';
import { Button } from '@mui/material';
import DaumPostcode from 'react-daum-postcode';
import { useQuery } from '@tanstack/react-query';
import { auctionAPI } from '../api/axios';

type Post = {
  auction_id: number;
  auction_title: string;
  product_name: string;
  category_name: string;
  meeting_place: string;
  offer_price: number;
  cur_price: string | null;
  remain_hours: number;
  remain_minute: number;
  remain_seconds: number;
  is_my_interest: boolean;
  is_my_auction: boolean;
  auction_image_urls: string[];
};

const TestContainer = () => {
  let { data, isLoading } = useQuery<Post[]>(['auctionsList'], () => {
    return auctionAPI.get('/lists/in-progress').then(({ data }) => {
      console.log("data:",data.data); //data
      console.log("data.content:",data.content); //data
      return data.data.contents;
    });
  });

  function printData(data: Post[]) {
    return;
  }

  return (
    <>
      <Grid container>
        {!isLoading ? (
          <Grid xs>
            {data !== null ? (
              <div>
                {data?.map((res, i) => (
                  <li key={i}>{res.auction_title}</li>
                ))}
              </div>
            ) : (
              <div>없지롱~</div>
            )}
          </Grid>
        ) : (
          <Grid>
            <p>로딩중</p>
          </Grid>
        )}
      </Grid>
    </>
  );
};

export default TestContainer;

import axios from "axios";

const auctionAPI = axios.create({
  baseURL: process.env.REACT_APP_AUCTION_SERVER_BASE_URL,
  headers: {
    "Content-Type": "application/json"
  }
});

auctionAPI.defaults.timeout = 3000;

const bidAPI = axios.create({
  baseURL: process.env.REACT_APP_BID_SERVER_BASE_URL,
  headers: {
    "Content-Type": "application/json"
  }
});

bidAPI.defaults.timeout = 3000;

export {auctionAPI, bidAPI};


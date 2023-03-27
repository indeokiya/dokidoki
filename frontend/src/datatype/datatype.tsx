
//경매가 진행중인 물건
export type Post = {
    auction_id: number;
    auction_title: string;
    product_name: string;
    category_name: string;
    meeting_place: string;
    offer_price: number;
    cur_price: number;
    price_size:number;
    remain_hours: number;
    remain_minutes: number;
    remain_seconds: number;
    is_my_interest: boolean;
    is_my_auction: boolean;
    auction_image_url: string;
  };



  //경매가 종료된 물건
export type endPost =  {
  auction_id: number;
  auction_title: string;
  product_name: string;
  category_name: string;
  offer_price: number;
  final_price: number;
  start_time: string;
  end_time: string;
  auction_image_urls: string[],
  is_sold_out: boolean
}


 
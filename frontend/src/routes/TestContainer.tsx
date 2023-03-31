const TestContainer = () =>{
  let data = false;

  function login(){
    localStorage.setItem("access_token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkb2tpZG9raS5jb20iLCJpYXQiOjE2ODAyMzM0NjEsImV4cCI6MTY4MDIzNzA2MSwidXNlcl9pZCI6M30.5fKOXLEsqb-4_kB9IsBlq3nE6WDZFvW-MOQTpoQq0vA")
    localStorage.setItem("refresh_token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkb2tpZG9raS5jb20iLCJpYXQiOjE2ODAyMzM0NjEsImV4cCI6MTY4MTUyOTQ2MSwidXNlcl9pZCI6MywibmFtZSI6Iuq5gOuylOyLnSIsImVtYWlsIjoia2JnNzYzNUBuYXZlci5jb20iLCJwaWN0dXJlIjoiaHR0cHM6Ly9kb2tpZG9raS5zMy5hcC1ub3J0aGVhc3QtMi5hbWF6b25hd3MuY29tL3Byb2ZpbGVzLzBlNTAwZTYzLTZmYWUtNDczZi04MjE0LWYwMjcyYTlmMjlkMi13YWxscGFwZXJiZXR0ZXIuY29tXzI1NjB4MTQ0MCUyMCUyODclMjkuanBnIiwicHJvdmlkZXIiOiJLQUtBTyJ9.k4M3l26bPQmy09F8XvwggdNCJnKmgr4TgAvffEvRTaM")
    localStorage.setItem("user_info",`{"iss":"dokidoki.com","iat":1680233167,"exp":1681529167,"user_id":3,"name":"김범식","email":"kbg7635@naver.com","picture":"https://dokidoki.s3.ap-northeast-2.amazonaws.com/profiles/0e500e63-6fae-473f-8214-f0272a9f29d2-wallpaperbetter.com_2560x1440%20%287%29.jpg","provider":"KAKAO","is_logged_in":true}`)

  }
  login()



return (
  <div>
    {data}
  </div>
)
}

export default TestContainer;
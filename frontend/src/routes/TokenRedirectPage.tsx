import { useSearchParams, useNavigate } from "react-router-dom";
import {useEffect} from 'react'
import { useSetRecoilState } from "recoil";
import { userInfoState } from "src/store/userInfoState";

const TokenRedirectPage = () => {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const setUserInfoState = useSetRecoilState(userInfoState);

    function parseJwt (token : string) {
        var base64Url = token.split('.')[1];
        var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
    
        return jsonPayload;
    }

    useEffect(() => {
        const access_token = searchParams.get('access_token');
        const refresh_token = searchParams.get('refresh_token');
        
        if(refresh_token === "null"){
            alert("이용 정지 당한 사용자입니다.\n정지 해제 날짜 : " + access_token);
            navigate('/',{replace:true});
            return;
        }

        if(access_token) localStorage.setItem('access_token', access_token);
        // else console.log('엑세스 토큰 안들어감');
        
        if(refresh_token) {
            localStorage.setItem('refresh_token', refresh_token);
            
            let user_info = JSON.parse(parseJwt(refresh_token));
            user_info = {...user_info, is_logged_in: true};
            setUserInfoState(user_info);
        }
        // else console.log('리프레시 토큰 안들어감');

        navigate('/',{replace:true})
    }, []);

    return null;
};

export default TokenRedirectPage;
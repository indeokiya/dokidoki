import { useNavigate } from "react-router-dom";

export const Logout = () =>{
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
    localStorage.removeItem("user_info");

    const navigate = useNavigate();

    navigate('/', {replace : true});

    return null;
}
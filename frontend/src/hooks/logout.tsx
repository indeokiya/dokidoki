export const Logout = () =>{
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
    localStorage.removeItem("user_info");

    window.location.href = window.location.origin;
}

export const RedirectLogin = ()=>{
    window.location.href = window.location.origin + '/login';
}
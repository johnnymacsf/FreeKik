import axios from "axios";

const API = axios.create({
    baseURL: "http://localhost:8080"
});

API.interceptors.request.use((req) => {
    if(typeof window !== 'undefined'){
        const token = localStorage.getItem("token");
        if(token){
            req.headers.Authorization = `Bearer ${token}`;
        }
    }
    return req;
});

export default API;
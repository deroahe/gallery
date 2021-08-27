import axios from "axios";
import authHeader from "./auth-header";

// const API_URL_UTIL = "https://deroahe-gallery-backend.herokuapp.com/api/util";
const API_URL_UTIL = "http://localhost:8080/api/util";


const exportExcel = () => {
    return axios.get(API_URL_UTIL + "/export-excel", { headers: authHeader() });
}

const exportExcelFetch = () => {
    return fetch(API_URL_UTIL + "/export-excel", { headers: authHeader() })
}

export default {
    exportExcel,
    exportExcelFetch,
}
import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8080/api/images";

const getAllImages = () => {
    return axios.get(API_URL);
};

const uploadImage = (data) => {
    return axios.post(API_URL, data, { headers: authHeader() })
}

export default {
    getAllImages,
    uploadImage
}
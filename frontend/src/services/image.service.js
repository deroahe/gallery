import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "https://deroahe-gallery-backend.herokuapp.com/api/images";
// const API_URL = "http://localhost:8080/api/images";

const getImageById = (imageId) => {
    return axios.get(API_URL + "/" + imageId);
}

const getAllImages = () => {
    return axios.get(API_URL);
};

const uploadImage = (data) => {
    return axios.post(API_URL, data, { headers: authHeader() })
}

const saveHashtagsToImage = (hashtagsNames, imageId) => {
    return axios.post(API_URL + "/save-image-hashtags", {hashtagsNames, imageId}, { headers: authHeader() })
}

const deleteImage = (imageId) => {
    return axios.post(API_URL + "/delete/" + imageId, null, { headers: authHeader() })
}

export default {
    getAllImages,
    uploadImage,
    getImageById,
    saveHashtagsToImage,
    deleteImage,
}
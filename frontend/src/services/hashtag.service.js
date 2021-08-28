import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "https://deroahe-gallery-backend.herokuapp.com/api/hashtags";
// const API_URL = "http://localhost:8080/api/hashtags";

const postHashtags = (hashtagsNames, imageId) => {
    return axios.post(API_URL, {
        hashtagsNames,
        imageId
    }, { headers: authHeader() })
}

export default {
    postHashtags,
}
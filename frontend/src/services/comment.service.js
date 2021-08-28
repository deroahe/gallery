import axios from "axios";
import authHeader from "./auth-header";

// const API_URL = "https://deroahe-gallery-backend.herokuapp.com/api/comments";
const API_URL = "http://localhost:8080/api/comments";

const postComment = (commentString, userId, imageId) => {
    return axios.post(API_URL, {
        commentString,
        userId,
        imageId
    }, { headers: authHeader() })
}

const findCommentsByImage = (imageId) => {
    return axios.get(API_URL + "/image-comments/" + imageId, { headers: authHeader() })
}

export default {
    postComment,
    findCommentsByImage
}
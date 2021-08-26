import axios from "axios";
import authHeader from "./auth-header";

const API_URL_TEST = "https://deroahe-gallery-backend.herokuapp.com/api/test/";
const API_URL_USERS = "https://deroahe-gallery-backend.herokuapp.com/api/users";
// const API_URL_TEST = "http://localhost:8080/api/test/";
// const API_URL_USERS = "http://localhost:8080/api/users";

const getPublicContent = () => {
    return axios.get(API_URL_TEST + "all");
};

const getUserBoard = () => {
    return axios.get(API_URL_TEST + "user", { headers: authHeader() });
};

const getModeratorBoard = () => {
    return axios.get(API_URL_TEST + "mod", { headers: authHeader() });
};

const getAdminBoard = () => {
    return axios.get(API_URL_TEST + "admin", { headers: authHeader() });
};

const getAllUsers = () => {
    return axios.get(API_URL_USERS, { headers: authHeader() });
};

const getUser = (id) => {
    return axios.get(API_URL_USERS + "/" + id, { headers: authHeader() });
};

const getAllUserImages = (userId) => {
    return axios.get(API_URL_USERS + "/" + userId + "/images",{ headers: authHeader() });
};

const updateUser = (user) => {
    return axios.post(API_URL_USERS + "/update" , user, { headers: authHeader() });
};

const deleteUser = (user) => {
    return axios.post(API_URL_USERS + "/delete", user,{ headers: authHeader() });
};

export default {
    getPublicContent,
    getUserBoard,
    getModeratorBoard,
    getAdminBoard,
    getAllUsers,
    updateUser,
    deleteUser,
    getUser,
};
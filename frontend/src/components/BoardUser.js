import React, { useState, useEffect } from "react";
import EventBus from "../common/EventBus";
import {Link} from "react-router-dom";

import UserService from "../services/user.service";
import ImageService from '../services/image.service';
import AuthService from '../services/auth.service';

const BoardUser = () => {
    const [content, setContent] = useState("");
    const [images, setImages] = useState(undefined);

    useEffect(() => {
        UserService.getUserBoard().then(
            (response) => {
                setContent(response.data);
            },
            (error) => {
                const _content =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                setContent(_content);

                if (error.response && error.response.status === 401) {
                    EventBus.dispatch("logout");
                }
            }
        );
        const user = AuthService.getCurrentUser();
        console.log(user.id);
        retrieveImagesByUserId(user.id);
    }, []);

    const retrieveImagesByUserId = (userId) => {
        if (userId !== undefined)
            ImageService.getAllImagesByUserId(userId)
                .then((r) => { setImages(r.data) })
                .catch((e) => console.log(e));
    }

    return (
        <div className="container">
            {/*<header className="jumbotron">*/}
            {/*    <h3>{content}</h3>*/}
            {/*</header>*/}
            <div className="categoryNameDiv"><h3>Your uploads</h3></div>
            {images !== undefined && images.map((image, key) =>
                <Link key={key} to={"/images/" + image.imageId}>
                    <img key={key} src={image.imageUrl} className="profile-img-card" alt={image.imageUrl}/>
                </Link>
            )}
        </div>
    );
};

export default BoardUser;
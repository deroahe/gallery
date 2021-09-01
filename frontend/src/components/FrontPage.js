import React, { useState, useEffect } from 'react';
import ImageList from './ImageList';
import FileUpload from "./FileUpload";
import ImageService from "../services/image.service";
import AuthService from '../services/auth.service';

const FrontPage = () => {

    const [images, setImages] = useState([]);
    const [currentUserId, setCurrentUserId] = useState(null);

    useEffect(() => {
        retrieveImages();
        retrieveCurrentUserId();
    }, []);

    const retrieveImages = () => {
        ImageService.getAllImages()
            .then((response) => {
                setImages(response.data);
            })
    }

    const style = {
        display: "block"
    }


    const retrieveCurrentUserId = () => {
        const user = AuthService.getCurrentUser(localStorage.getItem("user"));
        if (user) {
            setCurrentUserId(user.id);
        }

    }

    return (
        <div className="frontPage">
            <ImageList images={images}/>
            {currentUserId &&
            <FileUpload currentUserId={currentUserId} />}
        </div>
    );
}

export default FrontPage;
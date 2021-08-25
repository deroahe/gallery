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

    const retrieveCurrentUserId = () => {
        const user = AuthService.getCurrentUser(localStorage.getItem("user"));
        if (user) {
            setCurrentUserId(user.id);
        }

    }

    const appendNewlyUploadedImage = (imageUrl) => {
        setImages(images => [...images, imageUrl])
    }

    return (
        <div>
            <div className="frontPage">
                Images:
                <button onClick={retrieveImages}>Refresh</button>
                <ImageList images={images}/>
            </div>
            <div>
                <FileUpload
                    appendNewlyUploadedImage={appendNewlyUploadedImage}
                    currentUserId={currentUserId}
                />
            </div>
        </div>
    );
}

export default FrontPage;
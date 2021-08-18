import React, { useState, useEffect } from 'react';
import ImageList from './ImageList';
import FileUpload from "./FileUpload";
import ImageService from "../services/images.service";

const FrontPage = () => {

    const [images, setImages] = useState([]);

    useEffect(() => {
        ImageService.getAllImages()
            .then((response) => {
                response.data
                    .map((image) => {
                        setImages(images => [...images, image.imageUrl]);
                    });
            })
    }, []);

    const refreshFrontPage = () => {
        ImageService.getAllImages()
            .then((response) => {
                setImages([]);
                response.data
                    .map((image) => {
                        setImages(images => [...images, image.imageUrl]);
                    });
            })
    }

    const appendNewlyUploadedImage = (imageUrl) => {
        setImages(images => [...images, imageUrl])
    }

    return (
        <div className="frontPage">
            Images:
            <button onClick={refreshFrontPage}>Refresh</button>
            <ImageList urls={images}/>
            <FileUpload appendNewlyUploadedImage={appendNewlyUploadedImage}/>
        </div>
    );
}

export default FrontPage;
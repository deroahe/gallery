import React, {useEffect, useState} from 'react';

import ImageService from '../services/image.service';
import {Link} from "react-router-dom";

const ImageList = props => {

    const [images, setImages] = useState([]);

    useEffect(() => {
        retrieveImages();
    }, []);

    const retrieveImages = () => {
        ImageService.getAllImages()
            .then(response => {
                setImages(response.data);
            })
            .catch(e => {
                console.log(e);
            })
    }

    return (
        images.map((image, key) =>
            <Link key={key} to={"/images/" + image.imageId}>
                <img key={key} src={image.imageUrl} className="profile-img-card" alt={image.imageUrl}/>
            </Link>
        )
    )
}

export default ImageList;
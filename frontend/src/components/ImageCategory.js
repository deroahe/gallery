import React, {useEffect, useState} from 'react';

import ImageService from '../services/image.service';
import {Link} from "react-router-dom";

const ImageCategory = (props) => {
    const [images, setImages] = useState([]);

    useEffect(() => {
        retrieveImagesByCategory(props.match.params.category);

    }, [props.match.params.category]);

    const retrieveImagesByCategory = (category) => {
        if (category)
            ImageService.getAllImagesByCategory(category)
                .then((r) => { setImages(r.data) })
                .catch((e) => console.log(e));
    }
    return (
        <>
            <div className="categoryNameDiv"><h3>{props.match.params.category}</h3></div>
            {images.map((image, key) =>
                <Link key={key} to={"/images/" + image.imageId}>
                    <img key={key} src={image.imageUrl} className="profile-img-card" alt={image.imageUrl}/>
                </Link>
            )}
        </>
    )
}
export default ImageCategory;
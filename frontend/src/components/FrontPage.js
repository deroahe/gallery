import React, { useState, useEffect } from 'react';
import ImageList from './ImageList';
import http from '../http-common';
import FileUpload from "./FileUpload";

const FrontPage = (props) => {

    const [images, setImages] = useState([]);

    useEffect(() => {
        http.get("api/images/urls")
            .then((response) => {
                console.log("Response from get all urls", response);
                setImages(response.data);
            })
    }, []);

    const refreshFrontPage = () => {
        http.get("api/images/urls")
            .then((response) => {
                console.log("Response from get all urls in Refresh", response);
                setImages(response.data);
            }).catch((reason) => {
                console.log("In catch reason: ", reason)
        })
    }

    return (
        <div className="frontPage">
            Images:
            <button onClick={refreshFrontPage}>Refresh</button>
            <ImageList urls={images}/>
            <FileUpload />
        </div>
    );
}

export default FrontPage;
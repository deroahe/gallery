import React from 'react';
import Image from "./Image";

const ImageList = props => {
    return (
        <div className="imageListDiv">
            {
                props.urls.map(url =>
                    <Image key={url} url={url}/>
                )
            }
        </div>
    );
}

export default ImageList;
import React from 'react';

import '../App.css';

const Image = props => {

    return (
        <div className="imageDiv">
            <img src={props.url} alt={props.url} />
            <p className="hashtag">#hashtags</p>
        </div>
    );
}

export default Image;
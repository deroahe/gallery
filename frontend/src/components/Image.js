import React, {useEffect, useState} from 'react';

import AuthService from '../services/auth.service';
import ImageService from '../services/image.service';
import CommentService from '../services/comment.service';
import CommentAdd from "./CommentAdd";
import Button from "react-bootstrap/Button";

const Image = (props) => {
    const initialImageState = {
        imageUrl: "",
        imageHashtags: [],
        imageComments: []
    }

    const [currentImage, setCurrentImage] = useState(initialImageState);
    const [currentUserId, setCurrentUserId] = useState(null);
    const [imageComments, setImageComments] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        retrieveCurrentUserId();
        retrieveImage(props.match.params.id);
        findCommentsByImage(props.match.params.id);
    }, [props.match.params.id]);

    const retrieveImage = (imageId) => {
        ImageService.getImageById(imageId)
            .then((response) => {
                setCurrentImage(response.data);
            })
            .catch((err) => {
                console.log(err);
            })
    }

    const retrieveCurrentUserId = () => {
        const user = AuthService.getCurrentUser(localStorage.getItem("user"));
        if (user) {
            setCurrentUserId(user.id);
        }
    }

    const findCommentsByImage = (imageId) => {
        CommentService.findCommentsByImage(imageId)
            .then((r) => {
                setImageComments(r.data);
            })
            .catch((e) => console.log(e));
    }

    // const deleteImage = () => {
    //     ImageService.deleteImage(currentImage.imageId)
    //         .then(
    //             props.history.push("/images")
    //         )
    //         .catch((e) => console.log(e))
    // }

    return (
        <div>
            <img src={currentImage.imageUrl} alt={currentImage.imageUrl}
                 onLoad={ () => setLoading(false) }
            />
            {/*<Button*/}
            {/*    type="submit"*/}
            {/*    className="admin-operation-button"*/}
            {/*    onClick={deleteImage}*/}
            {/*>*/}
            {/*    Delete*/}
            {/*</Button>*/}
            {currentUserId && currentImage.imageId ? (
                <CommentAdd userId={currentUserId} imageId={currentImage.imageId}/>
            ) : ''}

            <h2>
                HASHTAGS:
            </h2>
            {
                currentImage.imageHashtags.map((hashtag, index) => (
                        <h4 key={index}>#{hashtag.hashtagName}</h4>
                    )
                )
            }
            <h2>
                COMMENTS:
            </h2>
            {
                imageComments.map((comment, index) =>
                    <h6 key={index}>{comment.commentString} by <strong key={index}>{comment.commentUser.userUsername}</strong></h6>
                )
            }
        </div>
    );
}

export default Image;
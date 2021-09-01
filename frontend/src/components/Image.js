import React, {useEffect, useState} from 'react';

import AuthService from '../services/auth.service';
import ImageService from '../services/image.service';
import CommentService from '../services/comment.service';
import CommentAdd from "./CommentAdd";
import Button from "react-bootstrap/Button";

const Image = (props) => {
    const initialImageState = {
        imageUrl: "",
        imageHashtags: undefined,
        imageComments: []
    }

    const [currentImage, setCurrentImage] = useState(initialImageState);
    const [currentUserId, setCurrentUserId] = useState(null);
    const [imageComments, setImageComments] = useState(undefined);
    const [fullSize, setFullSize] = useState(false);
    const [loading, setLoading] = useState(true);
    const [renderDelete, setRenderDelete] = useState(false);

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
            if (user.roles.includes("ROLE_ADMIN")) {
                setRenderDelete(true);
            }
        }
    }

    const findCommentsByImage = (imageId) => {
        CommentService.findCommentsByImage(imageId)
            .then((r) => setImageComments(r.data))
            .catch((e) => console.log(e));
    }

    const deleteImage = () => {
        ImageService.deleteImage(currentImage.imageId)
            .then(
                props.history.push("/images")
            )
            .catch((e) => console.log(e))
    }

    const imageFullSize = (e) => {
        e.preventDefault();
        if (fullSize) {
            e.target.style.width = '600px'
            e.target.style.height = 'auto'
        } else {
            e.target.style.width = '100%'
            e.target.style.height = 'auto'
        }
        setFullSize(!fullSize);
    }

    return (
        <div className="imageComponentDiv">
            <div>
                <img src={currentImage.imageUrl} alt={currentImage.imageUrl}
                     onLoad={ () => setLoading(false) }
                     onClick={imageFullSize}
                />

                {renderDelete &&
                <div>
                    <Button
                        type="submit"
                        className="admin-operation-button"
                        onClick={deleteImage}
                    >
                        Delete image
                    </Button>
                </div>}
                {currentUserId && currentImage.imageId ? (
                    <CommentAdd userId={currentUserId} imageId={currentImage.imageId}/>
                ) : null
                }
                {
                    currentImage.imageHashtags !== undefined && currentImage.imageHashtags.size > 0 ? (
                            <label>Hashtags</label>
                    )   : null
                }
                {
                    currentImage.imageHashtags && currentImage.imageHashtags.map((hashtag, index) => (
                            <h4 key={index}>#{hashtag.hashtagName}</h4>
                        )
                    )
                }
                {
                    imageComments && imageComments.map((comment, index) =>
                        <div key={index}>
                            <span key={index}>{comment.commentString} </span><span className="commentUser"> &nbsp; by <strong key={index}>{comment.commentUser.userUsername}</strong></span>
                        </div>
                    )
                }
            </div>
        </div>
    );
}

export default Image;
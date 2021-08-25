import React, {useEffect, useRef, useState} from 'react';

import AuthService from '../services/auth.service';
import ImageService from '../services/image.service';
import CommentService from '../services/comment.service';
import CommentAdd from "./CommentAdd";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

const Image = (props) => {
    const form = useRef();
    const checkBtn = useRef();

    const initialImageState = {
        imageUrl: "",
        imageHashtags: [],
        imageComments: []
    }

    const [currentImage, setCurrentImage] = useState(initialImageState);
    const [currentUserId, setCurrentUserId] = useState(undefined);
    const [commentText, setCommentText] = useState("");
    const [message, setMessage] = useState("");
    const [imageComments, setImageComments] = useState([]);

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

    const postComment = () => {
    }

    const handleSubmit = (evt) => {
        evt.preventDefault();
        if (checkBtn.current.context._errors.length === 0) {
            CommentService.postComment(commentText, currentUserId, currentImage.imageId)
                .then((r) => {
                    setMessage(r.data.message)
                })
                .catch((e) => {
                    setMessage("Error");
                    console.log(e);
                });
        }
    }

    const onChangeComment = (e) => {
        const commentText = e.target.value;
        setCommentText(commentText);
    }

    return (
        <div>
            <img src={currentImage.imageUrl} alt={currentImage.imageUrl}/>
            {
                <Form onSubmit={handleSubmit} ref={form}>
                    <label htmlFor="commentText">
                        Post a comment
                    </label>
                        <Input
                            type="text"
                            className="form-control"
                            name="commentText"
                            value={commentText}
                            onChange={onChangeComment}
                        />
                    <CheckButton style={{ display: "none" }} ref={checkBtn} />
                </Form>
            }
            {message}
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
import React, {useState} from 'react';
import Button from 'react-bootstrap/Button';
import CommentService from '../services/comment.service';
import {Link} from "react-router-dom";

const CommentAdd = (props) => {
    const [commentString, setCommentString] = useState('');
    const [imageId, setImageId] = useState(props.imageId);
    const [userId, setUserId] = useState(props.userId);

    const handleInputChange = event => {
        setCommentString(event.target.value);
    };

    const postComment = () => {
        CommentService.postComment(commentString, userId, imageId)
            .then((r) => {
                console.log(r);
                window.location.reload();
            })
            .catch((e) => {
                console.log(e);
            });
    }

    return (
        <div>
            {props.userId && props.imageId ? (

                <div className="edit-form">
                    <form>
                        <div className="form-group">
                            <label htmlFor="commentInput">Comment</label>
                            <input
                                type="text"
                                className="form-control"
                                id="commentInput"
                                name="commentInput"
                                defaultValue={commentString}
                                onChange={handleInputChange}
                            />
                        </div>
                    </form>

                    <Button
                        type="submit"
                        className="admin-operation-button"
                        onClick={postComment}
                    >
                        Post
                    </Button>
                </div>
            ) : (
                <div>
                    <br />
                    <p><Link to="/login" >Authenticate</Link> to post a comment</p>
                </div>
            )}
        </div>
    )
}

export default CommentAdd;
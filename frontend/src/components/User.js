import React, { useState, useEffect } from "react";
import UserDataService from "../services/user.service";

import Button from 'react-bootstrap/Button';

const User = props => {
    const initialUserState = {
        userId: undefined,
        userUsername: "",
        userEmail: ""
    };
    const [currentUser, setCurrentUser] = useState(initialUserState);
    const [message, setMessage] = useState("");

    const getUser = id => {
        UserDataService.getUser(id)
            .then(response => {
                setCurrentUser({
                    userId: response.data.userId,
                    userUsername: response.data.userUsername,
                    userEmail: response.data.userEmail
                });
            })
            .catch(e => {
                console.log(e);
            });
    };

    useEffect(() => {
        getUser(props.match.params.id);
    }, [props.match.params.id]);

    const handleInputChange = event => {
        const { name, value } = event.target;
        setCurrentUser({ ...currentUser, [name]: value });
    };

    const updateUser = () => {
        UserDataService.updateUser(currentUser)
            .then(response => {
                setMessage(response.data.message);
                props.history.push("/admin");
            })
            .catch(e => {
                console.log(e);
            });
    };

    const deleteUser = () => {
        UserDataService.deleteUser(currentUser)
            .then(response => {
                setMessage(response.data.message);
                props.history.push("/admin");
            })
            .catch(e => {
                console.log(e);
            })
    }

    return (
        <div>
            {currentUser ? (

                <div className="edit-form">
                    <h4>User</h4>
                    <form>
                        <div className="form-group">
                            <label htmlFor="userUsername">Username</label>
                            <input
                                type="text"
                                className="form-control"
                                id="userUsername"
                                name="userUsername"
                                defaultValue={currentUser.userUsername}
                                onChange={handleInputChange}
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="userEmail">Email</label>
                            <input
                                type="text"
                                className="form-control"
                                id="userEmail"
                                name="userEmail"
                                defaultValue={currentUser.userEmail}
                                onChange={handleInputChange}
                            />
                        </div>

                    </form>

                    <Button
                        type="submit"
                        className="admin-operation-button"
                        onClick={updateUser}
                    >
                        Update
                    </Button>
                    <Button
                        type="submit"
                        className="admin-operation-button"
                        onClick={deleteUser}
                    >
                        Delete
                    </Button>
                    <p>{message}</p>
                </div>
            ) : (
                <div>
                    <br />
                    <p>Please click on a User...</p>
                </div>
            )}
        </div>
    );
};

export default User;
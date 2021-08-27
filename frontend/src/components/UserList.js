import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Button from 'react-bootstrap/Button';

import UserService from "../services/user.service";

const UsersList = () => {
    const [users, setUsers] = useState([]);
    const [currentUser, setCurrentUser] = useState(null);
    const [currentIndex, setCurrentIndex] = useState(-1);
    const [images, setImages] = useState([]);

    useEffect(() => {
        retrieveUsers();
    }, []);

    const retrieveUsers = () => {
        UserService.getAllUsers()
            .then((response) => setUsers(response.data))
            .catch((e) => console.log(e));
    };

    const setActiveUser = (user, index) => {
        setCurrentUser(user);
        setCurrentIndex(index);
    };

    return (
        <div className="list row">
            <div className="col-md-6">
                <h4>Users List</h4>

                <ul className="list-group">
                    {users &&
                    users.map((user, index) => (
                        <li
                            className={
                                "list-group-item " + (index === currentIndex ? "active" : "")
                            }
                            onClick={() => setActiveUser(user, index)}
                            key={index}
                        >
                            {user.userUsername}
                        </li>
                    ))}
                </ul>
            </div>
            <div className="col-md-6">
                {currentUser ? (
                    <div>
                        <h4>User</h4>
                        <div>
                            <label>
                                <strong>Name:</strong>
                            </label>{" "}
                            {currentUser.userEmail}
                        </div>
                        <div>
                            <label>
                                <strong>Id:</strong>
                            </label>{" "}
                            {currentUser.userId}
                        </div>
                        <br/>
                        <Link
                            to={"/users/" + currentUser.userId}
                        ><Button>Edit</Button>
                        </Link>
                    </div>
                ) : (
                    <div>
                        <br />
                        <p>Please click on an User...</p>
                    </div>
                )}
            </div>
        </div>
    );
};

export default UsersList;
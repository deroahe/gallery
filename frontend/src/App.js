import React, { useState, useEffect } from "react";
import { Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth.service";

import Login from "./components/Login";
import Register from "./components/Register";
import Home from "./components/Home";
import Profile from "./components/Profile";
import BoardUser from "./components/BoardUser";
import BoardModerator from "./components/BoardModerator";
import BoardAdmin from "./components/BoardAdmin";
import Image from "./components/Image";

// import AuthVerify from "./common/AuthVerify";
import EventBus from "./common/EventBus";
import FrontPage from "./components/FrontPage";
import User from "./components/User";
import ImageCategory from "./components/ImageCategory";

const App = () => {
    const [showModeratorBoard, setShowModeratorBoard] = useState(false);
    const [showAdminBoard, setShowAdminBoard] = useState(false);
    const [currentUser, setCurrentUser] = useState(undefined);

    useEffect(() => {
        const user = AuthService.getCurrentUser();

        if (user) {
            setCurrentUser(user);
            setShowModeratorBoard(user.roles.includes("ROLE_MODERATOR"));
            setShowAdminBoard(user.roles.includes("ROLE_ADMIN"));
        }

        EventBus.on("logout", () => {
            logOut();
        });

        return () => {
            EventBus.remove("logout");
        };
    }, []);

    const logOut = () => {
        AuthService.logout();
        setShowModeratorBoard(false);
        setShowAdminBoard(false);
        setCurrentUser(undefined);
    };

    return (
        <div>
            <nav className="navbar navbar-expand navbar-dark bg-dark">
                <Link to={"/images"} className="navbar-brand">
                    Gallery
                </Link>
                <div className="navbar-nav mr-auto">
                    <li className="nav-item">
                        <Link to={"/images"} className="nav-link">
                            Front Page
                        </Link>
                    </li>

                    {showAdminBoard && (
                        <li className="nav-item">
                            <Link to={"/admin"} className="nav-link">
                                Admin Board
                            </Link>
                        </li>
                    )}

                    {/*{currentUser && (*/}
                    {/*    <li className="nav-item">*/}
                    {/*        <Link to={"/user"} className="nav-link">*/}
                    {/*            User*/}
                    {/*        </Link>*/}
                    {/*    </li>*/}
                    {/*)}*/}
                </div>

                {currentUser ? (
                    <div className="navbar-nav ml-auto">
                        <li className="nav-item">
                            <Link to={"/images-by-category/Space"} className="nav-link">
                                Space
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link to={"/images-by-category/Animals"} className="nav-link">
                                Animals
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link to={"/images-by-category/Nature"} className="nav-link">
                                Nature
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link to={"/profile"} className="nav-link">
                                {currentUser.username}
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link to={"/user-board"} className="nav-link">
                                My uploads
                            </Link>
                        </li>
                        <li className="nav-item">
                            <a href="/login" className="nav-link" onClick={logOut}>
                                LogOut
                            </a>
                        </li>
                    </div>
                ) : (
                    <div className="navbar-nav ml-auto">
                        <li className="nav-item">
                            <Link to={"/login"} className="nav-link">
                                Login
                            </Link>
                        </li>

                        <li className="nav-item">
                            <Link to={"/register"} className="nav-link">
                                Sign Up
                            </Link>
                        </li>
                    </div>
                )}
            </nav>

            <div className="container mt-3">
                <Switch>
                    <Route exact path={["/", "/home"]} component={FrontPage} />
                    <Route exact path="/login" component={Login} />
                    <Route exact path="/register" component={Register} />
                    <Route exact path="/profile" component={Profile} />
                    <Route path="/admin" component={BoardAdmin} />
                    <Route path="/images/:id" component={Image}/>}/>
                    <Route path="/images" component={FrontPage} />
                    <Route path="/users/:id" component={User}/>
                    <Route path="/user-board" component={BoardUser}/>
                    <Route path="/images-by-category/:category" component={ImageCategory}/>
                </Switch>
            </div>

            {/* <AuthVerify logOut={logOut}/> */}
        </div>
    );
};

export default App;
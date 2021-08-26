import React, {useEffect, useState} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import UserService from '../services/user.service'

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    },
});

function createData(username, email) {
    return {username, email};
}

const BasicTable = () => {
    const classes = useStyles();

    const userToEditInitialState = {
        username: "",
        email: ""
    }

    const [rows, setRows] = useState([]);
    const [userToEdit, setUserToEdit] = useState(userToEditInitialState);

    useEffect(() => {
        UserService.getAllUsers()
            .then((response) => {
                setRows(response.data);
            }).catch((error) => {
            console.log(error);
        });
    }, []);

    const removeUser = (id) => {
        UserService.removeUser(id)
            .then((response) => {
                console.log("Response: ", response);
            })
    };

    const handleInputChange = (event) => {
        const {name, value} = event.target;
        setUserToEdit({...userToEdit, [name]: value})
    };


    return (
        <TableContainer component={Paper}>
            <Table className={classes.table} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Dessert (100g serving)</TableCell>
                        <TableCell align="right">Username</TableCell>
                        <TableCell align="right">Email</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map((row, key) => (
                        <TableRow key={key}>
                            <TableCell component="th" scope="row">
                                <button onClick={() => {
                                    UserService.updateUser(row.id, {
                                            id: row.id,
                                            username: row.username,
                                            email: row.email
                                        }
                                    ).then((response) => {
                                        console.log("Response: ", response)
                                    }).catch((error) => {
                                        console.log(error);
                                    })
                                }}>Update
                                </button>
                                <button onClick={() => {
                                    UserService.removeUser(row.id).then((response) => {
                                        console.log("Response: ", response);
                                    }).catch((error) => {
                                        console.log(error);
                                    })
                                }}>Delete
                                </button>
                            </TableCell>
                            <TableCell align="right">
                                <input type="text"
                                       id={"username"}
                                       name={"username"}
                                       onChange={handleInputChange}
                                       value={row.username}
                                />
                            </TableCell>
                            <TableCell align="right">
                                <input type="text"
                                       id={"email"}
                                       name={"email"}
                                       onChange={handleInputChange}
                                       value={row.email}
                                />
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
export default BasicTable;
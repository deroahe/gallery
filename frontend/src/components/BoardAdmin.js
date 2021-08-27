import React, { useState, useEffect } from "react";
import EventBus from "../common/EventBus";
import Button from 'react-bootstrap/Button';

import UsersList from "./UserList";
import UserService from "../services/user.service";
import ExportService from '../services/export.service';

const BoardAdmin = () => {
    const [content, setContent] = useState("");

    useEffect(() => {
        UserService.getAdminBoard().then(
            (response) => {
                setContent(response.data);
            },
            (error) => {
                const _content =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                setContent(_content);

                if (error.response && error.response.status === 401) {
                    EventBus.dispatch("logout");
                }
            }
        );
    }, []);

    const exportExcel = () => {
        ExportService.exportExcelFetch()
            .then((r) => {
                return r.arrayBuffer()
            })
            .then((r) => {
                const byteArray = new Uint8Array(r);
                const a = window.document.createElement('a');
                a.href = window.URL.createObjectURL(
                    new Blob([byteArray], {
                        type:
                            'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
                    }),
                );
                a.download = "galleryReport.xlsx";
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
            })
    }

    return (
        <div className="container">
            <UsersList/>
            <div>
                <h4>Export excel document</h4>
                <Button onClick={exportExcel}>Export</Button>
            </div>
        </div>
    );
};

export default BoardAdmin;
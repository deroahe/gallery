import React,{Component} from 'react';
import http from '../http-common';

class App extends Component {

    state = {
        refresh: this.props.refreshFrontPage,
        // Initially, no file is selected
        selectedFile: null
    };

    // On file select (from the pop up)
    onFileChange = event => {

        // Update the state
        this.setState({ selectedFile: event.target.files[0] });

    };

    // On file upload (click the upload button)
    onFileUpload = () => {

        // Create an object of formData
        const formData = new FormData();

        // Update the formData object
        formData.append(
            "file",
            this.state.selectedFile,
            this.state.selectedFile.name
        );

        formData.append(
            "user",
            new Blob([JSON.stringify(
                {
                    id: 1
                }
            )], {
                type: "application/json"
            })
        )

        // Details of the uploaded file
        console.log(this.state.selectedFile);

        // Request made to the backend api
        // Send formData object
        http.post("http://localhost:8080/api/images", formData)
            .then((response) => {
                console.log("In post file upload, response: ", response);
                this.state.refresh()
            }).catch((reason => {
                console.log("In catch file upload reason: ", reason)
        }));
    };

    // File content to be displayed after
    // file upload is complete
    fileData = () => {

        if (this.state.selectedFile) {

            return (
                <div>
                    <h2>File Details:</h2>

                    <p>File Name: {this.state.selectedFile.name}</p>


                    <p>File Type: {this.state.selectedFile.type}</p>


                    <p>
                        Last Modified:{" "}
                        {this.state.selectedFile.lastModifiedDate.toDateString()}
                    </p>

                </div>
            );
        } else {
            return (
                <div>
                    <br />
                    <h4>Choose before Pressing the Upload button</h4>
                </div>
            );
        }
    };

    render() {

        return (
            <div>
                <h1>
                    GeeksforGeeks
                </h1>
                <h3>
                    File Upload using React!
                </h3>
                <div>
                    <input  name="file" type="file" onChange={this.onFileChange} />
                    <button onClick={this.onFileUpload} hidden={!this.state.selectedFile}>
                        Upload!
                    </button>
                </div>
                {this.fileData()}
            </div>
        );
    }
}

export default App;
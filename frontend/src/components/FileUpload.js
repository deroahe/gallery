import React,{Component} from 'react';
import ImageService from '../services/image.service';

class App extends Component {

    state = {
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

        // formData.append(
        //     "userId",
        //     new Blob([JSON.stringify(
        //         {
        //             userId: this.props.userId
        //         }
        //     )], {
        //         type: "application/json"
        //     })
        // )

        formData.append( "userId",
            new Blob([this.props.currentUserId], { type: "application/json"})
        )

        // Details of the uploaded file
        console.log(this.state.selectedFile);

        ImageService.uploadImage(formData)
            .then((response) => {
                this.props.appendNewlyUploadedImage(response.data.imageUrl);
            })
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
import React,{Component} from 'react';
import ImageService from '../services/image.service';

class App extends Component {

    state = {
        // Initially, no file is selected
        selectedFile: null,
        hashtagsNames: ''
    };

    // On file select (from the pop up)
    onFileChange = event => {

        // Update the state
        this.setState({ selectedFile: event.target.files[0] });

    };

    handleInputChange = event => {
        this.setState({ ...this.state, hashtagsNames: event.target.value });
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

        formData.append( "userId",
            new Blob([this.props.currentUserId], { type: "application/json"})
        )

        // Details of the uploaded file
        console.log(this.state.selectedFile);

        ImageService.uploadImage(formData)
            .then((response) => {
                if (this.state.hashtagsNames.length > 2) {
                    ImageService.saveHashtagsToImage(this.state.hashtagsNames, response.data.imageId)
                        .then((r) => window.location.reload())
                        .catch((e) => console.log(e))
                }
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
            return null;
        }
    };

    render() {


        return (
            <>
                <div className="uploadAndSetHashtagsDiv">
                    <div>
                        <label>Upload an image</label>
                        <input name="file" type="file" onChange={this.onFileChange} />
                    </div>
                    <div>
                        <label>Tag your image</label>
                        <input type="text"
                               id="hashtagsNamesInput"
                               name="hashtagsNamesInput"
                               placeholder="just,like,this"
                               defaultValue={this.state.hashtagsNames}
                               onChange={this.handleInputChange}
                        />
                        <button onClick={this.onFileUpload} hidden={!this.state.selectedFile}>
                            Upload!
                        </button>
                    </div>
                </div>
                {this.fileData()}
            </>
        );
    }
}

export default App;
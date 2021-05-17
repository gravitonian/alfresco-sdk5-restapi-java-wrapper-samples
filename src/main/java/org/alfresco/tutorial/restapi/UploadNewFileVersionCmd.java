
/*
 * Copyright 2021-2021 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.NodesApi;
import org.alfresco.core.model.Node;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class UploadNewFileVersionCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(UploadNewFileVersionCmd.class);

    private List<String> include = null;
    private List<String> fields = null;

    // If true, then created node will be version '1.0 MAJOR'. If false, then created node will be version '0.1 MINOR'.
    private Boolean majorVersion = true;
    // Add a version comment which will appear in version history. Setting this parameter also enables versioning of
    // this node, if it is not already versioned.
    private String updateComment = null;
    // Optional new name. This should include the file extension. The name must not contain spaces or the following
    // special characters: * " < > \ / ? : and |. The character `.` must not be used at the end of the name.
    private String updatedName = null;

    @Autowired
    NodesApi nodesApi;

    public void execute(String textFileNodeId, String binFileNodeId) throws IOException {
        // Update text content for a file
        Node newTextFile = updateTextFileContent(textFileNodeId,"Some UPDATED text for the file");

        // Upload a file as new content
        Node newFile = uploadNewFileVersion(binFileNodeId, "updatedpicture.png");
    }

    /**
     * Upload a file from disk as a new version
     */
    private Node uploadNewFileVersion(String fileNodeId, String filePath) {
        // Get the file bytes
        File someFile = new File(filePath);
        byte[] fileData = null;
        try {
            fileData = FileUtils.readFileToByteArray(someFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Update the file node content
        Node updatedFileNode = nodesApi.updateNodeContent(fileNodeId,
                fileData, majorVersion, updateComment, updatedName, include, fields).getBody().getEntry();

        LOGGER.info("Uploaded new content for file: {}", updatedFileNode.toString());

        return updatedFileNode;
    }

    /**
     * Update text content for a file
     */
    private Node updateTextFileContent(String fileNodeId, String textContent) {
        // Update the file node content
        Node updatedFileNode = nodesApi.updateNodeContent(fileNodeId,
                textContent.getBytes(), majorVersion, updateComment, updatedName, include, fields).getBody().getEntry();

        LOGGER.info("Updated text content for file: {}", updatedFileNode.toString());

        return updatedFileNode;
    }
}


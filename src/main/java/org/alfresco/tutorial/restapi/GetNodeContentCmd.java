
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
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;

@Component
public class GetNodeContentCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(GetNodeContentCmd.class);

    @Autowired
    NodesApi nodesApi;

    public void execute(String fileNodeId, String filePathOnDisk) throws IOException {
        Resource nodeContent = getNodeContent(fileNodeId);

        // Write file to disk
        File targetFile = new File(filePathOnDisk);
        FileUtils.copyInputStreamToFile(nodeContent.getInputStream(), targetFile);
    }

    /**
     * Get a file node content bytes (folders does not have content).
     *
     * @param nodeId   the id of the file node that we want to fetch content for.
     * @return Node content info object
     */
    private Resource getNodeContent(String nodeId) throws IOException {
        // Relevant when using API call from web browser, true is the default
        Boolean attachment = true;
        // Only download if modified since this time, optional
        OffsetDateTime ifModifiedSince = null;
        // The Range header indicates the part of a document that the server should return.
        // Single part request supported, for example: bytes=1-10., optional
        String range = null;

        Resource result = nodesApi.getNodeContent(nodeId, attachment, ifModifiedSince, range).getBody();
        LOGGER.info("Got node {} size: {}", result.getFilename(), result.contentLength());

        return result;
    }
}

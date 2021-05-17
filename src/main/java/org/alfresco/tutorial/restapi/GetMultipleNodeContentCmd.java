
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

import org.alfresco.core.handler.DownloadsApi;
import org.alfresco.core.handler.NodesApi;
import org.alfresco.core.model.Download;
import org.alfresco.core.model.DownloadBodyCreate;
import org.alfresco.core.model.DownloadEntry;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

@Component
public class GetMultipleNodeContentCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(GetMultipleNodeContentCmd.class);

    @Autowired
    DownloadsApi downloadsApi;

    @Autowired
    NodesApi nodesApi;

    public void execute(String[] fileNodeIds, String zipFilePathOnDisk) throws IOException, InterruptedException {
        DownloadEntry downloadEntry = createZipDownload(fileNodeIds);
        Resource zipNodeContent = getNodeContent(downloadEntry.getEntry().getId());

        // Write ZIP file to disk
        File targetFile = new File(zipFilePathOnDisk);
        FileUtils.copyInputStreamToFile(zipNodeContent.getInputStream(), targetFile);
    }

    /**
     * Create a ZIP download with multiple file nodes. This method waits until download is ready.
     *
     * @param nodeIds   the node ids for the files we want to download in one ZIP
     * @return download entry info object for the ZIP
     */
    private DownloadEntry createZipDownload(String[] nodeIds) throws InterruptedException {
        List<String> fields = null;

        // Set up POST data with node IDs we want to download
        DownloadBodyCreate downloads = new DownloadBodyCreate();
        for (String nodeId : nodeIds) {
            downloads.addNodeIdsItem(nodeId);
        }

        // First create the download on the server side
        DownloadEntry result = downloadsApi.createDownload(downloads, fields).getBody();

        LOGGER.info("Created ZIP download: {}", result.getEntry().toString());

        // Check the download status
        DownloadEntry download = downloadsApi.getDownload(result.getEntry().getId(), fields).getBody();
        while (!download.getEntry().getStatus().equals(Download.StatusEnum.DONE)) {
            LOGGER.info("Checking ZIP download status: {}", download.getEntry().getStatus());
            Thread.sleep(1000); // do nothing for 1000 milliseconds (1 second)
            download = downloadsApi.getDownload(result.getEntry().getId(), fields).getBody();
        }

        LOGGER.info("ZIP download is READY: {}", result.getEntry().getId());

        return download;
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

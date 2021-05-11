package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.VersionsApi;
import org.alfresco.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ListVersionHistoryCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(ListVersionHistoryCmd.class);

    @Autowired
    VersionsApi versionsApi;

    public void execute(String fileNodeId) throws IOException {
        VersionPagingList nodes = listVersionHistory(fileNodeId);
    }
    
    /**
     * List the version history for a file node.
     *
     * @param fileNodeId the id of the file node
     * @return a list of child node objects contained in the folder, or null if not found
     */
    private VersionPagingList listVersionHistory(String fileNodeId) {
        Integer skipCount = 0;
        Integer maxItems = 100;
        List<String> include = null;
        List<String> fields = null;

        LOGGER.info("Listing versions for file node ID {}", fileNodeId);
        VersionPagingList result = versionsApi.listVersionHistory(fileNodeId, include, fields, skipCount, maxItems).getBody().getList();
        for (VersionEntry versionEntry: result.getEntries()) {
            LOGGER.info("Node version " + versionEntry.getEntry().toString());
        }

        return result;
    }
}

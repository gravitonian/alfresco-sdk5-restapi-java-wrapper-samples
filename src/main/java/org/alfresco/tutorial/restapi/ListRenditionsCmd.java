package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.RenditionsApi;
import org.alfresco.core.model.RenditionEntry;
import org.alfresco.core.model.RenditionPagingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ListRenditionsCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(ListRenditionsCmd.class);

    @Autowired
    RenditionsApi renditionsApi;

    public void execute(String fileNodeId) throws IOException {
        RenditionPagingList nodeRenditions = listRenditions(fileNodeId);
    }
    
    /**
     * List renditions for a file node.
     *
     * @param fileNodeId the id of the file node
     * @return a list of renditions, or null if not found
     */
    private RenditionPagingList listRenditions(String fileNodeId) {
        String where = null; // filter renditions

        LOGGER.info("Listing versions for file node ID {}", fileNodeId);
        RenditionPagingList result = renditionsApi.listRenditions(fileNodeId, where).getBody().getList();
        for (RenditionEntry renditionEntry: result.getEntries()) {
            LOGGER.info("Node rendition: " + renditionEntry.getEntry().toString());
        }

        return result;
    }
}

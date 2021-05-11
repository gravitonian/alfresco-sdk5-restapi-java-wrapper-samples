package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.TrashcanApi;
import org.alfresco.core.model.DeletedNodeEntry;
import org.alfresco.core.model.DeletedNodesPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ListDeletedNodesCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(ListDeletedNodesCmd.class);

    @Autowired
    TrashcanApi trashcanApi;

    public void execute() throws IOException {
        Integer skipCount = 0;
        Integer maxItems = 100;
        List<String> include = new ArrayList<>();
        include.add("path");

        LOGGER.info("Listing soft deleted nodes in the trashcan:");
        DeletedNodesPaging deletedNodes = trashcanApi.listDeletedNodes(skipCount, maxItems, include).getBody();
        for (DeletedNodeEntry deletedNodeEntry: deletedNodes.getList().getEntries()) {
            LOGGER.info("    Deleted node: {}", deletedNodeEntry.getEntry());
        }
    }
}

package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.TrashcanApi;
import org.alfresco.core.model.DeletedNodeBodyRestore;
import org.alfresco.core.model.NodeEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RestoreDeletedNodeCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(RestoreDeletedNodeCmd.class);

    @Autowired
    TrashcanApi trashcanApi;

    public void execute(String nodeId, String restoreFolderId) throws IOException {
        List<String> fields = null;

        // POST body need to ne supplied with target folder ID
        DeletedNodeBodyRestore deletedNodeBodyRestore = new DeletedNodeBodyRestore();
        deletedNodeBodyRestore.setTargetParentId(restoreFolderId);
        deletedNodeBodyRestore.setAssocType("cm:contains");
        NodeEntry restoredNode = trashcanApi.restoreDeletedNode(nodeId, fields, deletedNodeBodyRestore).getBody();
        LOGGER.info("Restored node: {}", restoredNode.getEntry());
    }
}

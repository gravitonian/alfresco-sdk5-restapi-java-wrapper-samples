package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.NodesApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DeleteNodeCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(DeleteNodeCmd.class);

    @Autowired
    NodesApi nodesApi;

    public void execute(String nodeId) throws IOException {
        // If true, then the node is deleted permanently, without moving to the trashcan.
        // Only the owner of the node or an admin can permanently delete the node.
        // default value = false
        Boolean permanent = false;

        ResponseEntity<Void> deletedNodeResponse = nodesApi.deleteNode(nodeId, permanent);
        LOGGER.info("Deleted node response: {}", deletedNodeResponse);
    }
}

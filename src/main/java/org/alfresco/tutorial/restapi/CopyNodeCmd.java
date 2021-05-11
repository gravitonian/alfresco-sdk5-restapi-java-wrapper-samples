package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.NodesApi;
import org.alfresco.core.model.Node;
import org.alfresco.core.model.NodeBodyCopy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CopyNodeCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(CopyNodeCmd.class);

    @Autowired
    NodesApi nodesApi;

    public void execute(String nodeId, String parentFolderNodeId) throws IOException {
        List<String> include = new ArrayList<>();
        List<String> fields = null;

        include.add("path"); // add extra path property in response so we can see location of node

        Node node = nodesApi.getNode(nodeId, include, null, fields).getBody().getEntry();
        LOGGER.info("Got node we want to copy ID: {} Parent: {} Location: {}",
                node.getId(), node.getParentId(), node.getPath().getName());

        NodeBodyCopy nodeBodyCopy = new NodeBodyCopy();
        nodeBodyCopy.setTargetParentId(parentFolderNodeId);
        Node copiedNode = nodesApi.copyNode(nodeId, nodeBodyCopy, include, fields).getBody().getEntry();
        LOGGER.info("Copied node ID: {} Parent: {} Location: {}",
                copiedNode.getId(), copiedNode.getParentId(), copiedNode.getPath().getName());
    }
}

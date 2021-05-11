package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.NodesApi;
import org.alfresco.core.model.Node;
import org.alfresco.core.model.NodeBodyCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class LinkFileCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(LinkFileCmd.class);

    @Autowired
    NodesApi nodesApi;

    public void execute(String parentFolderNodeId, String linkToNodeId) throws IOException {
        Map<String, String> linkProps = new HashMap<>();
        linkProps.put("cm:destination", linkToNodeId); // Link points to this file node

        NodeBodyCreate nodeBodyCreate = new NodeBodyCreate();
        nodeBodyCreate.setName("Link to a text file");
        nodeBodyCreate.setNodeType("app:filelink"); // Out-of-the-box content model type for a file link
        nodeBodyCreate.setProperties(linkProps);

        Boolean autoRename = true;
        List<String> include = new ArrayList<>();
        List<String> fields = null;
        Boolean majorVersion = true;
        Boolean versioningEnabled = true;

        // Include the isLink property in the response so we can see if a node is a link
        include.add("isLink");

        Node fileLinkNode = nodesApi.createNode(parentFolderNodeId, nodeBodyCreate, autoRename, majorVersion,
                versioningEnabled, include, fields).getBody().getEntry();
        LOGGER.info("File link: {}", fileLinkNode);
    }
}

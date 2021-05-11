package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.NodesApi;
import org.alfresco.core.handler.SitesApi;
import org.alfresco.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AddSiteContentCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(AddSiteContentCmd.class);

    Integer skipCount = 0;
    Integer maxItems = 100;
    private List<String> fields = null;
    private List<String> include = null;
    private Boolean autoRename = true;
    private Boolean majorVersion = true;
    private Boolean versioningEnabled = true;

    @Autowired
    SitesApi sitesApi;

    @Autowired
    NodesApi nodesApi;

    public void execute(String siteId) throws IOException {
        // First get the Node ID for the Document Library
        String docLibNodeId = null;
        SiteContainerPaging siteContainerPaging = sitesApi.listSiteContainers(siteId, skipCount, maxItems, fields).getBody();
        LOGGER.info("Listing site containers [{}]: ", siteId);
        for (SiteContainerEntry siteContainerEntry: siteContainerPaging.getList().getEntries()) {
            SiteContainer siteContainer = siteContainerEntry.getEntry();
            LOGGER.info("  Site container: {}", siteContainer);
            if (siteContainer.getFolderId().equalsIgnoreCase("DocumentLibrary")) {
                docLibNodeId = siteContainer.getId();
            }
        }

        if (docLibNodeId != null) {
            // Create a folder in the document library
            createFolder(docLibNodeId, "White papers");
        } else {
            LOGGER.info("Document library not found in site {}", siteId);
        }
    }

    /**
     * Make the remote call to create a folder in the repository, if it does not exist.
     *
     * @param parentFolderId the node ID for the site container
     * @param folderName         the name of the folder
     * @return a node object for the newly created node, contains the ID,
     * such as e859588c-ae81-4c5e-a3b6-4c6109b6c905
     */
    private Node createFolder(String parentFolderId,
                              String folderName) {
        NodeBodyCreate nodeBodyCreate = new NodeBodyCreate();
        nodeBodyCreate.setName(folderName);
        nodeBodyCreate.setNodeType("cm:folder");
        Node folderNode = nodesApi.createNode(parentFolderId, nodeBodyCreate, autoRename, majorVersion, versioningEnabled,
                include, fields).getBody().getEntry();
        LOGGER.info("Created new folder in DocLib: {}", folderNode);

        return folderNode;
    }
}

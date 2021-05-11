package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.NodesApi;
import org.alfresco.core.model.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class ManageAssociationsCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(ManageAssociationsCmd.class);

    @Autowired
    NodesApi nodesApi;

    private Boolean autoRename = true;
    private Boolean majorVersion = true;
    private Boolean versioningEnabled = true;
    private String updateComment = null;
    private String updatedName = null;
    private List<String> include = null;
    private List<String> fields = null;
    private List<String> orderBy = null;
    private Integer skipCount = 0;
    private Integer maxItems = 100;
    private String where = null;
    private Boolean includeSource = false;

    public void execute() throws IOException {
        // List all folders and files (primary parent-child associations) in /Company Home/Data Dictionary
        NodeChildAssociationPagingList primaryChildAssociations =
                listPrimaryChildAssociations("-root-", "/Data Dictionary");

        // Create gadget folder, gadget image, and gadget review
        Node gadgetFolderNode = createFolder("My Gadgets", "");
        Node gadgetPictureNode = uploadFile(gadgetFolderNode.getId(), "gadget-picture.png", "somepicture.png");
        Node gadgetReviewNode = uploadFile(gadgetFolderNode.getId(), "gadget-review.txt", "sometext.txt");

        // Create the Gadget company node
        Map<String, Object> properties = new HashMap<>();
        properties.put("fdk:email", "info@coolgadgets.com");
        properties.put("fdk:url","www.coolgadgets.com");
        properties.put("fdk:city","London");
        Node companyNode = createNode(gadgetFolderNode.getId(), "Cool Gadgets Inc","fdk:company", properties);

        // Create a gadget node with associations using the FDK content model
        List<ChildAssociationBody> secondaryParentChildAssociations = new ArrayList<>();
        ChildAssociationBody childAssoc = new ChildAssociationBody();
        childAssoc.assocType("fdk:images");
        childAssoc.setChildId(gadgetPictureNode.getId());
        secondaryParentChildAssociations.add(childAssoc);
        List<AssociationBody> peer2peerAssociations = new ArrayList<>();
        AssociationBody peer2peerAssoc = new AssociationBody();
        peer2peerAssoc.assocType("fdk:reviews");
        peer2peerAssoc.setTargetId(gadgetReviewNode.getId());
        peer2peerAssociations.add(peer2peerAssoc);
        AssociationBody peer2peerAssoc2 = new AssociationBody();
        peer2peerAssoc2.assocType("fdk:company");
        peer2peerAssoc2.setTargetId(companyNode.getId());
        peer2peerAssociations.add(peer2peerAssoc2);
        Node gadgetNode = createNodeWithAssociations(
                gadgetFolderNode.getId(),"My Gadget", "fdk:gadget",
                secondaryParentChildAssociations, peer2peerAssociations);

        // List secondary parent-child associations for a node
        NodeChildAssociationPagingList secondaryAssoc = listSecondaryChildAssociations(gadgetNode.getId());

        // List peer-2-peer associations for a node
        NodeAssociationPagingList targetAssoc = listPeer2PeerAssociations(gadgetNode.getId());
    }

    /**
     * List primary parent-child associations. Basically list folder contents.
     *
     * @param rootNodeId         the id of the folder node that is the root. If relativeFolderPath is null, then content in this folder will be listed. Besides node ID the aliases -my-, -root- and -shared- are also supported.
     * @param relativeFolderPath path relative rootNodeId, if this is not null, then the content of this folder will be listed
     * @return a list of child node objects contained in the folder, or null if not found
     */
    private NodeChildAssociationPagingList listPrimaryChildAssociations(String rootNodeId, String relativeFolderPath) {
        LOGGER.info("Listing primary child associations for folder {}{}", rootNodeId, relativeFolderPath);
        NodeChildAssociationPagingList result = nodesApi.listNodeChildren(rootNodeId, skipCount, maxItems, orderBy, where, include,
                relativeFolderPath, includeSource, fields).getBody().getList();
        for (NodeChildAssociationEntry childNodeAssoc: result.getEntries()) {
            LOGGER.info("Found primary child [name=" + childNodeAssoc.getEntry().getName() + "]");
        }

        return result;
    }

    /**
     * List secondary parent-child associations.
     *
     * @param nodeId         the node to list assoc for
     * @return a list of child node objects contained in the node, or null if not found
     */
    private NodeChildAssociationPagingList listSecondaryChildAssociations(String nodeId) {
        LOGGER.info("Listing secondary child associations for node {}", nodeId);
        NodeChildAssociationPagingList result = nodesApi.listSecondaryChildren(
                nodeId, where, include, skipCount, maxItems, includeSource, fields).getBody().getList();
        for (NodeChildAssociationEntry childNodeAssoc: result.getEntries()) {
            LOGGER.info("Found secondary child [name=" + childNodeAssoc.getEntry().getName() + "]");
        }

        return result;
    }

    /**
     * List peer-2-peer associations.
     *
     * @param nodeId         the node to list assoc for
     * @return a list of assoc objects associated with the node
     */
    private NodeAssociationPagingList listPeer2PeerAssociations(String nodeId) {
        LOGGER.info("Listing peer-2-peer associations for node {}", nodeId);
        NodeAssociationPagingList result = nodesApi.listTargetAssociations(
                nodeId, where, include, fields).getBody().getList();
        for (NodeAssociationEntry targetAssoc: result.getEntries()) {
            LOGGER.info("Found target [name=" + targetAssoc.getEntry().getName() + "]");
        }

        return result;
    }

    /**
     * Create a node with associations.
     *
     * @param parentNodeId the parent node id
     * @param nodeName     the name of the node
     * @param nodeType     the type of the node
     * @param secondaryParentChildAssociations a list of secondary parent-child associations that should be set up
     * @param peer2peerAssociations a list of peer-2-peer associations that should be set up
     * @return a node object for the newly created node, contains the ID,
     * such as e859588c-ae81-4c5e-a3b6-4c6109b6c905
     */
    private Node createNodeWithAssociations(
            String parentNodeId,
            String nodeName,
            String nodeType,
            List<ChildAssociationBody> secondaryParentChildAssociations,
            List<AssociationBody> peer2peerAssociations) {
        NodeBodyCreate nodeBodyCreate = new NodeBodyCreate();
        nodeBodyCreate.setName(nodeName);
        nodeBodyCreate.setNodeType(nodeType);
        nodeBodyCreate.setSecondaryChildren(secondaryParentChildAssociations);
        nodeBodyCreate.setTargets(peer2peerAssociations);
        Node node = nodesApi.createNode(parentNodeId, nodeBodyCreate, autoRename, majorVersion, versioningEnabled,
                include, fields).getBody().getEntry();
        LOGGER.info("Created new node with associations: {}", node);

        return node;
    }

    /**
     * Make the remote call to create a folder in the repository, if it does not exist.
     *
     * @param folderName         the name of the folder
     * @param relativeFolderPath path relative to /Company Home
     * @return a node object for the newly created node, contains the ID,
     * such as e859588c-ae81-4c5e-a3b6-4c6109b6c905
     */
    private Node createFolder(String folderName,
                              String relativeFolderPath) {
        String nodeId = "-root-";
        NodeBodyCreate nodeBodyCreate = new NodeBodyCreate();
        nodeBodyCreate.setName(folderName);
        nodeBodyCreate.setNodeType("cm:folder");
        nodeBodyCreate.setRelativePath(relativeFolderPath);
        Node folderNode = nodesApi.createNode(nodeId, nodeBodyCreate, autoRename, majorVersion, versioningEnabled,
                include, fields).getBody().getEntry();
        LOGGER.info("Created new folder: {}", folderNode);

        return folderNode;
    }

    /**
     * Create a node
     *
     * @param parentNodeId  the node id for parent folder
     * @param nodeName      the name of the node
     * @param nodeType      the type of the node
     * @return a node object for the newly created node, contains the ID,
     * such as e859588c-ae81-4c5e-a3b6-4c6109b6c905
     */
    private Node createNode(String parentNodeId,
                            String nodeName,
                            String nodeType,
                            Map<String, Object> properties) {
        NodeBodyCreate nodeBodyCreate = new NodeBodyCreate();
        nodeBodyCreate.setName(nodeName);
        nodeBodyCreate.setNodeType(nodeType);
        nodeBodyCreate.setProperties(properties);
        Node node = nodesApi.createNode(
                parentNodeId, nodeBodyCreate, autoRename, majorVersion, versioningEnabled, include, fields).getBody().getEntry();
        LOGGER.info("Created new node: {}", node);

        return node;
    }

    /**
     * Upload a file from disk
     */
    private Node uploadFile(String folderId, String fileName, String filePath) {
        // Create the file node metadata
        NodeBodyCreate nodeBodyCreate = new NodeBodyCreate();
        nodeBodyCreate.setName(fileName);
        nodeBodyCreate.setNodeType("cm:content");
        Node fileNode = nodesApi.createNode(
                folderId, nodeBodyCreate, autoRename, majorVersion, versioningEnabled, include, fields).getBody().getEntry();

        // Get the file bytes
        File someFile = new File(filePath);
        byte[] fileData = null;
        try {
            fileData = FileUtils.readFileToByteArray(someFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add the file node content
        Node updatedFileNode = nodesApi.updateNodeContent(fileNode.getId(),
                fileData, majorVersion, updateComment, updatedName, include, fields).getBody().getEntry();

        LOGGER.info("Created file with content: {}", updatedFileNode.toString());

        return updatedFileNode;
    }

}

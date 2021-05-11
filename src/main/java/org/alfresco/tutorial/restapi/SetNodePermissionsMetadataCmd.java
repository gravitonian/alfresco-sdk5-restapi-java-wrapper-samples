package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.NodesApi;
import org.alfresco.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SetNodePermissionsMetadataCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(SetNodePermissionsMetadataCmd.class);

    @Autowired
    NodesApi nodesApi;

    public void execute(String nodeId) throws IOException {
        // First get current permissions
        PermissionsInfo currentPermissions = getNodePermissions(nodeId);

        // Update with permissions for a user and a group
        // Add current permissions first, it will overwrite so we need to add what's already set
        PermissionsBody permissionsBody = new PermissionsBody();
        permissionsBody.setIsInheritanceEnabled(true);
        permissionsBody.setLocallySet(currentPermissions.getLocallySet());
        PermissionElement engineeringGroupPermission = new PermissionElement();
        engineeringGroupPermission.setName("Collaborator");
        engineeringGroupPermission.setAuthorityId("GROUP_engineering");
        engineeringGroupPermission.setAccessStatus(PermissionElement.AccessStatusEnum.ALLOWED);
        permissionsBody.addLocallySetItem(engineeringGroupPermission);
        PermissionElement testUserPermission = new PermissionElement();
        testUserPermission.setName("Contributor");
        testUserPermission.setAuthorityId("tester");
        testUserPermission.setAccessStatus(PermissionElement.AccessStatusEnum.ALLOWED);
        permissionsBody.addLocallySetItem(testUserPermission);

        // Update permissions for node
        Node node = updateNodePermissions(nodeId, permissionsBody);
    }

    /**
     * Get node permissions.
     *
     * @param nodeId the id of the node that we want to get permissions for.
     * @return updated Node object
     */
    private PermissionsInfo getNodePermissions(String nodeId) {
        String relativePath = null;
        List<String> fields = null;
        List<String> include = new ArrayList<>();
        include.add("permissions");

        NodeEntry result = nodesApi.getNode(nodeId, include, relativePath, fields).getBody();
        LOGGER.info("Got node including permissions {}", result.getEntry());

        return result.getEntry().getPermissions();
    }

    /**
     * Update node permissions.
     *
     * @param nodeId the id of the node that we want to update permissions for.
     * @param permissionsBody permissions to set on the node
     * @return updated Node object
     */
    private Node updateNodePermissions(String nodeId,
                            PermissionsBody permissionsBody) {

        List<String> include = new ArrayList<>();
        include.add("permissions");
        List<String> fields = null;

        NodeBodyUpdate nodeBodyUpdate = new NodeBodyUpdate();
        nodeBodyUpdate.setPermissions(permissionsBody);

        NodeEntry result = nodesApi.updateNode(nodeId, nodeBodyUpdate, include, fields).getBody();
        LOGGER.info("Updated node permissions {}", result.getEntry());

        return result.getEntry();
    }
}

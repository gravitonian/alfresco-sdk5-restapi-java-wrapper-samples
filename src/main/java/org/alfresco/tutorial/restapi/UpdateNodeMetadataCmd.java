package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.NodesApi;
import org.alfresco.core.model.Node;
import org.alfresco.core.model.NodeBodyUpdate;
import org.alfresco.core.model.NodeEntry;
import org.alfresco.core.model.PermissionsBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UpdateNodeMetadataCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(UpdateNodeMetadataCmd.class);

    @Autowired
    NodesApi nodesApi;

    public void execute(String nodeId) throws IOException {
        Map<String, Object> properties = new HashMap<>();
        properties.put("cm:title", "UPDATED title");
        properties.put("cm:description", "UPDATED description");
        Node node = updateNode(nodeId, "newname.txt", properties, null, null);
    }

    /**
     * Update a node (such as file/folder).
     *
     * @param nodeId the id of the node that we want to update metadata for.
     * @param newName a new name for the node (sets cm:name)
     * @param properties the properties we want to update and their new values
     * @param aspectNames a list of aspect names to set the node, not that it needs to include all aspects as it will overwrite
     * @param permissionsBody permissions to set on the node
     * @return updated Node object
     */
    private Node updateNode(String nodeId,
                            String newName,
                            Map<String, Object> properties,
                            List<String> aspectNames,
                            PermissionsBody permissionsBody) {

        List<String> include = null;
        List<String> fields = null;

        NodeBodyUpdate nodeBodyUpdate = new NodeBodyUpdate();
        nodeBodyUpdate.setName(newName);
        nodeBodyUpdate.setProperties(properties);
        nodeBodyUpdate.setAspectNames(aspectNames);
        nodeBodyUpdate.setPermissions(permissionsBody);

        NodeEntry result = nodesApi.updateNode(nodeId, nodeBodyUpdate, include, fields).getBody();
        LOGGER.info("Updated node {}", result.getEntry());

        return result.getEntry();
    }
}

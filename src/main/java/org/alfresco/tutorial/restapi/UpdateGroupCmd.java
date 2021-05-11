package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.GroupsApi;
import org.alfresco.core.model.GroupBodyUpdate;
import org.alfresco.core.model.GroupEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class UpdateGroupCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(UpdateGroupCmd.class);

    @Autowired
    GroupsApi groupsApi;

    public void execute(String groupId, String newName) throws IOException {
        List<String> fields = null;
        List<String> include = null;

        GroupBodyUpdate groupBodyUpdate = new GroupBodyUpdate();
        groupBodyUpdate.setDisplayName(newName);

        GroupEntry group = groupsApi.updateGroup(groupId, groupBodyUpdate, include, fields).getBody();
        LOGGER.info("Updated group {}", group);
    }
}

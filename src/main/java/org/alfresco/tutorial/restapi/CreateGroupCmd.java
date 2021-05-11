package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.GroupsApi;
import org.alfresco.core.model.GroupBodyCreate;
import org.alfresco.core.model.GroupEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CreateGroupCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(CreateGroupCmd.class);

    @Autowired
    GroupsApi groupsApi;

    public void execute(String groupId, String name) throws IOException {
        List<String> fields = null;
        List<String> include = null;

        GroupBodyCreate groupBodyCreate = new GroupBodyCreate();
        groupBodyCreate.setId(groupId);
        groupBodyCreate.setDisplayName(name);
        GroupEntry groupEntry = groupsApi.createGroup(groupBodyCreate, include, fields).getBody();
        LOGGER.info("Created group  {}", groupEntry.getEntry());
    }
}

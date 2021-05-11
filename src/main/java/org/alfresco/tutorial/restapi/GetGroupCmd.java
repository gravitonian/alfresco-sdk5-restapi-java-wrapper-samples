package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.GroupsApi;
import org.alfresco.core.model.GroupEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class GetGroupCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(GetGroupCmd.class);

    @Autowired
    GroupsApi groupsApi;

    public void execute(String groupId) throws IOException {
        List<String> fields = null;
        List<String> include = null;

        GroupEntry groupEntry = groupsApi.getGroup(groupId, include, fields).getBody();
        LOGGER.info("Got group metadata  {}", groupEntry.getEntry());
    }
}

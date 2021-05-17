
/*
 * Copyright 2021-2021 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
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


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

import org.alfresco.core.handler.TrashcanApi;
import org.alfresco.core.model.DeletedNodeBodyRestore;
import org.alfresco.core.model.NodeEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RestoreDeletedNodeCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(RestoreDeletedNodeCmd.class);

    @Autowired
    TrashcanApi trashcanApi;

    public void execute(String nodeId, String restoreFolderId) throws IOException {
        List<String> fields = null;

        // POST body need to ne supplied with target folder ID
        DeletedNodeBodyRestore deletedNodeBodyRestore = new DeletedNodeBodyRestore();
        deletedNodeBodyRestore.setTargetParentId(restoreFolderId);
        deletedNodeBodyRestore.setAssocType("cm:contains");
        NodeEntry restoredNode = trashcanApi.restoreDeletedNode(nodeId, fields, deletedNodeBodyRestore).getBody();
        LOGGER.info("Restored node: {}", restoredNode.getEntry());
    }
}

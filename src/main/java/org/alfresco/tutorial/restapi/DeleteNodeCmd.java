
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

import org.alfresco.core.handler.NodesApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DeleteNodeCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(DeleteNodeCmd.class);

    @Autowired
    NodesApi nodesApi;

    public void execute(String nodeId) throws IOException {
        // If true, then the node is deleted permanently, without moving to the trashcan.
        // Only the owner of the node or an admin can permanently delete the node.
        // default value = false
        Boolean permanent = false;

        ResponseEntity<Void> deletedNodeResponse = nodesApi.deleteNode(nodeId, permanent);
        LOGGER.info("Deleted node response: {}", deletedNodeResponse);
    }
}

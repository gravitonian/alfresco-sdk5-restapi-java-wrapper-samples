
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
import org.alfresco.core.model.Node;
import org.alfresco.core.model.NodeBodyLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class LockNodeCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(LockNodeCmd.class);

    @Autowired
    NodesApi nodesApi;

    public void execute(String nodeId) throws IOException {
        List<String> include = null;
        List<String> fields = null;

        Node node = nodesApi.getNode(nodeId, include, null, fields).getBody().getEntry();
        LOGGER.info("Got node we want to lock ID: {} Is locked ?: {}", node.getId(), node.isIsLocked());

        if (!node.isIsLocked()) {
            // Lock the file with exclusive lock
            NodeBodyLock nodeBodyLock = new NodeBodyLock();
            Node lockedNode = nodesApi.lockNode(nodeId, nodeBodyLock, include, fields).getBody().getEntry();
            LOGGER.info("Locked node: {}", lockedNode);

            // Do the work on the file

            // Unlock the file
            Node unLockedNode = nodesApi.unlockNode(nodeId, include, fields).getBody().getEntry();
            LOGGER.info("Unlocked node: {}", unLockedNode);

        }
    }
}

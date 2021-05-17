
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
import org.alfresco.core.model.NodeBodyMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MoveNodeCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(MoveNodeCmd.class);

    @Autowired
    NodesApi nodesApi;

    public void execute(String nodeId, String parentFolderNodeId) throws IOException {
        List<String> include = new ArrayList<>();
        List<String> fields = null;

        include.add("path"); // add extra path property in response so we can see location of node

        Node node = nodesApi.getNode(nodeId, include, null, fields).getBody().getEntry();
        LOGGER.info("Got node before move ID: {} Parent: {} Location: {}",
                node.getId(), node.getParentId(), node.getPath().getName());

        NodeBodyMove nodeBodyMove = new NodeBodyMove();
        nodeBodyMove.setTargetParentId(parentFolderNodeId);
        Node movedNode = nodesApi.moveNode(nodeId, nodeBodyMove, include, fields).getBody().getEntry();
        LOGGER.info("Moved node ID: {} Parent: {} Location: {}",
                movedNode.getId(), movedNode.getParentId(), movedNode.getPath().getName());
    }
}

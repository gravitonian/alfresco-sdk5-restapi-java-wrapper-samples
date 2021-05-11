package org.alfresco.tutorial.restapi;

import org.alfresco.core.handler.QueriesApi;
import org.alfresco.core.model.NodeEntry;
import org.alfresco.core.model.NodePagingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class FindNodesCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(FindNodesCmd.class);

    @Autowired
    QueriesApi queriesApi;

    public void execute() throws IOException {
        String rootNodeId = "-root-"; // The id of the node to start the search from. Supports the aliases -my-, -root- and -shared-.
        Integer skipCount = 0;
        Integer maxItems = 100;

        // Restrict the returned results to only those of the given node type and its sub-types
        String nodeType = null;

        List<String> include = null;
        List<String> orderBy = null;
        List<String> fields = null;

        String term = "Dict*";
        LOGGER.info("Searching for nodes by term: {}", term);
        NodePagingList result = queriesApi.findNodes(
                term, rootNodeId, skipCount, maxItems, nodeType, include, orderBy, fields).getBody().getList();
        for (NodeEntry node: result.getEntries()) {
            LOGGER.info("Found node [name={}][id={}]", node.getEntry().getName(), node.getEntry().getId());
        }
    }
}

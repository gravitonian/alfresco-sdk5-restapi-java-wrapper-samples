
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

import org.alfresco.core.handler.RenditionsApi;
import org.alfresco.core.model.RenditionEntry;
import org.alfresco.core.model.RenditionPagingList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ListRenditionsCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(ListRenditionsCmd.class);

    @Autowired
    RenditionsApi renditionsApi;

    public void execute(String fileNodeId) throws IOException {
        RenditionPagingList nodeRenditions = listRenditions(fileNodeId);
    }
    
    /**
     * List renditions for a file node.
     *
     * @param fileNodeId the id of the file node
     * @return a list of renditions, or null if not found
     */
    private RenditionPagingList listRenditions(String fileNodeId) {
        String where = null; // filter renditions

        LOGGER.info("Listing versions for file node ID {}", fileNodeId);
        RenditionPagingList result = renditionsApi.listRenditions(fileNodeId, where).getBody().getList();
        for (RenditionEntry renditionEntry: result.getEntries()) {
            LOGGER.info("Node rendition: " + renditionEntry.getEntry().toString());
        }

        return result;
    }
}


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

import org.alfresco.core.handler.TagsApi;
import org.alfresco.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ManageTagsCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(ManageTagsCmd.class);

    private Integer skipCount = 0;
    private Integer maxItems = 100;
    private List<String> fields = null;
    private List<String> include = new ArrayList<>();

    // Include an extra field with tag count
    public ManageTagsCmd() {
        include.add("count");
    }

    @Autowired
    TagsApi tagsApi;

    public void execute(String nodeId) throws IOException {
        Tag firstTag = createTag(nodeId, "tag-one");
        Tag secondTag = createTag(nodeId, "tag-two");

        LOGGER.info("Listing tags for the whole repository: ");
        TagPagingList repoTags = tagsApi.listTags(skipCount, maxItems, fields, include).getBody().getList();
        for (TagEntry repoTagEntry: repoTags.getEntries()) {
            LOGGER.info("    {} count: {}", repoTagEntry.getEntry().getTag(), repoTagEntry.getEntry().getCount());
        }

        LOGGER.info("Listing tags for node: {}", nodeId);
        TagPagingList nodeTags = tagsApi.listTagsForNode(nodeId, skipCount, maxItems, fields).getBody().getList();
        for (TagEntry nodeTagEntry: nodeTags.getEntries()) {
            LOGGER.info("    {}", nodeTagEntry.getEntry());
        }
    }

    private Tag createTag(String nodeId, String text) {
        TagBody tagBody = new TagBody();
        tagBody.setTag(text);
        Tag tag = tagsApi.createTagForNode(nodeId, tagBody, fields).getBody().getEntry();
        LOGGER.info("Created Tag {}", tag);
        return tag;
    }
}

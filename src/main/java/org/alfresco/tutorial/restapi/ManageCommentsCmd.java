
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

import org.alfresco.core.handler.CommentsApi;
import org.alfresco.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ManageCommentsCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(ManageCommentsCmd.class);

    private Integer skipCount = 0;
    private Integer maxItems = 100;
    private List<String> fields = new ArrayList<>();

    // Fetch only the fields we are interested in
    public ManageCommentsCmd() {
        fields.add("content,createdAt");
    }

    @Autowired
    CommentsApi commentsApi;

    public void execute(String nodeId) throws IOException {
        Comment firstComment = createComment(nodeId, "First comment");
        Comment secondComment = createComment(nodeId, "Second comment");

        LOGGER.info("Listing comments: ");
        CommentPagingList comments = commentsApi.listComments(nodeId, skipCount, maxItems, fields).getBody().getList();
        for (CommentEntry commentEntry: comments.getEntries()) {
            LOGGER.info("    {}", commentEntry.getEntry());
        }
    }

    private Comment createComment(String nodeId, String text) {
        CommentBody commentBody = new CommentBody();
        commentBody.setContent(text);
        Comment comment = commentsApi.createComment(nodeId, commentBody, fields).getBody().getEntry();
        LOGGER.info("{}", comment);
        return comment;
    }
}

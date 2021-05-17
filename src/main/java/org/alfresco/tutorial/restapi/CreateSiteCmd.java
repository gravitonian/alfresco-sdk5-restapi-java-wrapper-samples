
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

import org.alfresco.core.handler.SitesApi;
import org.alfresco.core.model.Site;
import org.alfresco.core.model.SiteBodyCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CreateSiteCmd {
    static final Logger LOGGER = LoggerFactory.getLogger(CreateSiteCmd.class);

    private List<String> fields = null;

    // Flag to indicate whether the Share-specific (surf) configuration files for the site should not be created
    // Default = false
    Boolean skipConfiguration = null;
    // Flag to indicate whether the site should not be added to the user's site favorites
    // Default = false
    Boolean skipAddToFavorites = null;

    @Autowired
    SitesApi sitesApi;

    public void execute(String siteId) throws IOException {
        SiteBodyCreate siteBodyCreate = new SiteBodyCreate();
        siteBodyCreate.setId(siteId);
        siteBodyCreate.setTitle("title-" + siteId);
        siteBodyCreate.setDescription("description-" + siteId);
        siteBodyCreate.setVisibility(SiteBodyCreate.VisibilityEnum.PUBLIC);

        Site site = sitesApi.createSite(siteBodyCreate, skipConfiguration, skipAddToFavorites, fields).getBody().getEntry();

        LOGGER.info("Created site: {}", site);
    }
}

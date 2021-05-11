package org.alfresco.tutorial.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class RestApiApplication implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiApplication.class);

    @Autowired
    FindNodesCmd findNodesCmd;

    @Autowired
    FindSitesCmd findSitesCmd;

    @Autowired
    FindPeopleCmd findPeopleCmd;

    @Autowired
    GetNodeMetadataCmd getNodeMetadataCmd;

    @Autowired
    ListFolderContentCmd listFolderContentCmd;

    @Autowired
    CreateSiteCmd createSiteCmd;

    @Autowired
    CreateFolderCmd createFolderCmd;

    @Autowired
    CreateFileCmd createFileCmd;

    @Autowired
    CreateFileCustomTypeCmd createFileCustomTypeCmd;

    @Autowired
    UploadNewFileVersionCmd uploadNewFileVersionCmd;

    @Autowired
    SearchCmd searchCmd;

    @Autowired
    ListVersionHistoryCmd listVersionHistoryCmd;

    @Autowired
    GetNodeContentCmd getNodeContentCmd;

    @Autowired
    GetMultipleNodeContentCmd getMultipleNodeContentCmd;

    @Autowired
    ListRenditionsCmd listRenditionsCmd;

    @Autowired
    GetRenditionContentCmd getRenditionContentCmd;

    @Autowired
    UpdateNodeMetadataCmd updateNodeMetadataCmd;

    @Autowired
    SetNodePermissionsMetadataCmd setNodePermissionsMetadataCmd;

    @Autowired
    ManageAssociationsCmd manageAssociationsCmd;

    @Autowired
    ManageCommentsCmd manageCommentsCmd;

    @Autowired
    ManageTagsCmd manageTagsCmd;

    @Autowired
    CopyNodeCmd copyNodeCmd;

    @Autowired
    MoveNodeCmd moveNodeCmd;

    @Autowired
    LockNodeCmd lockNodeCmd;

    @Autowired
    LinkFileCmd linkFileCmd;

    @Autowired
    DeleteNodeCmd deleteNodeCmd;

    @Autowired
    ListDeletedNodesCmd listDeletedNodesCmd;

    @Autowired
    RestoreDeletedNodeCmd restoreDeletedNodeCmd;

    @Autowired
    AddSiteContentCmd addSiteContentCmd;

    @Autowired
    AddSiteMembersCmd addSiteMembersCmd;

    @Autowired
    ListPeopleCmd listPeopleCmd;

    @Autowired
    CreatePersonCmd createPersonCmd;

    @Autowired
    GetPersonMetadataCmd getPersonMetadataCmd;

    @Autowired
    UpdatePersonMetadataCmd updatePersonMetadataCmd;

    @Autowired
    RequestPwdResetCmd requestPwdResetCmd;

    @Autowired
    ListPersonGroupMembershipsCmd listPersonGroupMembershipsCmd;

    @Autowired
    ListGroupsCmd listGroupsCmd;

    @Autowired
    CreateGroupCmd createGroupCmd;

    @Autowired
    GetGroupCmd getGroupCmd;

    @Autowired
    UpdateGroupCmd updateGroupCmd;

    @Autowired
    ListGroupMembersCmd listGroupMembersCmd;

    @Autowired
    CreateGroupMembershipCmd createGroupMembershipCmd;

    @Autowired
    DeleteGroupMembershipCmd deleteGroupMembershipCmd;

    @Autowired
    ListAuditAppsCmd listAuditAppsCmd;

    @Autowired
    GetAuditAppCmd getAuditAppCmd;

    @Autowired
    EnableDisableAuditAppCmd enableDisableAuditAppCmd;

    @Autowired
    ListAuditLogsCmd listAuditLogsCmd;

    @Autowired
    ListNodeAuditLogsCmd listNodeAuditLogsCmd;

    @Autowired
    GetAuditLogCmd getAuditLogCmd;

    @Autowired
    DeleteAuditLogsForAppCmd deleteAuditLogsForAppCmd;

    @Autowired
    DeleteAuditLogCmd deleteAuditLogCmd;

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }

    public void run(String... args) throws Exception {
        for (int i = 0; i < args.length; ++i) {
            LOGGER.info("args[{}]: {}", i, args[i]);
        }

        String command = args[0];

        switch (command) {
            case "get-node":
                getNodeMetadataCmd.execute();
                break;
            case "list-folder":
                listFolderContentCmd.execute();
                break;
            case "find-nodes":
                findNodesCmd.execute();
                break;
            case "find-sites":
                findSitesCmd.execute();
                break;
            case "find-people":
                findPeopleCmd.execute();
                break;
            case "create-site":
                createSiteCmd.execute(args[1]); // siteId
                break;
            case "create-folder":
                createFolderCmd.execute();
                break;
            case "create-file":
                createFileCmd.execute();
                break;
            case "create-file-custom":
                createFileCustomTypeCmd.execute();
                break;
            case "search":          // siteId, term
                searchCmd.execute(args[1], args[2]);
                break;
            case "upload-new-version":          // textFileNodeId, binFileNodeId
                uploadNewFileVersionCmd.execute(args[1], args[2]);
                break;
            case "list-file-versions":       // fileNodeId
                listVersionHistoryCmd.execute(args[1]);
                break;
            case "get-file-content":       // file node Id, filename on disk
                getNodeContentCmd.execute(args[1], args[2]);
                break;
            case "get-multiple-file-content":       // file node Id 1, file node Id 2, zip filename on disk
                List<String> nodeIds = new ArrayList<String>();
                nodeIds.add(args[1]);
                nodeIds.add(args[2]);
                getMultipleNodeContentCmd.execute(nodeIds.toArray(new String[0]), args[3]);
                break;
            case "list-renditions":     // file node id
                listRenditionsCmd.execute(args[1]);
                break;
            case "get-rendition-content": // file node Id, rendition Id, filename on disk
                getRenditionContentCmd.execute(args[1], args[2], args[3]);
                break;
            case "update-metadata":          // file node ID
                updateNodeMetadataCmd.execute(args[1]);
                break;
            case "update-permissions":          // file/folder node ID
                setNodePermissionsMetadataCmd.execute(args[1]);
                break;
            case "manage-associations":
                manageAssociationsCmd.execute();
                break;
            case "manage-comments":     // file node ID
                manageCommentsCmd.execute(args[1]);
                break;
            case "manage-tags":     // file node ID
                manageTagsCmd.execute(args[1]);
                break;
            case "copy-node":       // file node ID, folder node ID
                copyNodeCmd.execute(args[1], args[2]);
                break;
            case "move-node":       // file node ID, folder node ID
                moveNodeCmd.execute(args[1], args[2]);
                break;
            case "lock-node":     // file node ID
                lockNodeCmd.execute(args[1]);
                break;
            case "link-file":       // destination parent folder node ID, file node ID
                linkFileCmd.execute(args[1], args[2]);
                break;
            case "delete-node":     // node ID
                deleteNodeCmd.execute(args[1]);
                break;
            case "list-deleted-nodes":
                listDeletedNodesCmd.execute();
                break;
            case "restore-deleted-node":     // Deleted node ID
                restoreDeletedNodeCmd.execute(args[1], args[2]);
                break;
            case "add-site-content":    // site id
                addSiteContentCmd.execute(args[1]);
                break;
            case "add-site-member":    // site id, person id
                addSiteMembersCmd.execute(args[1], args[2]);
                break;
            case "list-people":
                listPeopleCmd.execute();
                break;
            case "create-person":       // username, pwd, first, last, email
                createPersonCmd.execute(args[1], args[2], args[3], args[4], args[5]);
                break;
            case "get-person-metadata":     // username
                getPersonMetadataCmd.execute(args[1]);
                break;
            case "update-person-metadata":      // username
                updatePersonMetadataCmd.execute(args[1]);
                break;
            case "request-pwd-reset": // username
                requestPwdResetCmd.execute(args[1]);
                break;
            case "list-person-group-memberships":   // username
                listPersonGroupMembershipsCmd.execute(args[1]);
                break;
            case "list-groups":
                listGroupsCmd.execute();
                break;
            case "create-group":        // id, name
                createGroupCmd.execute(args[1], args[2]);
                break;
            case "get-group":       // id
                getGroupCmd.execute(args[1]);
                break;
            case "update-group":        // id, new name
                updateGroupCmd.execute(args[1], args[2]);
                break;
            case "list-group-members":  // id
                listGroupMembersCmd.execute(args[1]);
                break;
            case "create-group-membership":     // group id, member id, member type
                createGroupMembershipCmd.execute(args[1], args[2], args[3]);
                break;
            case "delete-group-membership":     // group id, member id
                deleteGroupMembershipCmd.execute(args[1], args[2]);
                break;
            case "list-audit-apps":
                listAuditAppsCmd.execute();
                break;
            case "get-audit-app":     // app id
                getAuditAppCmd.execute(args[1]);
                break;
            case "enable-audit-app":            // app id
                enableDisableAuditAppCmd.execute(args[1]);
                break;
            case "list-audit-logs":         // app id
                listAuditLogsCmd.execute(args[1]);
                break;
            case "list-audit-logs-node":         // node id
                listNodeAuditLogsCmd.execute(args[1]);
                break;
            case "get-audit-log":       // app id, log id
                getAuditLogCmd.execute(args[1], args[2]);
                break;
            case "delete-audit-logs-for-app":   // app id
                deleteAuditLogsForAppCmd.execute(args[1]);
                break;
            case "delete-audit-log":   // app id, log id
                deleteAuditLogCmd.execute(args[1], args[2]);
                break;
            default:
                LOGGER.error("Command {} is not available", command);
        }

    }
}

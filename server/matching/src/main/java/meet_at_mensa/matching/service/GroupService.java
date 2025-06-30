package meet_at_mensa.matching.service;

import java.time.LocalDate;
import java.util.UUID;

import org.openapitools.model.Group;
import org.openapitools.model.Location;
import org.springframework.beans.factory.annotation.Autowired;

import meet_at_mensa.matching.exception.GroupNotFoundException;
import meet_at_mensa.matching.model.GroupEntity;
import meet_at_mensa.matching.repository.GroupRepository;

public class GroupService {


    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ConversationStarterService conversationStarterService;

    @Autowired
    private MatchService matchService;

    /**
     * Searches the database for a group with this groupID
     *
     * @param groupID UUID of the group being retrieved
     * @return Group object representing the group
     * @throws GroupNotFoundException if no group is found
     */
    public Group getGroup(UUID groupID) {

        // find group with this ID or throw exception if such a group does not exist
        GroupEntity groupEntity = groupRepository.findById(groupID)
            .orElseThrow(() -> new GroupNotFoundException(String.format("Group with ID %s not found", groupID)));

        Group group = new Group(
            groupEntity.getGroupID(), // id
            groupEntity.getDate(), // date
            groupEntity.getTimeslot(), // timeslot
            groupEntity.getLocation(), // Location (enum)
            matchService.getMatchStatus(groupID), // List<MatchStatus>
            conversationStarterService.getPrompts(groupID) // ConversationStarterCollection
        );

        return group;

    }

    /**
     * Registers a new group in the database
     * 
     * This method should only be called by other services and is not intended for user by the controller classes
     * as it does not add corresponding matches or conversation starters
     *
     * @param date date of the group meeting
     * @param time time of the group meeting
     * @param location location of the group meeting
     * @return Group ID representing the group (Note: Does not return the whole group object as it does not handle matches and conversation-starters)
     */
    protected UUID registerGroup(LocalDate date, Integer timeslot, Location location) {

        // create new group
        GroupEntity group = new GroupEntity(date, timeslot, location);

        // save to list
        groupRepository.save(group);

        // return ID
        return group.getGroupID();
    }
    
}

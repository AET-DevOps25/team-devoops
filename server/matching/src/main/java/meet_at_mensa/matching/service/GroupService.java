package meet_at_mensa.matching.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.openapitools.model.Group;
import org.openapitools.model.Location;
import org.openapitools.model.MatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meet_at_mensa.matching.exception.GroupNotFoundException;
import meet_at_mensa.matching.exception.MatchNotFoundException;
import meet_at_mensa.matching.exception.RequestNotFoundException;
import meet_at_mensa.matching.model.GroupEntity;
import meet_at_mensa.matching.model.MatchEntity;
import meet_at_mensa.matching.repository.GroupRepository;
import meet_at_mensa.matching.repository.MatchRepository;

@Service
public class GroupService {


    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ConversationStarterService conversationStarterService;

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
            getGroupStatus(groupID), // List<MatchStatus>
            conversationStarterService.getPrompts(groupID) // ConversationStarterCollection
        );

        return group;

    }


    /**
     * Gets all groups with events on a given date
     * 
     *
     * @param date date of the group meeting
     * @return List<Group> objects
     * @throws groupNotFoundException if no groups are available on this date
     */
    public List<Group> getGroupsOnDate(LocalDate date) {

        Iterable<GroupEntity> groupEntities = groupRepository.findByDate(date);

        // if the list is empty, throw a GroupNotFound
        if (!groupEntities.iterator().hasNext()) {
            throw new GroupNotFoundException();
        }

        // create an empty liste
        List<Group> groups = new ArrayList<Group>();

        // collect all groups into a list
        for (GroupEntity groupEntity : groupEntities) {
            
            groups.add(getGroup(groupEntity.getGroupID()));

        }

        // return list
        return groups;

    }


    /**
     * Gets all groups older than a given date
     *
     * @param date date of the group meeting
     * @return List<Group> objects
     * @throws groupNotFoundException if no groups are available on this date
     */
    public List<Group> getGroupsOlderThan(LocalDate date) {

        Iterable<GroupEntity> groupEntities = groupRepository.findAll();

        // if the list is empty, throw a GroupNotFound
        if (!groupEntities.iterator().hasNext()) {
            throw new GroupNotFoundException();
        }

        // create an empty liste
        List<Group> groups = new ArrayList<Group>();

        // collect all groups into a list
        for (GroupEntity groupEntity : groupEntities) {

            // adds to list if group is older than the date
            if(groupEntity.getDate().isBefore(date)) {
                groups.add(getGroup(groupEntity.getGroupID()));
            }

        }

        // return list
        return groups;

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
    

    /**
     * Retieves the match status for all users in a group
     *
     *
     * @param groupID UUID of the group
     * @return List<MatchStatus> objects representing each users' status
     * @throws MatchNotFoundException if no user with id {userID} is found
     */
    protected List<MatchStatus> getGroupStatus(UUID groupID){

        // get all matches in the system for a given group ID
        Iterable<MatchEntity> matchEntities = matchRepository.findByGroupID(groupID);

        // if list is empty, throw exception
        if (!matchEntities.iterator().hasNext()) {
            throw new MatchNotFoundException();
        }

        // create blank matchstatus list
        List<MatchStatus> groupStatus = new ArrayList<MatchStatus>();

        for (MatchEntity matchEntity : matchEntities) {

            // create a match Status object for a single user
            MatchStatus matchStatus = new MatchStatus(matchEntity.getUserID(), matchEntity.getInviteStatus());

            // add it to the list
            groupStatus.add(matchStatus);

        }

        // return the list
        return groupStatus;

    }

    /**
     * Removes a group and it's conversation-starters from the database
     *
     *
     * @param groupID UUID of the group to be removed
     * @return true if group was removed
     * @throws GroupNotFoundException if no group with groupID is found
     */
    protected Boolean removeGroup(UUID groupID) {

        // check if group exists
        groupRepository.findById(groupID)
            .orElseThrow(() -> new GroupNotFoundException());

        // delete entry from grouprepository
        groupRepository.deleteById(groupID);

        // remove group prompts
        conversationStarterService.removePrompts(groupID);

        // return true if deleted
        return true;

    }

}

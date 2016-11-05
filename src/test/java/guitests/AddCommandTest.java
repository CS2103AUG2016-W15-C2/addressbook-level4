package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import w15c2.tusk.commons.core.Messages;
import w15c2.tusk.logic.commands.taskcommands.AddTaskCommand;
import w15c2.tusk.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

//    @Test
//    public void add() {
//        //add one person
//        TestPerson[] currentList = td.getTypicalPersons();
//        TestPerson personToAdd = td.hoon;
//        assertAddSuccess(personToAdd, currentList);
//        currentList = TestUtil.addPersonsToList(currentList, personToAdd);
//
//        //add another person
//        personToAdd = td.ida;
//        assertAddSuccess(personToAdd, currentList);
//        currentList = TestUtil.addPersonsToList(currentList, personToAdd);
//
//        //add duplicate person
//        commandBox.runCommand(td.hoon.getAddCommand());
//        assertResultMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(taskListPanel.isListMatching(currentList));
//
//        //add to empty list
//        commandBox.runCommand("clear");
//        assertAddSuccess(td.alice);
//
//        //invalid command
//        commandBox.runCommand("adds Johnny");
//        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
//    }
//
//    private void assertAddSuccess(TestPerson personToAdd, TestPerson... currentList) {
//        commandBox.runCommand(personToAdd.getAddCommand());
//
//        //confirm the new card contains the right data
//        TaskCardHandle addedCard = taskListPanel.navigateToPerson(personToAdd.getName().fullName);
//        assertMatching(personToAdd, addedCard);
//
//        //confirm the list now contains all previous persons plus the new person
//        TestPerson[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
//        assertTrue(taskListPanel.isListMatching(expectedList));
//    }

}

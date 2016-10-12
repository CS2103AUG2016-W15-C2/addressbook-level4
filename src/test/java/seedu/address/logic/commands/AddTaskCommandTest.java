package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.AddTaskCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.model.task.TaskManager;

public class AddTaskCommandTest {

	// Initialized to support the tests
	InMemoryTaskList model;

	@Before
	public void setup() {
		model = new TaskManager();
	}
	
	@Test
	public void addTask_validDescription() throws IllegalValueException {
		/* CommandResult should return a string that denotes success in execution if description 
		 * given to AddTaskCommand constructor is a string with size > 0
		 */
		AddTaskCommand command = new AddTaskCommand("Meeting");
		command.setData(model);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(AddTaskCommand.MESSAGE_SUCCESS, "[Floating Task][Description: Meeting]")));
	}
	
	@Test(expected=IllegalValueException.class)
	public void addTask_emptyStringDescription() throws IllegalValueException {
		// Construction of the AddTaskCommand with an empty string should lead to an error
		AddTaskCommand command = new AddTaskCommand("");
	}
	
	@Test(expected=IllegalValueException.class)
	public void addTask_nullDescription() throws IllegalValueException {
		// Construction of the AddTaskCommand with null reference should lead to an error
		AddTaskCommand command = new AddTaskCommand(null);
	}

}

package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.taskcommands.DeleteTaskCommand;
import seedu.address.model.task.InMemoryTaskList;
import seedu.address.testutil.TestUtil;

public class DeleteTaskCommandTest {

	
	
	@Test
	public void deleteTask_noTasksAdded() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * there are no tasks that have been added).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupEmptyTaskList();
		DeleteTaskCommand command = new DeleteTaskCommand(1);
		command.setData(model);
				
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void deleteTask_indexTooLarge() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * index is too large).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupFloatingTasks(3);
		DeleteTaskCommand command = new DeleteTaskCommand(4);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void deleteTask_indexTooSmall() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since 
		 * index is too small).
		 */
		InMemoryTaskList model;
		model = TestUtil.setupFloatingTasks(3);
		DeleteTaskCommand command = new DeleteTaskCommand(-1);
		command.setData(model);
		
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertCommandFeedback(command, expected);
	}
	
	@Test
	public void deleteTask_validIndex() throws IllegalValueException {
		/* 
		 * CommandResult should return a string that denotes success in execution if index given 
		 * to DeleteTaskCommand constructor is within the range of added tasks.
		 */
		InMemoryTaskList model;
		model = TestUtil.setupFloatingTasks(3);
		DeleteTaskCommand command = new DeleteTaskCommand(2);
		command.setData(model);
		
		String expected = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, "[Floating Task][Description: Task 1]");
		assertCommandFeedback(command, expected);
	}


	/*
	 * Given a command and an expected string, execute the command
	 * and assert that the feedback corresponds to the expected string
	 */
	public void assertCommandFeedback(DeleteTaskCommand command, String expected) {
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(expected));
	}
}

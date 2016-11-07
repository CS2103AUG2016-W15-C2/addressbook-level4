package w15c2.tusk.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import w15c2.tusk.commons.core.Messages;
import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.logic.commands.taskcommands.DeleteTaskCommand;
import w15c2.tusk.model.Model;
import w15c2.tusk.testutil.TestUtil;

//@@author A0139817U
public class DeleteTaskCommandTest {
	
	// Initialized to support the tests
	Model model;
	
	@Test
	public void execute_noTasksAdded() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * there are no tasks that have been added).
		 */
		model = TestUtil.setupEmptyTaskList();
		
		CommandResult result = createAndExecuteDelete(1);
		String feedback = result.feedbackToUser;
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertEquals(feedback, expected);
	}
	
	@Test
	public void execute_indexTooLarge() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since
		 * index is too large).
		 */
		model = TestUtil.setupFloatingTasks(3);
		
		CommandResult result = createAndExecuteDelete(4);
		String feedback = result.feedbackToUser;
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertEquals(feedback, expected);
	}
	
	@Test
	public void execute_indexTooSmall() throws IllegalValueException {
		/*
		 * CommandResult should return a string that denotes that execution failed (since 
		 * index is too small).
		 */
		model = TestUtil.setupFloatingTasks(3);
		
		CommandResult result = createAndExecuteDelete(-1);
		String feedback = result.feedbackToUser;
		String expected = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		assertEquals(feedback, expected);
	}
	
	@Test
	public void execute_validIndex() throws IllegalValueException {
		/* 
		 * CommandResult should return a string that denotes success in execution if index given 
		 * to DeleteTaskCommand constructor is within the range of added tasks.
		 */
		model = TestUtil.setupFloatingTasks(3);
		
		CommandResult result = createAndExecuteDelete(2);
		String feedback = result.feedbackToUser;
		String expected = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, "Task 1");
		assertEquals(feedback, expected);
	}


	/**
	 * Utility functions
	 */
	// Create and execute DeleteTaskCommand
	public CommandResult createAndExecuteDelete(int index) {
		DeleteTaskCommand command = new DeleteTaskCommand(index);
		command.setData(model);
		return command.execute();
	}
}

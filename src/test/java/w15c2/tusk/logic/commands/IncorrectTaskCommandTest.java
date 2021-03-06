package w15c2.tusk.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import w15c2.tusk.commons.core.Messages;
import w15c2.tusk.logic.commands.CommandResult;
//@@author A0143107U
/**
 * Tests IncorrectTask Command 
 */
public class IncorrectTaskCommandTest {
	@Test
	public void incorrectTask_validFeedback() {
		/* CommandResult should return a string that denotes success in execution if description 
		 * given to AddTaskCommand constructor is a string with size > 0
		 */
		IncorrectCommand command = new IncorrectCommand(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(Messages.MESSAGE_INVALID_COMMAND_FORMAT));
	}
}

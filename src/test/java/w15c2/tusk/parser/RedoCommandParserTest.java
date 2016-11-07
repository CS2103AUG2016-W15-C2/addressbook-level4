package w15c2.tusk.parser;

import static org.junit.Assert.assertEquals;
import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.RedoTaskCommand;
import w15c2.tusk.logic.parser.RedoCommandParser;

//@@author A0143107U
/**
 * Tests Redo Command Parser
 */
public class RedoCommandParserTest {
	// Initialized to support the tests
	RedoCommandParser parser = new RedoCommandParser();
				
	/**
	 * Testing correct handling of invalid redo formats
	 */
	@Test
	public void prepareCommand_invalidFormat() {
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoTaskCommand.MESSAGE_USAGE);
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("previous");
		String feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("last");
		feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
	}
				
	/**
	 * Testing valid redo format
	 */
	@Test
	public void prepareCommand_validRedoFormat() {
		String expected = RedoTaskCommand.MESSAGE_REDO_TASK_SUCCESS;
		
		RedoTaskCommand command = (RedoTaskCommand) parser.prepareCommand("");
		String feedback = command.toString();
		assertEquals(feedback, expected);			
	}	
}

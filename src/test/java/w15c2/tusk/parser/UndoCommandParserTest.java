package w15c2.tusk.parser;

import static org.junit.Assert.assertEquals;
import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.UndoTaskCommand;
import w15c2.tusk.logic.parser.UndoCommandParser;

/**
 * Tests Undo Command Parser
 */
//@@author A0143107U
public class UndoCommandParserTest {
	// Initialized to support the tests
	UndoCommandParser parser = new UndoCommandParser();
				
	/**
	 * Testing correct handling of invalid undo formats
	 */
	@Test
	public void prepareCommand_invalidFormat() {
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoTaskCommand.MESSAGE_USAGE);
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("previous");
		String feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("last");
		feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
	}
				
	/**
	 * Testing valid undo format
	 */
	@Test
	public void prepareCommand_validUndoFormat() {
		String expected = UndoTaskCommand.MESSAGE_UNDO_TASK_SUCCESS;
		
		UndoTaskCommand command = (UndoTaskCommand) parser.prepareCommand("");
		String feedback = command.toString();
		assertEquals(feedback, expected);			
	}	
}

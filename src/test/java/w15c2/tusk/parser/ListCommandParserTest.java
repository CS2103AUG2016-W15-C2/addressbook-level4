package w15c2.tusk.parser;

import static org.junit.Assert.assertEquals;
import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import w15c2.tusk.logic.commands.IncorrectCommand;
import w15c2.tusk.logic.commands.taskcommands.ListTaskCommand;
import w15c2.tusk.logic.parser.ListCommandParser;
//@@author A0143107U
/**
 * Tests List Command Parser
 */
public class ListCommandParserTest {
	// Initialized to support the tests
	ListCommandParser parser = new ListCommandParser();
		
	/**
	 * Testing correct handling of invalid formats
	 */
	@Test
	public void prepareCommand_invalidFormat() {
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListTaskCommand.MESSAGE_USAGE);
		
		IncorrectCommand command = (IncorrectCommand) parser.prepareCommand("meeting");
		String feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
			
		command = (IncorrectCommand) parser.prepareCommand("all");
		feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
	}
	/**
	 * Testing correct format of list alias type
	 */
	@Test
	public void prepareCommand_listAlias() {
		String expected = "alias";
		
		ListTaskCommand command = (ListTaskCommand) parser.prepareCommand("alias");
		String feedback = command.getType();
		assertEquals(feedback, expected);
		
	}
		
	/**
	 * Testing correct format of list completed type
	 */
	@Test
	public void prepareCommand_listCompleted() {
		String expected = "completed";
		
		ListTaskCommand command = (ListTaskCommand) parser.prepareCommand("completed");
		String feedback = command.getType();
		assertEquals(feedback, expected);
		
		expected = "complete";
		
		command = (ListTaskCommand) parser.prepareCommand("complete");
		feedback = command.getType();
		assertEquals(feedback, expected);
		
	}
	
	/**
	 * Testing correct format of list all type
	 */
	@Test
	public void prepareCommand_listAll() {
		String expected = "";
		
		ListTaskCommand command = (ListTaskCommand) parser.prepareCommand("");
		String feedback = command.getType();
		assertEquals(feedback, expected);
		
	}
}

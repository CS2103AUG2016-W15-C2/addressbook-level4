package w15c2.tusk.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.UnpinTaskCommand;
import w15c2.tusk.logic.parser.UnpinCommandParser;
//@@author A0143107U
/**
 * Tests Unpin Command Parser
 */
public class UnpinCommandParserTest {
	// Initialized to support the tests
	UnpinCommandParser parser = new UnpinCommandParser();
	
	/**
	 * Testing correct handling of invalid indices
	 * 
	 * Invalid EPs: Indices, Formats
	 */
	@Test
	public void prepareCommand_invalidIndex() {
		/*
		 * Testing correct handling of indices
		 * 
		 * Incorrect indices EPs: Negative values, zero
		 */
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinTaskCommand.MESSAGE_USAGE);
		
		// EP: Negative value
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("-1");
		String actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("-6");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Zero
		command = (IncorrectTaskCommand) parser.prepareCommand("0");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
	}
	
	@Test
	public void prepareCommand_invalidFormat() {
		/*
		 * Testing correct handling of formats
		 * 
		 * Incorrect formats EPs: Non-integers, multiple integers, spaces, empty string
		 */
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinTaskCommand.MESSAGE_USAGE);
		
		// EP: Non-integers
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("hello world");
		String actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("hello");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Multiple integers
		command = (IncorrectTaskCommand) parser.prepareCommand("123 123");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Spaces
		command = (IncorrectTaskCommand) parser.prepareCommand(" ");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
		
		// EP: Empty string
		command = (IncorrectTaskCommand) parser.prepareCommand("");
		actual = command.feedbackToUser;
		assertEquals(actual, expected);
	}
	
	/**
	 * Testing parsing of valid indices
	 */
	@Test
	public void prepareCommand_validIndex() {		
		UnpinTaskCommand command = (UnpinTaskCommand) parser.prepareCommand("1");
		assertTrue(command.targetIndex == 1);
		
		command = (UnpinTaskCommand) parser.prepareCommand("1000");
		assertTrue(command.targetIndex == 1000);
	}
}

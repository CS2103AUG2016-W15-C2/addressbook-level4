package w15c2.tusk.parser;

import static org.junit.Assert.assertEquals;
import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import w15c2.tusk.logic.commands.taskcommands.HelpTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.parser.HelpCommandParser;

/**
 * Tests Help Command Parser
 */
//@@author A0143107U
public class HelpCommandParserTest {
	// Initialized to support the tests
	HelpCommandParser parser = new HelpCommandParser();
	
	/**
	 * Testing correct handling of invalid formats
	 * 
	 */
	@Test
	public void prepareCommand_invalidFormat() {
		/*
		 * Testing correct handling of non-empty strings
		 * 
		 */
		String expected = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpTaskCommand.MESSAGE_USAGE);
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("listing");
		String feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("all");
		feedback = command.feedbackToUser;
		assertEquals(feedback, expected);
	}
	
	/**
	 * Testing valid help format
	 */
	@Test
	public void prepareCommand_validHelpFormat() {
		String expected = HelpTaskCommand.SHOWING_HELP_MESSAGE;
		
		HelpTaskCommand command = (HelpTaskCommand) parser.prepareCommand("");
		String feedback = command.toString();
		assertEquals(feedback, expected);
		
	}
}

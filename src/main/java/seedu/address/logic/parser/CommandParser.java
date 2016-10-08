package seedu.address.logic.parser;

import seedu.address.logic.commands.taskcommands.TaskCommand;

/*
 * Defines a generic parser for commands
 */
public abstract class CommandParser {
	
	public abstract TaskCommand prepareCommand(String arguments);

}

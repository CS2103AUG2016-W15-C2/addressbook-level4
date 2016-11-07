package w15c2.tusk.logic.parser;

import static w15c2.tusk.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import w15c2.tusk.commons.util.StringUtil;
import w15c2.tusk.logic.commands.taskcommands.IncorrectTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.TaskCommand;
import w15c2.tusk.logic.commands.taskcommands.UnpinTaskCommand;

//@@author A0138978E
/*
 * Parses Unpin commands
 */
public class UnpinCommandParser extends CommandParser{
	public static final String COMMAND_WORD = UnpinTaskCommand.COMMAND_WORD;
    public static final String ALTERNATE_COMMAND_WORD = UnpinTaskCommand.ALTERNATE_COMMAND_WORD;

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

	/**
     * Parses arguments in the context of the Unpin task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	public TaskCommand prepareCommand(String arguments) {
		 Optional<Integer> index = parseIndex(arguments);
	        if(!index.isPresent()){
	            return new IncorrectTaskCommand(
	                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinTaskCommand.MESSAGE_USAGE));
	        }

	        return new UnpinTaskCommand(index.get());
	}
	
	/**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     * Returns an {@code Optional.empty()} otherwise.
     * @param commmand arguments to parse
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }
}

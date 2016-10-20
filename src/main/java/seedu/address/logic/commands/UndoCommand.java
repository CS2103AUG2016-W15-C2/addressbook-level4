package seedu.address.logic.commands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.taskcommands.TaskCommand;

/**
 * Lists all tasks in the TaskManager to the user.
 */
public class UndoCommand extends TaskCommand {
    
    public static final String COMMAND_WORD = "undo";
    
    public static final String MESSAGE_UNDO_TASK_SUCCESS = "Undo successful";
    public static final String MESSAGE_UNDO_INVALID_STATE = "Undo is not successful because there is no previous command";
    
    public UndoCommand() {}

    @Override
    public CommandResult execute() {
    	try {
    		model.undo();
    	} catch (IllegalStateException ise) {
    		return new CommandResult(MESSAGE_UNDO_INVALID_STATE);
    	}
    	return new CommandResult(MESSAGE_UNDO_TASK_SUCCESS);
    }
}

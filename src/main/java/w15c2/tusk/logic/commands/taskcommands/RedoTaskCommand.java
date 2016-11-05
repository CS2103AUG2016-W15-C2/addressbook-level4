package w15c2.tusk.logic.commands.taskcommands;

import w15c2.tusk.logic.commands.CommandResult;

/**
 * Lists all tasks in the TaskManager to the user.
 */
//@@author A0139817U
public class RedoTaskCommand extends TaskCommand {
    
    public static final String COMMAND_WORD = "redo";
    public static final String ALTERNATE_COMMAND_WORD = null;
    public static final String COMMAND_FORMAT = COMMAND_WORD;
    public static final String COMMAND_DESCRIPTION = "Redo an Undo"; 
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": \t redoes previous command.";


    public static final String MESSAGE_REDO_TASK_SUCCESS = "Redo successful";
    public static final String MESSAGE_REDO_INVALID_STATE = "Redo is not successful because the previous successful command is not undo";
    
    public RedoTaskCommand() {}

    @Override
    public CommandResult execute() {
    	try {
    		model.redo();
    	} catch (IllegalStateException ise) {
    		return new CommandResult(MESSAGE_REDO_INVALID_STATE);
    	}
    	return new CommandResult(MESSAGE_REDO_TASK_SUCCESS);
    }
    
    @Override
    public String toString(){
        return MESSAGE_REDO_TASK_SUCCESS;
    }
}

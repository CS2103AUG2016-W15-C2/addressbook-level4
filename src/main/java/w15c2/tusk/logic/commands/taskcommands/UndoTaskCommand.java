package w15c2.tusk.logic.commands.taskcommands;

import w15c2.tusk.commons.core.EventsCenter;
import w15c2.tusk.commons.events.ui.HideHelpRequestEvent;
import w15c2.tusk.logic.commands.CommandResult;

/**
 * Lists all tasks in the TaskManager to the user.
 */
//@@author A0139817U
public class UndoTaskCommand extends TaskCommand {
    
    public static final String COMMAND_WORD = "undo";
    public static final String ALTERNATE_COMMAND_WORD = null;
    public static final String COMMAND_FORMAT = COMMAND_WORD;
    public static final String COMMAND_DESCRIPTION = "Undo a Command"; 
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": \t undoes previous command.";

    
    public static final String MESSAGE_UNDO_TASK_SUCCESS = "Undo successful";
    public static final String MESSAGE_UNDO_INVALID_STATE = "Undo is not successful because there is no previous command";
    
    public UndoTaskCommand() {}

    @Override
    public CommandResult execute() {
    	try {
    		model.undo();
    	} catch (IllegalStateException ise) {
    		return new CommandResult(MESSAGE_UNDO_INVALID_STATE);
    	}
    	EventsCenter.getInstance().post(new HideHelpRequestEvent());
    	return new CommandResult(MESSAGE_UNDO_TASK_SUCCESS);
    }
    
    @Override
    public String toString(){
        return MESSAGE_UNDO_TASK_SUCCESS;
    }
}

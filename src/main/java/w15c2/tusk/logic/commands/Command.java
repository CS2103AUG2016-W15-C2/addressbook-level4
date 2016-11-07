package w15c2.tusk.logic.commands;

import w15c2.tusk.commons.core.EventsCenter;
import w15c2.tusk.commons.core.Messages;
import w15c2.tusk.commons.events.ui.HideHelpRequestEvent;
import w15c2.tusk.commons.events.ui.IncorrectTaskCommandAttemptedEvent;
import w15c2.tusk.model.Model;

//@@author A0138978E
/**
 * Represents a Task command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
	protected Model model;
	
    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }
	
    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult execute();

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }
    
    /**
     * Raises an event to close the help window
     */
    protected void closeHelpWindow() {
        EventsCenter.getInstance().post(new HideHelpRequestEvent());
    }

    /**
     * Raises an event to indicate an attempt to execute an incorrect command
     */
    protected void indicateAttemptToExecuteIncorrectCommand() {
        EventsCenter.getInstance().post(new IncorrectTaskCommandAttemptedEvent(this));
    }
}

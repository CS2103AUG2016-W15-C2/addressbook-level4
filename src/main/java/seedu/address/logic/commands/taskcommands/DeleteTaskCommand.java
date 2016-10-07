package seedu.address.logic.commands.taskcommands;

import javafx.collections.ObservableList;
import seedu.address.commons.collections.UniqueItemCollection.ItemNotFoundException;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.task.Task;

/**
 * Deletes a task identified using it's last displayed index from TaskManager.
 */
public class DeleteTaskCommand extends TaskCommand {

	  public static final String COMMAND_WORD = "delete";

	    public static final String MESSAGE_USAGE = COMMAND_WORD
	            + ": Deletes the task identified by the index number used in the last task listing.\n"
	            + "Parameters: INDEX (must be a positive integer)\n"
	            + "Example: " + COMMAND_WORD + " 1";

	    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

	    public final int targetIndex;

	    public DeleteTaskCommand(int targetIndex) {
	        this.targetIndex = targetIndex;
	    }


	    @Override
	    public CommandResult execute() {

	        ObservableList<Task> lastShownList = model.getCurrentFilteredTasks();

	        if (lastShownList.size() < targetIndex) {
	            indicateAttemptToExecuteIncorrectCommand();
	            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	        }

	        Task taskToDelete = lastShownList.get(targetIndex - 1);

	        try {
	            model.deleteTask(taskToDelete);
	            model.clearTasksFilter();
	        } catch (ItemNotFoundException tnfe) {
	            assert false : "The target item cannot be missing";
	        }

	        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
	    }

}

package seedu.address.logic.commands.taskcommands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.task.Task;

/**
 * Unfavorites a task identified using it's last displayed index from TaskManager.
 */
public class UncompleteTaskCommand extends TaskCommand {

	public static final String COMMAND_WORD = "uncomplete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Uncompletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNCOMPLETE_TASK_SUCCESS = "Uncompleted task: %1$s";

    public final int targetIndex;

    public UncompleteTaskCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

	    ObservableList<Task> lastShownList = model.getCurrentFilteredTasks();

        if (lastShownList.size() < targetIndex || targetIndex <= 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToUncomplete = lastShownList.get(targetIndex - 1);
        model.uncompleteTask(taskToUncomplete);
        model.refreshTasksFilter();

        return new CommandResult(String.format(MESSAGE_UNCOMPLETE_TASK_SUCCESS, taskToUncomplete));
    }

}
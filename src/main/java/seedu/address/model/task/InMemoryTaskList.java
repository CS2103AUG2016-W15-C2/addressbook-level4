package seedu.address.model.task;

import java.util.Set;

import seedu.address.commons.collections.UniqueItemCollection.DuplicateItemException;
import seedu.address.commons.collections.UniqueItemCollection.ItemNotFoundException;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.Alias;

/*
 * Represents an in-memory task list
 */
public interface InMemoryTaskList {

	/*
	 * Adds a task to the current in-memory representation of the Task List
	 */
	void addTask(Task toAdd) throws DuplicateItemException;
	
	/*
	 * Updates a task with a new task from the current in-memory representation of the Task List
	 */
	void updateTask(Task toAdd, Task newTask) throws ItemNotFoundException, DuplicateItemException;
	
	/*
	 * Removes a task from the current in-memory representation of the Task List
	 */
	void deleteTask(Task toRemove) throws ItemNotFoundException;
	
	/*
	 * Favorites a task in the current in-memory representation of the Task List
	 */
	void favoriteTask(Task toFavorite);
	
	/*
	 * Unfavorites a task in the current in-memory representation of the Task List
	 */
	void unfavoriteTask(Task toFavorite);
	
	/*
	 * Adds a set of keywords to filter the task list by
	 */
	void filterTasks(Set<String> keywords);
	
	/*
	 * Clears the filtering keywords applied to the tasks
	 */
	void clearTasksFilter();
	
	/*
	 * Gets the current list of tasks with the filtering words applied
	 */
	UnmodifiableObservableList<Task> getCurrentFilteredTasks();
	
	/*
	 * Adds a one-word alias for any sentence to be used as a command.
	 */
	void addAlias(Alias toAdd) throws DuplicateItemException;
	
	/*
	 * Removes an alias from the current in-memory representation of the Alias List
	 */
	void deleteAlias(Alias toRemove) throws ItemNotFoundException;
	
	/*
	 * Undoes the previous command if it were a valid command
	 */
	void undo() throws IllegalStateException;
	
	/*
	 * Gets the list of alias
	 */
	UnmodifiableObservableList<Alias> getAlias();

	
	
}

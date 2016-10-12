package seedu.address.model.task;

import java.util.Set;

import seedu.address.commons.collections.UniqueItemCollection;
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
	void addTask(Task toAdd) throws UniqueItemCollection.DuplicateItemException;
	
	/*
	 * Removes a task from the current in-memory representation of the Task List
	 */
	void deleteTask(Task toRemove) throws UniqueItemCollection.ItemNotFoundException;
	
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
	void addAlias(Alias toAdd) throws UniqueItemCollection.DuplicateItemException;
	
	/*
	 * Removes an alias from the current in-memory representation of the Alias List
	 */
	void deleteAlias(Alias toRemove) throws ItemNotFoundException;
	
	/*
	 * Gets the list of alias
	 */
	UnmodifiableObservableList<Alias> getAlias();

	
	
}

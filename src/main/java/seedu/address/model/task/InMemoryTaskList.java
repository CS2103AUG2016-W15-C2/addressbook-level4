package seedu.address.model.task;

import java.util.Set;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.core.UnmodifiableObservableList;

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
	
}

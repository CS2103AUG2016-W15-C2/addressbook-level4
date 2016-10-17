package seedu.address.model.task;

import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.collections.UniqueItemCollection.DuplicateItemException;
import seedu.address.commons.collections.UniqueItemCollection.ItemNotFoundException;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.model.AliasChangedEvent;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.Alias;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;

/*
 * Manages a list of tasks 1and acts as a gateway for Commands to perform CRUD operations on the list
 */
public class TaskManager extends ComponentManager implements InMemoryTaskList {
	private UniqueItemCollection<Task> tasks;
	private UniqueItemCollection<Alias> aliases;
	
	// Collection to store the tasks / aliases after each command that changes tasks or aliases. ALlows user to undo.
	private UniqueItemCollection<Task> oldTasks;
	private UniqueItemCollection<Alias> oldAliases;
	
	// Collection to store the current tasks / aliases before an undo command. Allows user to redo.
	private UniqueItemCollection<Task> undoneTasks;
	private UniqueItemCollection<Alias> undoneAliases;
	
	private FilteredList<Task> filteredTasks;


	public TaskManager() {
		// TODO: make use of loaded data
		this.tasks = new UniqueItemCollection<Task>();
		this.aliases = new UniqueItemCollection<Alias>();
		filteredTasks = new FilteredList<>(tasks.getInternalList());
	}
	
	public TaskManager(UniqueItemCollection<Task> tasks, UniqueItemCollection<Alias> aliases, UserPrefs userPrefs) {
		this.tasks = tasks;
		this.aliases = aliases;
		filteredTasks = new FilteredList<>(this.tasks.getInternalList());
	}
	
	@Override
	public synchronized void addTask(Task toAdd) throws DuplicateItemException {
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();
		
		tasks.add(toAdd);
		
		// Update global copies of tasks
		updateTasks(tempTasks);
		indicateTaskManagerChanged();
	}
	
	@Override
	public synchronized void updateTask(Task toUpdate, Task newTask) throws ItemNotFoundException {
		assert tasks.contains(toUpdate);
		
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();
		
		tasks.replace(toUpdate, newTask);
		
		// Update global copies of tasks
		updateTasks(tempTasks);
		indicateTaskManagerChanged();
	}

	@Override
	public synchronized void deleteTask(Task toRemove) throws ItemNotFoundException {
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();
		
	    tasks.remove(toRemove);
	    
	    // Update global copies of tasks
	 	updateTasks(tempTasks);
	    indicateTaskManagerChanged();
	}
	
	@Override
	public void favoriteTask(Task toFavorite) {
		assert tasks.contains(toFavorite);
		
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();
		
		toFavorite.setAsFavorite();
		
		// Update global copies of tasks
		updateTasks(tempTasks);
		indicateTaskManagerChanged();
	}

	@Override
	public void unfavoriteTask(Task toUnfavorite) {
		assert tasks.contains(toUnfavorite);
		
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Task> tempTasks = tasks.copyCollection();
		
		toUnfavorite.setAsNotFavorite();
		
		// Update global copies of tasks
		updateTasks(tempTasks);
		indicateTaskManagerChanged();
	}
	
	/**
	 * Undoes the previous valid command.
	 */
	public void undo() throws IllegalStateException {
		// oldTasks and oldAliases can never be both present since only one will override the other
		assert oldTasks != null && oldAliases != null;
		
		if (oldTasks == null && oldAliases == null) {
			throw new IllegalStateException("Unable to undo because there is no previous state to revert to");
		}
		
		// Undoing task type command
		if (oldTasks != null) {
			// Before undoing, undoneTasks assigned the current values to allow user to redo
			undoneTasks = tasks;
			// Since no alias have been undone, undoneAliases set to null
			undoneAliases = null; 
			
			// The current tasks have been reinstated to their older versions
			tasks = oldTasks;
			
		// Undoing alias type command
		} else if (oldAliases != null) {
			// Before undoing, undoneAlises assigned the current values to allow user to redo
			undoneAliases = aliases;
			// Since no task have been undone, undoneTasks set to null
			undoneTasks = null; 
			
			// The current aliases have been reinstated to their older versions
			aliases = oldAliases;
		}
		
		// Can only undo once. Hence, oldTasks & oldAliases are set to null after one undo
		oldAliases = null;
		oldTasks = null;
		
		// Refresh the filtered tasks
		filteredTasks = new FilteredList<>(this.tasks.getInternalList());
		
		// Raise the changes
		indicateTaskManagerChanged();
		indicateAliasChanged();
	}
	
	/**
	 * Updates the old & undone copies of tasks & aliases when Tasks have been modified 
	 */
	public void updateTasks(UniqueItemCollection<Task> oldCopy) {
		// Set oldAliases to null to know that it is Tasks that have been modified
		oldTasks = oldCopy;
		oldAliases = null;
		
		// Since the current command is not redo, clear the undone tasks / aliases
		undoneTasks = null;
		undoneAliases = null;	
	}
	
	/**
	 * Updates the old & undone copies of tasks & aliases when Aliases have been modified
	 */
	public void updateAliases(UniqueItemCollection<Alias> oldCopy) {
		// Set oldTasks to null to know that it is Aliases that have been modified
		oldAliases = oldCopy;
		oldTasks = null;
		
		// Since the current command is not redo, clear the undone tasks / aliases
		undoneTasks = null;
		undoneAliases = null;
	}
	
    /** Keeps the internal ObservableList sorted.
     * Raises an event to indicate the model has changed.
     */
    private void indicateTaskManagerChanged() {
    	FXCollections.sort(tasks.getInternalList());
        raise(new TaskManagerChangedEvent(tasks));
    }

	@Override
	public void filterTasks(Set<String> keywords) {
	    filterTasks(new PredicateExpression(new NameQualifier(keywords)));
	}
	
	public void filterTasks(Expression expression) {
	    filteredTasks.setPredicate(expression::satisfies);
	}

	@Override
	public void clearTasksFilter() {
	    filteredTasks.setPredicate(null);
	}

	@Override
	public UnmodifiableObservableList<Task> getCurrentFilteredTasks() {
		return new UnmodifiableObservableList<>(filteredTasks);
	}
	
	@Override
	public void addAlias(Alias toAdd) throws DuplicateItemException{
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Alias> tempAliases = aliases.copyCollection();
		
		aliases.add(toAdd);
		
		// Update global copies of tasks
		updateAliases(tempAliases);
	    indicateAliasChanged();
	}
	
	@Override
	public synchronized void deleteAlias(Alias toRemove) throws ItemNotFoundException {
		// Create a temporary storage of tasks and update the global copy only when update is successful
		UniqueItemCollection<Alias> tempAliases = aliases.copyCollection();
		
	    aliases.remove(toRemove);
	    
	    // Update global copies of tasks
	 	updateAliases(tempAliases);
	    indicateAliasChanged();
	}
	
	/** Raises an event to indicate the model has changed */
    private void indicateAliasChanged() {
        raise(new AliasChangedEvent(aliases));
    }
    
    @Override
	public UnmodifiableObservableList<Alias> getAlias() {
		return new UnmodifiableObservableList<>(aliases.getInternalList());
	}
  	
	interface Expression {
        boolean satisfies(Task task);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(Task task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(Task task);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(Task task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.getDescription().getContent(), keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }



	
}
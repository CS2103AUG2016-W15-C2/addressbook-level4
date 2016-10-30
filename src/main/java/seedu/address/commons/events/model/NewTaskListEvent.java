package seedu.address.commons.events.model;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.Task;

/** 
 * Indicates the Task List have been replaced (due to undo/redo commands) 
 */
//@@author A0139817U
public class NewTaskListEvent extends BaseEvent {

	public final UniqueItemCollection<Task> newTasks;
    public final FilteredList<Task> filteredTasks;
    public final ArrayList<ObservableList<Task>> listOfLists;
    
    public NewTaskListEvent(UniqueItemCollection<Task> newTasks, FilteredList<Task> filteredTasks,
            ArrayList<ObservableList<Task>> listOfLists){
        this.newTasks = newTasks;
        this.filteredTasks = filteredTasks;
        this.listOfLists = listOfLists;
    }

    @Override
    public String toString() {
        return "number of tasks " + newTasks.getInternalList().size();
    }
}

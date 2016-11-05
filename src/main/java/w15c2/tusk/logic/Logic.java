package w15c2.tusk.logic;

import javafx.collections.ObservableList;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.model.Alias;
import w15c2.tusk.model.HelpGuide;
import w15c2.tusk.model.task.Task;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText);

    /** Returns the filtered list of tasks */ 
    ObservableList<Task> getFilteredTaskList();
    
    ObservableList<Alias> getAlias();
    
    ObservableList<HelpGuide> getHelpList();

    //@@author A0138978E
    /** Returns the previous command in the command history */
    String getPreviousCommand();
    
    /** Returns the next command in the command history */
    String getNextCommand();
    
    /** Sets the text that should be used as the source for autocomplete suggestions */
    void setTextToAutocomplete(String text);
    
    /** Uses the text set as the autcomplete source to return another autocomplete suggestion  */
    String getNextAutocompleteSuggestion();

}

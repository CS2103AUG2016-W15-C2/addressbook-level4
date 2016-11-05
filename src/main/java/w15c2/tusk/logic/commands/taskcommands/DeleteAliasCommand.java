package w15c2.tusk.logic.commands.taskcommands;

import javafx.collections.ObservableList;
import w15c2.tusk.commons.collections.UniqueItemCollection.ItemNotFoundException;
import w15c2.tusk.commons.core.EventsCenter;
import w15c2.tusk.commons.events.ui.HideHelpRequestEvent;
import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.model.Alias;

/**
 * Deletes an alias identified using it's shortcut.
 */
//@@author A0143107U
public class DeleteAliasCommand extends TaskCommand {

	public static final String COMMAND_WORD = "unalias";
    public static final String ALTERNATE_COMMAND_WORD = null;
    
    public static final String COMMAND_FORMAT = COMMAND_WORD + "<alias>";
    public static final String COMMAND_DESCRIPTION = "Delete an Alias"; 
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the shortcut of the alias.\n"
            + "Parameters: SHORTCUT\n"
            + "Example: " + COMMAND_WORD + " am";

    public static final String MESSAGE_DELETE_ALIAS_SUCCESS = "Deleted alias: %1$s";
    public static final String MESSAGE_ALIAS_NOT_FOUND = "No such alias found.";

    public final String shortcut;

    public DeleteAliasCommand(String shortcut) 
            throws IllegalValueException {
    	if(shortcut == null || shortcut.isEmpty()){
    		throw new IllegalValueException("Shortcut to DeleteAliasCommand constructor is empty.\n"+ MESSAGE_USAGE);
    	}
        this.shortcut = shortcut;
    }

    @Override
    public CommandResult execute() {
    	Alias aliasToDelete = new Alias();
	    ObservableList<Alias> aliasList = model.getAlias();
	    for(int i=0; i<aliasList.size(); i++){
	    	if(shortcut.equals(aliasList.get(i).getShortcut())){
	    		aliasToDelete = aliasList.get(i);
	    		break;
	    	}
	    }
	    try {
	        model.deleteAlias(aliasToDelete);   
	        closeHelpWindow();
	        return new CommandResult(String.format(MESSAGE_DELETE_ALIAS_SUCCESS, aliasToDelete));
	    } catch (ItemNotFoundException tnfe) {
        	return new CommandResult(MESSAGE_ALIAS_NOT_FOUND);
	    }

    }

}

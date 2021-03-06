package w15c2.tusk.logic.commands;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Logger;

import w15c2.tusk.commons.core.LogsCenter;
import w15c2.tusk.logic.commands.taskcommands.AddTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.ClearTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.CompleteTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.DeleteTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.FindTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.ListTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.PinTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.UncompleteTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.UnpinTaskCommand;
import w15c2.tusk.logic.commands.taskcommands.UpdateTaskCommand;
import w15c2.tusk.model.HelpGuide;

//@@author A0139708W
/**
 * Container for Commands to create help information
 */
public class CommandList {
    private static final Logger logger = LogsCenter.getLogger(CommandList.class);
    
    /**
     * List of all available task commands.
     * 
     * @return  list of task command classes.
     */
    private static Class<?>[] getList(){
        
        return new Class[]{
        		HelpCommand.class,
                AddTaskCommand.class,
                FindTaskCommand.class,
                UpdateTaskCommand.class, 
                DeleteTaskCommand.class,
                ClearTaskCommand.class,
                UndoCommand.class,
                RedoCommand.class,
                CompleteTaskCommand.class,
                UncompleteTaskCommand.class,
                ListTaskCommand.class,
                PinTaskCommand.class,
                UnpinTaskCommand.class,
                AddAliasCommand.class, 
                DeleteAliasCommand.class,
                SetStorageCommand.class, 
                ExitCommand.class
        };
    }
    
    /**
     * Returns help information for all task
     * commands from class list.
     * 
     * @return  List of help information.
     */
    public static ArrayList<HelpGuide> getHelpList() {
        ArrayList<HelpGuide> helpGuideList = new ArrayList<HelpGuide>();
        for(Class<?> taskCommand : getList()) {
            try {
                Field argumentsField = taskCommand.getField("COMMAND_FORMAT");
                String arguments = (String) argumentsField.get(null);
                Field commandDescriptionField = taskCommand.getField("COMMAND_DESCRIPTION");
                String commandDescription = (String) commandDescriptionField.get(null);
                helpGuideList.add(new HelpGuide(commandDescription, arguments));

            } catch (NoSuchFieldException e) {
                logger.severe("Error: Non-command class placed into list");
                e.printStackTrace();
            } catch (Exception e) {
                logger.severe("Exception encountered");
                e.printStackTrace();
            }
        }
        return helpGuideList;
        
    }

}

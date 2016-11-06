package w15c2.tusk.logic.commands.taskcommands;

import w15c2.tusk.commons.core.EventsCenter;
import w15c2.tusk.commons.events.ui.ShowHelpRequestEvent;
import w15c2.tusk.logic.commands.CommandResult;

//@@author A0139708W
/*
 * Shows Help for Commands
 */
public class HelpTaskCommand extends TaskCommand {
    public static final String COMMAND_WORD = "help";
    public static final String ALTERNATE_COMMAND_WORD = null;
    public static final String COMMAND_FORMAT = COMMAND_WORD;
    public static final String COMMAND_DESCRIPTION = "Help"; 

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help panel.";

    public HelpTaskCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }

    @Override
    public String toString(){
        return SHOWING_HELP_MESSAGE;
    }
}

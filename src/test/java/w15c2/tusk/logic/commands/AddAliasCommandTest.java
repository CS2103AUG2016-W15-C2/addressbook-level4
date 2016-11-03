package w15c2.tusk.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.logic.commands.taskcommands.AddAliasCommand;
import w15c2.tusk.model.task.Model;
import w15c2.tusk.model.task.TaskManager;

//@@author A0143107U
public class AddAliasCommandTest {

	// Initialized to support the tests
	Model model;

	@Before
	public void setup() {
		model = new TaskManager();
	}
	
	@Test
	public void addAlias_validShortcut_validSentence() throws IllegalValueException {
		/* CommandResult should return a string that denotes success in execution if description 
		 * given to AddAliasCommand constructor is a string with size > 0
		 */
		AddAliasCommand command = new AddAliasCommand("am", "add meeting");
		command.setData(model);
		CommandResult result = command.execute();
		String feedback = result.feedbackToUser;
		assertTrue(feedback.equals(String.format(AddAliasCommand.MESSAGE_SUCCESS, "am add meeting")));
	}
	
	@Test(expected=IllegalValueException.class)
	public void addAlias_emptyStringShortcut_emptyStringSentence() throws IllegalValueException {
		// Construction of the AddAliasCommand with an empty strings should lead to an error
		new AddAliasCommand("", "");
	}
	
	@Test(expected=IllegalValueException.class)
	public void addAlias_nullShortcut_nullSentence() throws IllegalValueException {
		// Construction of the AddAliasCommand with null shortcut and null sentence should lead to an error
		new AddAliasCommand(null, null);
	}
	
	@Test(expected=IllegalValueException.class)
	public void addAlias_validShortcut_nullSentence() throws IllegalValueException {
		// Construction of the AddAliasCommand with valid shortcut but null sentence should lead to an error
		new AddAliasCommand("am", null);
	}

}

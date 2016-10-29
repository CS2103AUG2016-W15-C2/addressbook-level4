package seedu.address.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import java.util.Calendar;
import java.util.Date;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.util.DateUtil;
import seedu.address.logic.commands.taskcommands.IncorrectTaskCommand;
import seedu.address.logic.commands.taskcommands.UpdateTaskCommand;
import seedu.address.logic.parser.UpdateCommandParser;

//@@author A0139817U
public class UpdateCommandParserTest {
	/**
	 * Format of update command: update INDEX task/description/date(update type) UPDATED_VALUE
	 */
	// Initialized to support the tests
	UpdateCommandParser parser = new UpdateCommandParser();


	/**
	 * Testing correct handling of invalid formats, indices, update types, or dates
	 * 
	 * Invalid EPs: Formats, Indices, Update types, Dates
	 */
	@Test
	public void prepareCommand_invalidFormat() {
		/*
		 * Testing correct handling of invalid formats
		 */
		String expectedTask = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateTaskCommand.MESSAGE_USAGE);
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("1taskworkshop");
		String actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("1task workshop");
		actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_invalidIndex() {
		/*
		 * Testing correct handling of invalid indices
		 */
		String expectedTask = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("-1 task workshop");
		String actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("a task workshop");
		actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_invalidUpdateType() {
		/*
		 * Testing correct handling of invalid update types
		 */
		String expectedTask = UpdateCommandParser.MESSAGE_INVALID_UPDATE_TYPE;
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("2 tasks workshop");
		String actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("3 descriptio workshop");
		actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("3 dat oct 1 to oct 2");
		actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("3 random oct 1 to oct 2");
		actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_invalidDate() {
		/*
		 * Testing correct handling of invalid dates
		 */
		String expectedTask = MESSAGE_INVALID_DATE_FORMAT;
		
		IncorrectTaskCommand command = (IncorrectTaskCommand) parser.prepareCommand("2 date octo 1");
		String actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("3 date oct 1 t oct 2");
		actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("3 date oct 1 to oct 32");
		actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
		
		command = (IncorrectTaskCommand) parser.prepareCommand("3 date some words");
		actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
		
		// End date should not be earlier than start date
		command = (IncorrectTaskCommand) parser.prepareCommand("3 date today 1pm to 1am");
		actualTask = command.feedbackToUser;
		assertEquals(actualTask, expectedTask);
	}
	
	/**
	 * Testing correct updating of tasks, description or date
	 * 
	 * Valid EPs: Task, Description, Date
	 */
	
	/**
	 * Testing correct updating of tasks
	 */
	@Test
	public void prepareCommand_updateTask() {
		// No time specified for dates
		UpdateTaskCommand command = (UpdateTaskCommand) parser.prepareCommand("1 task workshop");
		String expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_TASK, 
				"[Floating Task][Description: workshop]");
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (UpdateTaskCommand) parser.prepareCommand("1 task homework by oct 1");
		expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_TASK, 
				"[Deadline Task][Description: homework][Deadline: 01.10.2016]");
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (UpdateTaskCommand) parser.prepareCommand("1 task overseas from oct 1 to oct 2");
		expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_TASK, 
				"[Event Task][Description: overseas][Start date: 01.10.2016][End date: 02.10.2016]");
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		// Time specified for dates
		command = (UpdateTaskCommand) parser.prepareCommand("1 task homework by oct 1 5.10pm");
		expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_TASK, 
				"[Deadline Task][Description: homework][Deadline: 01.10.2016 05:10PM]");
		actualTask = command.getTaskDetails(true);
		assertEquals(actualTask, expectedTask);
		
		command = (UpdateTaskCommand) parser.prepareCommand("1 task overseas from oct 1 5.10pm to oct 2 6.25pm");
		expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_TASK, 
				"[Event Task][Description: overseas][Start date: 01.10.2016 05:10PM][End date: 02.10.2016 06:25PM]");
		actualTask = command.getTaskDetails(true);
		assertEquals(actualTask, expectedTask);
		
		Date firstDate = DateUtil.createDateAfterToday(1, 17, 10);  // Tomorrow 5.10pm
		Date secondDate = DateUtil.createDateAfterToday(2, 18, 25); // The day after tomorrow 6.25pm
		command = (UpdateTaskCommand) parser.prepareCommand("1 task overseas from tmr 5.10pm to the day after tmr 6.25pm");
		String eventTask = String.format("[Event Task][Description: overseas][Start date: %s][End date: %s]",
				DateUtil.dateFormatWithTime.format(firstDate), DateUtil.dateFormatWithTime.format(secondDate));
		expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_TASK, eventTask);
		actualTask = command.getTaskDetails(true);
		assertEquals(actualTask, expectedTask);
	}
	
	/**
	 * Testing correct updating of description
	 */
	@Test
	public void prepareCommand_updateDescription() {
		UpdateTaskCommand command = (UpdateTaskCommand) parser.prepareCommand("1 description workshop");
		String expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_DESCRIPTION, 
				"workshop");
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (UpdateTaskCommand) parser.prepareCommand("1 description homework by oct 1");
		expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_DESCRIPTION, 
				"homework by oct 1");
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (UpdateTaskCommand) parser.prepareCommand("1 description homework from oct 1 to oct 2");
		expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_DESCRIPTION, 
				"homework from oct 1 to oct 2");
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
	}
	
	/**
	 * Testing correct updating of dates of tasks 
	 */
	@Test
	public void prepareCommand_updateDate_deadline() {
		/*
		 * Updating deadlines
		 */
		String expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_DEADLINE, 
				"01.10.2016");
		
		// No time specified for dates
		UpdateTaskCommand command = (UpdateTaskCommand) parser.prepareCommand("1 date oct 1");
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (UpdateTaskCommand) parser.prepareCommand("1 date OcTobeR 1 2016");
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		// Time specified for dates
		command = (UpdateTaskCommand) parser.prepareCommand("1 date 2.30am Oct 1 2016");
		expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_DEADLINE, 
				"01.10.2016 02:30AM");
		actualTask = command.getTaskDetails(true);
		assertEquals(actualTask, expectedTask);
		
		Date firstDate = DateUtil.createDateAfterToday(1, 17, 10);
		command = (UpdateTaskCommand) parser.prepareCommand("1 date tmr 5.10pm");
		expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_DEADLINE, 
				DateUtil.dateFormatWithTime.format(firstDate.getTime()));
		actualTask = command.getTaskDetails(true);
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_updateDate_startEndDate() {
		/*
		 * Updating start dates and end dates
		 */
		String expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_START_END_DATE, 
				"01.10.2016", "02.10.2016");
		
		// No time specified for dates
		UpdateTaskCommand command = (UpdateTaskCommand) parser.prepareCommand("1 date oct 1 to oct 2");
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (UpdateTaskCommand) parser.prepareCommand("1 date from oct 1 to oct 2");
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		// Time specified for dates
		command = (UpdateTaskCommand) parser.prepareCommand("1 date from 9.30am oct 1 to 8am oct 2");
		expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_START_END_DATE, 
				"01.10.2016 09:30AM", "02.10.2016 08:00AM");
		actualTask = command.getTaskDetails(true);
		assertEquals(actualTask, expectedTask);
		
		Date firstDate = DateUtil.createDateAfterToday(1, 17, 10); // Tomorrow 5.10pm
		Date secondDate = DateUtil.createDateAfterToday(2, 18, 25); // The day after tomorrow 6.25pm
		command = (UpdateTaskCommand) parser.prepareCommand("1 date tmr 5.10pm to the day after tmr 6.25pm");
		expectedTask = String.format(UpdateTaskCommand.TASK_DETAILS_UPDATE_START_END_DATE, 
				DateUtil.dateFormatWithTime.format(firstDate), DateUtil.dateFormatWithTime.format(secondDate));
		actualTask = command.getTaskDetails(true);
		assertEquals(actualTask, expectedTask);
	}
}
package w15c2.tusk.parser;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import w15c2.tusk.commons.util.DateUtil;
import w15c2.tusk.logic.commands.taskcommands.AddTaskCommand;
import w15c2.tusk.logic.parser.AddCommandParser;

//@@author A0139817U
public class AddCommandParserTest {

	// Initialized to support the tests
	AddCommandParser parser = new AddCommandParser();
	
	/**
	 * Testing situations in which user intends to create Floating Tasks.
	 */
	@Test
	public void prepareCommand_floatingTask_successful() {
		/*
		 * Normal add Floating task command
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("meeting");
		String expectedTask = "[Floating Task][Description: meeting]";
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
	}
	
	/**
	 * Testing situations in which user intends to create Deadline Tasks.
	 */
	@Test
	public void prepareCommand_deadlineTask_successful() {
		/*
		 * Normal add Deadline task command
		 */
		// No time specified for dates
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("homework by Oct 12");
		String expectedTask = "[Deadline Task][Description: homework][Deadline: 12.10.2016]";
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("homework on 1 Jan 2016");
		expectedTask = "[Deadline Task][Description: homework][Deadline: 01.01.2016]";
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("homework at 1 Jan 2016");
		expectedTask = "[Deadline Task][Description: homework][Deadline: 01.01.2016]";
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("homework by 1 Jan 2016");
		expectedTask = "[Deadline Task][Description: homework][Deadline: 01.01.2016]";
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		// Time specified for dates
		command = (AddTaskCommand) parser.prepareCommand("homework by 1 Jan 2016 6.30pm");
		expectedTask = "[Deadline Task][Description: homework][Deadline: 01.01.2016 06:30PM]";
		actualTask = command.getTaskDetails(true);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("homework on 1 Jan 2016 6.30pm");
		expectedTask = "[Deadline Task][Description: homework][Deadline: 01.01.2016 06:30PM]";
		actualTask = command.getTaskDetails(true);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("homework at 1 Jan 2016 6.30pm");
		expectedTask = "[Deadline Task][Description: homework][Deadline: 01.01.2016 06:30PM]";
		actualTask = command.getTaskDetails(true);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("meeting from the day after tmr to next saturday");
		expectedTask = "[Deadline Task][Description: homework][Deadline: 01.01.2016 06:30PM]";
		actualTask = command.getTaskDetails(true);
		
		Date tomorrow = DateUtil.createDateAfterToday(1, 13, 59);
		command = (AddTaskCommand) parser.prepareCommand("homework by tmr 1:59pm");
		expectedTask = String.format("[Deadline Task][Description: homework][Deadline: %s]", 
				DateUtil.dateFormatWithTime.format(tomorrow));
		actualTask = command.getTaskDetails(true);
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_deadlineTask_multipleKeywords() {
		/*
		 * Multiple "from", "by" and "at" keywords should not affect the task command
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("from by from at by Oct 12");
		String expectedTask = "[Deadline Task][Description: from by from at][Deadline: 12.10.2016]";
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_deadlineTask_looksLikeEventTask() {
		/*
		 * DeadlineTask that looks like EventTask due to the presence of "from" and "to" keywords.
		 * If "by"/"on"/"at" comes after the "from" key word, it is a DeadlineTask.
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("Camp from May 11 to May 12 at by May 1");
		String expectedTask = "[Deadline Task][Description: Camp from May 11 to May 12 at][Deadline: 01.05.2016]";
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("Camp from May 11 to May 12 at on May 1");
		expectedTask = "[Deadline Task][Description: Camp from May 11 to May 12 at][Deadline: 01.05.2016]";
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_deadlineTask_unsuccessfulDueToIncorrectDate() {
		/*
		 * DeadlineTask becomes a FloatingTask because of incorrect date formats.
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("homework by Ot 12");
		String expectedTask = "[Floating Task][Description: homework by Ot 12]";
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("homework by Octo 12");
		expectedTask = "[Floating Task][Description: homework by Octo 12]";
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("homework by Oct 35");
		expectedTask = "[Floating Task][Description: homework by Oct 35]";
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_deadlineTask_unsuccessfulDueToIncorrectFormat() {
		/*
		 * DeadlineTask becomes a FloatingTask because of incorrect date formats.
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("passerby Oct 31");
		String expectedTask = "[Floating Task][Description: passerby Oct 31]";
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
	}
	
	/**
	 * Testing situations in which user intends to create Event Tasks.
	 */
	@Test
	public void prepareCommand_eventTask_successful() {
		/*
		 * Normal add Event task command
		 */
		// No time specified for dates
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("project from Oct 12 to Oct 13");
		String expectedTask = "[Event Task][Description: project][Start date: 12.10.2016][End date: 13.10.2016]";
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("project from Oct 12 - Oct 13");
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("project from 12 October 2016 to 13 October 2016");
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		// Time specified for dates
		command = (AddTaskCommand) parser.prepareCommand("project from Oct 12 5pm - Oct 13 6.30pm");
		expectedTask = "[Event Task][Description: project][Start date: 12.10.2016 05:00PM][End date: 13.10.2016 06:30PM]";
		actualTask = command.getTaskDetails(true);
		assertEquals(actualTask, expectedTask);
		
		Date startDate = DateUtil.createDateAfterToday(0, 17, 15); // Today 5.15pm
		Date endDate = DateUtil.createDateAfterToday(0, 18, 45);   // Today 6.45pm
		command = (AddTaskCommand) parser.prepareCommand("project from today 5.15pm - 6.45pm");
		expectedTask = String.format("[Event Task][Description: project][Start date: %s][End date: %s]",
				DateUtil.dateFormatWithTime.format(startDate.getTime()), DateUtil.dateFormatWithTime.format(endDate.getTime()));
		actualTask = command.getTaskDetails(true);
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_eventTask_multipleKeywords() {
		/*
		 * Multiple "from", "by", "on" and "at" keywords should not affect the task command
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("from at by on from on by from Oct 1 - Oct 2");
		String expectedTask = "[Event Task][Description: from at by on from on by][Start date: 01.10.2016][End date: 02.10.2016]";
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_eventTask_looksLikeDeadlineTask() {
		/*
		 * EventTask that looks like DeadlineTask due to the presence of "by" keyword.
		 * If "from" comes after the "by"/"on"/"at" key word, it is an EventTask.
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("Concert by 1 December from May 1 to May 2");
		String expectedTask = "[Event Task][Description: Concert by 1 December][Start date: 01.05.2016][End date: 02.05.2016]";
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("Concert on 1 December from May 1 to May 2");
		expectedTask = "[Event Task][Description: Concert on 1 December][Start date: 01.05.2016][End date: 02.05.2016]";
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("Concert at 1 December from May 1 to May 2");
		expectedTask = "[Event Task][Description: Concert at 1 December][Start date: 01.05.2016][End date: 02.05.2016]";
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
	}
	
	@Test
	public void prepareCommand_eventTask_unsuccessfulDueToIncorrectDate() {
		/*
		 * EventTask becomes a FloatingTask because of incorrect date formats.
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("project from Octo 12 to Oct 13");
		String expectedTask = "[Floating Task][Description: project from Octo 12 to Oct 13]";
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("project from Oct 12 -- Oct 13");
		expectedTask = "[Floating Task][Description: project from Oct 12 -- Oct 13]";
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("project fr Oct 12 to Oct 13");
		expectedTask = "[Floating Task][Description: project fr Oct 12 to Oct 13]";
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("project from Oct 12 t Oct 13");
		expectedTask = "[Floating Task][Description: project from Oct 12 t Oct 13]";
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("project by Oct 12 to Oct 13");
		expectedTask = "[Floating Task][Description: project by Oct 12 to Oct 13]";
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		// End date is earlier than start date
		command = (AddTaskCommand) parser.prepareCommand("project from tomorrow to today");
		expectedTask = "[Floating Task][Description: project from tomorrow to today]";
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
		
		command = (AddTaskCommand) parser.prepareCommand("project from today 5pm - 3am");
		expectedTask = "[Floating Task][Description: project from today 5pm - 3am]";
		actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
	}

	@Test
	public void prepareCommand_eventTask_unsuccessfulDueToIncorrectFormat() {
		/*
		 * EventTask becomes a FloatingTask because of incorrect date formats.
		 */
		AddTaskCommand command = (AddTaskCommand) parser.prepareCommand("refrom Oct 30 to Oct 31");
		String expectedTask = "[Floating Task][Description: refrom Oct 30 to Oct 31]";
		String actualTask = command.getTaskDetails(false);
		assertEquals(actualTask, expectedTask);
	}
}

package w15c2.tusk.model.task;

import java.util.Date;

import w15c2.tusk.commons.util.DateUtil;

/**
 * An EventTask is a task that holds a start date and an end date
 */
//@@author A0139817U
public class EventTask extends Task implements PinnableTask, CompletableTask {

	private Date startDate;
	private Date endDate;

	public EventTask(String descriptionText, Date startDate, Date endDate) {
		this.description = new Description(descriptionText);
		this.startDate = startDate;
		this.endDate = endDate;
	}
	

	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	@Override
	public EventTask copy() {
		String newDescription = this.description.getContent();
		Date newStartDate = new Date(this.startDate.getTime());
		Date newEndDate = new Date(this.endDate.getTime());
		EventTask newTask = new EventTask(newDescription, newStartDate, newEndDate); 
		
		// Copy pin status
		if (this.isPinned()) {
			newTask.setAsPin();
		} else {
			newTask.setAsNotPin();
		}
		
		// Copy completed status
		if (this.isCompleted()) {
			newTask.setAsComplete();
		} else {
			newTask.setAsUncomplete();
		}
		
		return newTask;
	}
	
	@Override
	public String toString() {
		return description.toString();
	}
	
	@Override
	// Return the specifics of each task (with or without time)
	public String getTaskDetails(boolean withTime) {
		if (withTime) {
			return String.format("[Event Task][Description: %s][Start date: %s][End date: %s]", 
					description, DateUtil.dateFormatWithTime.format(startDate), DateUtil.dateFormatWithTime.format(endDate));
		} else {
			return String.format("[Event Task][Description: %s][Start date: %s][End date: %s]", 
					description, DateUtil.dateFormat.format(startDate), DateUtil.dateFormat.format(endDate));
		}
	}
	
    @Override
    public boolean isOverdue() {
        return startDate.before(new Date());
    }
}
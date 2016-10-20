package seedu.address.model.task;

import java.util.Date;

import seedu.address.model.Copiable;

/*
 * A DeadlineTask is a task that holds a date as the deadline
 */
public class DeadlineTask extends Task implements FavoritableTask, CompletableTask, DatedTask {

	private Date deadline;

	public DeadlineTask(String descriptionText, Date deadline) {
		this.description = new Description(descriptionText);
		this.deadline = deadline;
	}
		
	public Date getDeadline() {
		return deadline;
	}
	
	@Override
	public DeadlineTask copy() {
		String newDescription = this.description.getContent();
		Date newDeadline = new Date(this.deadline.getTime());
		DeadlineTask newTask = new DeadlineTask(newDescription, newDeadline);
		if (this.isFavorite()) {
			newTask.setAsFavorite();
		} else {
			newTask.setAsNotFavorite();
		}
		return newTask;
	}
	
	@Override
	public String toString() {
		return String.format("[Deadline Task][Description: %s][Deadline: %s]", 
				description, dateFormat.format(deadline));
	}
}
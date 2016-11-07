package w15c2.tusk.model.task;

/**
 * A simple Task implementation that does not have a deadline
 */
public class FloatingTask extends Task implements PinnableTask, CompletableTask {

	private FloatingTask(Description description) {
		super(description);
	}

	public FloatingTask(String descriptionText) {
		this(new Description(descriptionText));
	}
	
	@Override
	public FloatingTask copy() {
		Description newDescription = new Description(this.description.getContent());
		FloatingTask newTask = new FloatingTask(newDescription);
		
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
	public String getTaskDetails(boolean withTime) {
		return String.format("[Floating Task][Description: %s]", description);
	}
}

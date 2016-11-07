package w15c2.tusk.model.task;

//@@author A0138978E
/*
 * Tasks that implement this interface can be pinned
 */
public interface PinnableTask {
	
	/*
	 * Gives the task a 'pin' status
	 */
	public void setAsPin();
	
	/*
	 * Removes the task's 'pin' status
	 */
	public void setAsNotPin();
	
	/*
	 * Checks if a task is pinned
	 */
	public boolean isPinned();
}

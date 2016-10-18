package seedu.address.model.task;

public interface FavoritableTask {
	
	/*
	 * Gives the task a 'favorite' status
	 */
	public void setAsFavorite();
	
	/*
	 * Removes the task's 'favorite' status
	 */
	public void setAsNotFavorite();
	
	/*
	 * Checks if a task is favorited
	 */
	public boolean isFavorite();
}
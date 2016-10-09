package seedu.address.model.task;

/*
 * A simple Task implementation that does not have a deadline
 */
public class FloatingTask extends Task implements FavoritableTask {

	private boolean isFavorite = false;
	

	public FloatingTask(Description description) {
		super(description);
	}

	public FloatingTask(String descriptionText) {
		this(new Description(descriptionText));
	}
	
	
	@Override
	public void setIsFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
		
	}

	@Override
	public boolean getIsFavorite() {
		return isFavorite;
	}

	
	
}

package w15c2.tusk.model.task;

//@@author A0138978E
/*
 * Represents an immutable description for a Task.
 */
public class Description {
	private final String content;
	
	public Description() {
		this.content = "";
	}
	
	public Description(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	@Override
	public boolean equals(Object other) {
	    return this.toString().equals(other.toString());
	}
	
	@Override
	public String toString() {
		return getContent();
	}
}

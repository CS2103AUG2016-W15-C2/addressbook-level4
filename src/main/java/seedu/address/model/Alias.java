package seedu.address.model;

/*
 * A one-word alias for any sentence to be used as a command
 */
public class Alias {
	private String shortcut;
	private String sentence;
	
	public Alias() {
		this("", "");
	}
	
	public Alias(String shortcut, String sentence) {
		this.shortcut = shortcut;
		this.sentence = sentence;
	}

	public String getShortcut() {
		return shortcut;
	}	
	
	public String getSentence() {
		return sentence;
	}

	@Override
	public String toString() {
		String s = new String(shortcut);
		s = s.concat(" " + sentence);
		return s;
	}
	
	@Override
	public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Alias// instanceof handles nulls
                && this.shortcut.equals(
                ((Alias) other).getShortcut()));
    }

	
}
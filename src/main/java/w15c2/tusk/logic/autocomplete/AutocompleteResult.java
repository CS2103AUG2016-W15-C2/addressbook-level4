package w15c2.tusk.logic.autocomplete;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterables;

//@@author A0138978E
/**
 * Cycles through all matches infinitely
 */
public class AutocompleteResult {
	
	private Iterator<String> matchIterator;
	
	public AutocompleteResult(List<String> matchedWords) {
		assert matchedWords != null;
		assert matchedWords.size() > 0;
		
		// Infinite circular iterator for the list of matched words
		this.matchIterator = Iterables.cycle(matchedWords).iterator();
	}
	
	public String getNextMatch() {
		assert matchIterator != null;
		return matchIterator.next();		
	}
	
	
}

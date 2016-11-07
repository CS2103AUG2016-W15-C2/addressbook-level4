package w15c2.tusk.logic.autocomplete;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import w15c2.tusk.logic.autocomplete.AutocompleteEngine;
import w15c2.tusk.logic.autocomplete.AutocompleteResult;

//@@author A0138978E
public class AutocompleteEngineTest {
	private AutocompleteEngine autocompleteEngine;
	
	@Test
	public void getQueryResult_noWordsToMatch() {
		autocompleteEngine = new AutocompleteEngine();
		AutocompleteResult result = autocompleteEngine.getQueryResult("ad");
		assertEquals(result.getNextMatch(), "ad");
		assertEquals(result.getNextMatch(), "ad");
	}
	
	@Test
	public void getQueryResult_noWordsCorrectlyMatch() {
		List<String> wordsToMatch = new ArrayList<String>();
		wordsToMatch.add("delete");
		
		autocompleteEngine = new AutocompleteEngine();
		AutocompleteResult result = autocompleteEngine.getQueryResult("ad");
		assertEquals(result.getNextMatch(), "ad");
		assertEquals(result.getNextMatch(), "ad");
	}
	
	@Test
	public void getQueryResult_oneWordMatches() {
		List<String> wordsToMatch = new ArrayList<String>();
		wordsToMatch.add("add");
		
		autocompleteEngine = new AutocompleteEngine(new HashSet<String>(wordsToMatch));
		AutocompleteResult result = autocompleteEngine.getQueryResult("ad");
		assertEquals(result.getNextMatch(), "add");
		assertEquals(result.getNextMatch(), "add");
	}
	
	@Test
	public void getQueryResult_twoWordsMatch() {
		List<String> wordsToMatch = new ArrayList<String>();
		wordsToMatch.add("add");
		wordsToMatch.add("alias");
		
		autocompleteEngine = new AutocompleteEngine(new HashSet<String>(wordsToMatch));
		AutocompleteResult result = autocompleteEngine.getQueryResult("a");
		assertEquals(result.getNextMatch(), "add");
		assertEquals(result.getNextMatch(), "alias");
		assertEquals(result.getNextMatch(), "add");
	}
	
	@Test
	public void getQueryResult_twoWordsMatchOneWordDoesNot() {
		List<String> wordsToMatch = new ArrayList<String>();
		wordsToMatch.add("delete");
		wordsToMatch.add("add");
		wordsToMatch.add("alias");
		
		autocompleteEngine = new AutocompleteEngine(new HashSet<String>(wordsToMatch));
		AutocompleteResult result = autocompleteEngine.getQueryResult("a");
		assertEquals(result.getNextMatch(), "add");
		assertEquals(result.getNextMatch(), "alias");
		assertEquals(result.getNextMatch(), "add");
	}
	
	@Test
	public void getQueryResult_oneWordMatchesTwoWordsDoNot() {
		List<String> wordsToMatch = new ArrayList<String>();
		wordsToMatch.add("delete");
		wordsToMatch.add("add");
		wordsToMatch.add("alias");
		
		autocompleteEngine = new AutocompleteEngine(new HashSet<String>(wordsToMatch));
		AutocompleteResult result = autocompleteEngine.getQueryResult("d");
		assertEquals(result.getNextMatch(), "delete");
		assertEquals(result.getNextMatch(), "delete");
	}
	
	@Test
	public void getQueryResult_twoDifferentQueries() {
		List<String> wordsToMatch = new ArrayList<String>();
		wordsToMatch.add("delete");
		wordsToMatch.add("add");
		wordsToMatch.add("alias");
		
		autocompleteEngine = new AutocompleteEngine(new HashSet<String>(wordsToMatch));
		AutocompleteResult result = autocompleteEngine.getQueryResult("d");
		assertEquals(result.getNextMatch(), "delete");
		assertEquals(result.getNextMatch(), "delete");
		
		result = autocompleteEngine.getQueryResult("a");
		assertEquals(result.getNextMatch(), "add");
		assertEquals(result.getNextMatch(), "alias");
		assertEquals(result.getNextMatch(), "add");
		
	}

	
}

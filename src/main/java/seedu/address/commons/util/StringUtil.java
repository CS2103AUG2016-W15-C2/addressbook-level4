package seedu.address.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    public static boolean containsIgnoreCase(String source, String query) {
        String[] split = source.toLowerCase().split("\\s+");
        List<String> strings = Arrays.asList(split);
        return strings.stream().filter(s -> s.equals(query.toLowerCase())).count() > 0;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t){
        assert t != null;
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     *   Will return false for null, empty string, "-1", "0", "+1", and " 2 " (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s){
        return s != null && s.matches("^0*[1-9]\\d*$");
    }
    
    /*
     * Returns a string where spaces are added between
     * word-character and character-word boundaries
     * E.g. 31Oct2016 -> 31 Oct 2016
     */
    public static String addSpacesBetweenNumbersAndWords(String s) {
    	if (s == null) return null;
    	
    	s = s.replaceAll("(\\d+)(?!st|nd|rd|th)([a-zA-Z]+)", "$1 $2");
    	s = s.replaceAll("([a-zA-Z]+)(\\d+)(?!st|nd|rd|th)", "$1 $2");
    	return s;
    	/*
    	if (s.length() <= 1) return s;
    	
    	StringBuilder outString = new StringBuilder();
    	
    	char isPre3
    	char current = s.charAt(1);
    	
    	outString.append(prev);
    	
    	for (int i = 1; i < s.length(); i++) {
    		
    	}
    	*/

    }
}

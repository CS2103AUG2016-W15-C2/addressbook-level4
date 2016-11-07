package w15c2.tusk.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    public static boolean containsIgnoreCase(String source, String query) {
    	if(query.equals("")){
    		return false;
    	}
        String[] split = source.toLowerCase().split("\\s+");
        List<String> strings = Arrays.asList(split);
        return strings.stream().filter(s -> s.contains(query.toLowerCase())).count() > 0;
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
    
    //@@author A0138978E
    /**
     * Returns a string where spaces are added between
     * word-character and character-word boundaries
     * E.g. 31Oct2016 -> 31 Oct 2016
     * @param s the original string with numbers and words possibly
     * @return a string with spaces between numbers and words
     */
    public static String addSpacesBetweenNumbersAndWords(String s) {
    	if (s == null) return null;
    	
    	s = s.replaceAll("(\\d+)(?!st|nd|rd|th)([a-zA-Z]+)", "$1 $2");
    	s = s.replaceAll("([a-zA-Z]+)(\\d+)(?!st|nd|rd|th)", "$1 $2");
    	return s;

    }
}

package seedu.address.logic.parser;

/*
 * List of all available parsers to respond to command input
 */
public class CommandParserList {
	public static Class<?>[] getList(){
	    
		return new Class[]{
				AddCommandParser.class, 
				UpdateCommandParser.class,
				DeleteCommandParser.class, 
				FindCommandParser.class,
				FavoriteCommandParser.class,
				UnfavoriteCommandParser.class,
				UndoCommandParser.class,
				ListCommandParser.class,
				AddAliasCommandParser.class, 
				DeleteAliasCommandParser.class};
	}
}

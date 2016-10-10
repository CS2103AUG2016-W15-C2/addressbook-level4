package seedu.address.logic.parser.timedate;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joestelmach.natty.*;

public class NattyTest {
	
	public NattyTest() {
		Parser parser = new Parser();
		List<DateGroup> groups = parser.parse("the day before next thursday");
		for(DateGroup group:groups) {
		  List<Date> dates = group.getDates();
		  int line = group.getLine();
		  int column = group.getPosition();
		  String matchingValue = group.getText();
		  String syntaxTree = group.getSyntaxTree().toStringTree();
		  Map<String, List<ParseLocation>> parseMap = group.getParseLocations();
		  boolean isRecurring = group.isRecurring();
		  Date recursUntil = group.getRecursUntil();
		}
	}
	
	

}

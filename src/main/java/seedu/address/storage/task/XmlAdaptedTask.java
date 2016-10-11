package seedu.address.storage.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Task;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String description;
    
    private Date startDate;
    
    private Date endDate;
    
    @XmlElement(required = true)
    private Class<?> taskType;
    
    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(Task source) {
    	description = source.getDescription().toString();
    	taskType = source.getClass();
    	
    	// Set dates appropriately
    	if (source instanceof DeadlineTask) {
    		startDate = null;
    		endDate = ((DeadlineTask)source).getDeadline();
    	} else if (source instanceof EventTask) {
    		EventTask castedSource = (EventTask)source;
    		startDate = castedSource.getStartDate();
    		endDate = castedSource.getEndDate();    		
    	} else {
    		startDate = null;
    		endDate = null;
    	}
    	
    	
    }

    /**
     * Converts this jaxb-friendly adapted task object into an appropriate Task subclass..
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
    	if (taskType == Task.class || taskType == null) {
    		throw new IllegalValueException("Incorrect task type: " + taskType.toString());
    	}
    	
    	if (taskType == FloatingTask.class) {
    		return new FloatingTask(description);
    	} else if (taskType == DeadlineTask.class) {
    		return new DeadlineTask(description, endDate);
    	} else if (taskType == EventTask.class) {
    		return new EventTask(description, startDate, endDate);
    	} else {
    		throw new IllegalValueException("Incorrect task type: " + taskType.toString());
    	}
        
    }
}

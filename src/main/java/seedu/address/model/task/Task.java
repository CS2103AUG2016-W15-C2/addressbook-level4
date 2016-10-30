package seedu.address.model.task;

import seedu.address.model.Copiable;

/*
 * Represents a highly general Task object to be subclassed
 */
public abstract class Task implements FavoritableTask, CompletableTask, Comparable<Task>, Copiable<Task> {
	//@@author A0138978E
	/*
	 * All tasks are required to minimally have a description
	 */
	protected Description description;
		
	/*
	 * Indicates if this task is favorited
	 */
	protected boolean favorite = false;
	
	//@@author 
	/*
	 * Indicates if this task is completed
	 */
	protected boolean complete = false;
	
	//@@author A0138978E
	@Override
	public void setAsFavorite() {
		this.favorite = true;
	}
	
	@Override
	public void setAsNotFavorite() {
		this.favorite = false;
	}
	
	@Override
	public boolean isFavorite() {
		return this.favorite;
	}
	
	//@@author 
	@Override
	public void setAsComplete() {
		this.complete = true;
	}
	
	@Override
	public void setAsUncomplete() {
		this.complete = false;
	}
	
	@Override
	public boolean isComplete(){
		return this.complete;
	}
	
	
	//@@author A0138978E
	public Task() {
		this(new Description());
	}
	
	public Task(Description description) {
		this.description = description;
	}

	public Description getDescription() {
		return description;
	}	
	
	@Override
	public String toString() {
		return description.toString();
	}
	
	
	/*
	 * Defines an ordering where favorited tasks are always appear at the start
	 * of an ordered list of tasks as opposed to non-favorited tasks
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Task other) {
		if (this.isFavorite() && !other.isFavorite()) {
			return -1;
		} else if (!this.isFavorite() && other.isFavorite()) {
			return 1;
		} else if (this instanceof FloatingTask && other instanceof DatedTask){
		    return -1;
		}
		else if (this instanceof DeadlineTask && other instanceof DeadlineTask ) {
		    DeadlineTask thisOne = (DeadlineTask) this;
		    DeadlineTask otherOne = (DeadlineTask) other;
		    if(thisOne.getDeadline().before(otherOne.getDeadline())) {
		        return -1;
		    }
		    else if(thisOne.getDeadline().after(otherOne.getDeadline())) {
		        return 1;
		    }
		    else {
		        return 0;
		    }
		}
		else if (this instanceof EventTask && other instanceof EventTask) {
		    EventTask thisOne = (EventTask) this;
		    EventTask otherOne = (EventTask) other;
		    if(thisOne.getEndDate().before(otherOne.getEndDate())) {
		        return -1;
		    }
		    else if(thisOne.getEndDate().after(otherOne.getEndDate())) {
		        return 1;
		    }
		    else {
		        return 0;
		    }
		}
	      else if (this instanceof EventTask && other instanceof DeadlineTask) {
	            EventTask thisOne = (EventTask) this;
	            DeadlineTask otherOne = (DeadlineTask) other;
	            if(thisOne.getEndDate().before(otherOne.getDeadline())) {
	                return -1;
	            }
	            else if(thisOne.getEndDate().after(otherOne.getDeadline())) {
	                return 1;
	            }
	            else {
	                return 0;
	            }
	        }
	      else if (this instanceof DeadlineTask && other instanceof EventTask) {
	          DeadlineTask thisOne = (DeadlineTask) this;
	            EventTask otherOne = (EventTask) other;
	            if(thisOne.getDeadline().before(otherOne.getEndDate())) {
	                return -1;
	            }
	            else if(thisOne.getDeadline().after(otherOne.getEndDate())) {
	                return 1;
	            }
	            else {
	                return 0;
	            }
	        }

		else {
		    return 0;
		}
	}
	
}

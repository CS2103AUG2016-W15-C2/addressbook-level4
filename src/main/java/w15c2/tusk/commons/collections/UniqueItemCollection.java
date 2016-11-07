package w15c2.tusk.commons.collections;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import w15c2.tusk.commons.exceptions.DuplicateDataException;
import w15c2.tusk.model.Copiable;

//@@author A0138978E
/*
 * Defines a list where every item must be referentially different from every other item (no duplicate objects)
 */
public class UniqueItemCollection<T> implements Iterable<T>{
	
	/**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateItemException extends DuplicateDataException {
        public DuplicateItemException() {
            super("Operation would result in duplicate items");
        }
    }

    
    /**
     * Signals that an operation targeting a specified item in the list would fail because
     * there is no such matching item in the list.
     */
    public static class ItemNotFoundException extends Exception {}

    private final ObservableList<T> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ItemCollection.
     */
    public UniqueItemCollection() {}
    
    //@@author A0139817U
    /**
     * Duplicates an existing UniqueItemCollection
     */
    @SuppressWarnings("unchecked")
	public UniqueItemCollection<T> copyCollection()  {
    	UniqueItemCollection<T> copiedCollection = new UniqueItemCollection<T>();
    	for (T item : internalList) {
    		try {
    			// Items in the list must implement the Copiable interface
	    		assert (item instanceof Copiable);
	    		
	    		Copiable<T> copiableItem = (Copiable<T>) item;
	    		
	    		// Make a copy of the item and add into the new list
	    		copiedCollection.add(copiableItem.copy()); 
	    		
    		} catch (DuplicateItemException die) {
    			assert false : "There should be no duplicate items in the UniqueItemCollection";
    		}
    	}
    	return copiedCollection;
    }

    //@@author A0138978E
    /**
     * Returns true if the list contains an equivalent item as the given argument.
     * @param toCheck the item to check
     * @return if the list contains an equivalent item
     */
    public boolean contains(T toCheck) {
        if (toCheck == null) {
            return false;
        }
        
        // Force reference equality in the internal list
        return internalList.stream().anyMatch(obj -> obj == toCheck);
    }

    /**
     * Adds a item to the list.
     *@param toAdd item to add
     * @throws DuplicateItemException if the item to add is a duplicate of an existing item in the list.
     */
    public void add(T toAdd) throws DuplicateItemException {
        assert toAdd != null;
        
        if (contains(toAdd)) {
            throw new DuplicateItemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent item from the list.
     * @param toRemove item to remove
     * @throws ItemNotFoundException if no such item could be found in the list.
     */
    public boolean remove(T toRemove) throws ItemNotFoundException {
        assert toRemove != null;
        final boolean itemFoundAndDeleted = internalList.remove(toRemove);
        if (!itemFoundAndDeleted) {
            throw new ItemNotFoundException();
        }
        return itemFoundAndDeleted;
    }
    
    //@@author A0139817U
    /**
     * Replaces an item to remove with an item to add from the list.
     * 
     * @throws ItemNotFoundException if the item to be removed cannot be found in the list.
     */
    public boolean replace(T toRemove, T toAdd) throws ItemNotFoundException {
    	assert toRemove != null && toAdd != null;
    	for (int i = 0; i < internalList.size(); i++) {
    		if (internalList.get(i) == toRemove) {
    			internalList.remove(i);
    			internalList.add(i, toAdd);
    			return true;
    		}
    	}
    	throw new ItemNotFoundException();
    }

    //@@author A0138978E
    public ObservableList<T> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    /**
     * Check for reference equality, then compare equality element-wise
     */
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueItemCollection// instanceof handles nulls
                && this.internalList.equals(
                ((UniqueItemCollection<T>) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}

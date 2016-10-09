package seedu.address.commons.collections;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;


public class UniqueItemCollection<T> implements Iterable<T>{
	
	/**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateItemException extends DuplicateDataException {
        protected DuplicateItemException() {
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

    /**
     * Returns true if the list contains an equivalent item as the given argument.
     */
    public boolean contains(T toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a item to the list.
     *
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
     *
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

    public ObservableList<T> getInternalList() {
        return internalList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
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

package w15c2.tusk.testutil;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.scene.layout.AnchorPane;
import junit.framework.AssertionFailedError;
import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.core.UnmodifiableObservableList;
import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.commons.util.FileUtil;
import w15c2.tusk.commons.util.XmlUtil;
import w15c2.tusk.logic.commands.taskcommands.AddTaskCommand;
import w15c2.tusk.model.Model;
import w15c2.tusk.model.ModelManager;
import w15c2.tusk.model.task.DeadlineTask;
import w15c2.tusk.model.task.EventTask;
import w15c2.tusk.model.task.FloatingTask;
import w15c2.tusk.model.task.Task;
import w15c2.tusk.storage.task.XmlSerializableTaskManager;

/**
 * A utility class for test cases.
 */
public class TestUtil {
    
    /**
    * Folder used for temp files created during testing. Ignored by Git.
    */
    public static String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    public static Model setupEmptyTaskList() {
        return new ModelManager();
    }

    //@@author A0139817U
    // Setting up tasks in the TaskList in order to find them in the tests
    public static Model setupSomeTasksInTaskList(int n) throws IllegalValueException {
        Model newTaskList = new ModelManager();
        // Add 3 tasks into the task manager
        for (int i = 0; i < n; i++) {
            AddTaskCommand command = new AddTaskCommand(String.format("Task %d", i));
            command.setData(newTaskList);
            command.execute();
        }
        return newTaskList;
    }
	
	/**
	 * Setting up Floating tasks in the TaskList in order to find them in the tests
	 */
	public static Model setupFloatingTasks(int n) throws IllegalValueException {
		Model newTaskList = new ModelManager();
		// Add n tasks into the task manager
		for (int i = 0; i < n; i++) {
			newTaskList.addTask(new FloatingTask(String.format("Task %d", i)));
		}
		return newTaskList;
	}
	
	// Setting up tasks with more varied names
	public static Model setupTasksWithVariedNames(int n) throws IllegalValueException {
		Model newTaskList = new ModelManager();
		// Add n tasks into the task manager
		for (int i = 0; i < n; i++) {
			if (i % 3 == 0) {
				newTaskList.addTask(new FloatingTask(String.format("Apple %d", i)));
			} else if (i % 3 == 1) {
				newTaskList.addTask(new FloatingTask(String.format("Banana %d", i)));
			} else {
				newTaskList.addTask(new FloatingTask(String.format("Carrot %d", i)));
			}
		}
		return newTaskList;
	}
	
	// Setting up completed tasks in the TaskList in order to find them in the tests
	public static Model setupSomeCompletedTasksInTaskList(int n) throws IllegalValueException {
		Model newTaskList = new ModelManager();
		// Add 3 tasks into the task manager
		for (int i = 0; i < n; i++) {
			AddTaskCommand command = new AddTaskCommand(String.format("Task %d", i));
			command.setData(newTaskList);
			command.execute();
		}
		UnmodifiableObservableList<Task> list= newTaskList.getCurrentFilteredTasks();
		for (int i = 0; i < n; i++) {
			list.get(i).setAsComplete();
		}
		return newTaskList;
	}
		
	// Setting up pinned tasks in the TaskList in order to find them in the tests
	public static Model setupSomePinnedTasksInTaskList(int n) throws IllegalValueException {
		Model newTaskList = new ModelManager();
		// Add 3 tasks into the task manager
		for (int i = 0; i < n; i++) {
			AddTaskCommand command = new AddTaskCommand(String.format("Task %d", i));
			command.setData(newTaskList);
			command.execute();
		}
		UnmodifiableObservableList<Task> list= newTaskList.getCurrentFilteredTasks();
		for (int i = 0; i < n; i++) {
			list.get(i).setAsPin();
		}
		return newTaskList;
	}
	/**
	 * Setting up interleaved Floating, Deadline and Event tasks.
	 * Dates for Deadline tasks are set to 1 January 2016.
	 * Date range for Event tasks are set from 1 January 2016 to 2 January 2016.
	 * 
	 * NOTE: Do not change the descriptions and dates because other tests depend on it.
	 */
	public static Model setupMixedTasks(int n) throws IllegalValueException {
		Model newTaskList = new ModelManager();
		int counter = 0;
		for (int i = 0; i < n; i++) {
			int k = counter % 3;
			if (k == 0) {
				// Add Floating task
				String description = String.format("Task %d", i);
				newTaskList.addTask(new FloatingTask(description));
				
			} else if (k == 1) {
				// Add Deadline task
				String description = String.format("Task %d", i);
				Date deadline = new GregorianCalendar(2016, Calendar.JANUARY, 1).getTime();
				newTaskList.addTask(new DeadlineTask(description, deadline));
				
			} else {
				// Add Event task
				String description = String.format("Task %d", i);
				Date startDate = new GregorianCalendar(2016, Calendar.JANUARY, 1).getTime();
				Date endDate = new GregorianCalendar(2016, Calendar.JANUARY, 2).getTime();
				newTaskList.addTask(new EventTask(description, startDate, endDate));
			}
			counter++;
		}
		return newTaskList;
	}
	
	 /**
	  * Appends the file name to the sandbox folder path.
	  * Creates the sandbox folder if it doesn't exist.
	  * @param fileName
	  * @return
	  */
	//@@author A0138978E
	 public static String getFilePathInSandboxFolder(String fileName) {
	     try {
	         FileUtil.createDirs(new File(SANDBOX_FOLDER));
	     } catch (IOException e) {
	         throw new RuntimeException(e);
	     }
	     return SANDBOX_FOLDER + fileName;
	 }
	
	 public static void createDataFileWithSampleData(String filePath) {
	     createDataFileWithData(generateSampleStorageTaskManager(), filePath);
	 }
	
	 public static <T> void createDataFileWithData(T data, String filePath) {
	     try {
	         File saveFileForTesting = new File(filePath);
	         FileUtil.createIfMissing(saveFileForTesting);
	         XmlUtil.saveDataToFile(saveFileForTesting, data);
	     } catch (Exception e) {
	         throw new RuntimeException(e);
	     }
	 }
	 public static XmlSerializableTaskManager generateSampleStorageTaskManager() {
	     return new XmlSerializableTaskManager(generateEmptyTaskManager());
	 }
	 public static UniqueItemCollection<Task> generateEmptyTaskManager() {
	     return new UniqueItemCollection<Task>();
	 }
	 
	 public static AnchorPane generateAnchorPane() {
	     return new AnchorPane();
	 }

    public static String LS = System.lineSeparator();

    public static void assertThrows(Class<? extends Throwable> expected, Runnable executable) {
        try {
            executable.run();
        }
        catch (Throwable actualException) {
            if (!actualException.getClass().isAssignableFrom(expected)) {
                String message = String.format("Expected thrown: %s, actual: %s", expected.getName(),
                        actualException.getClass().getName());
                throw new AssertionFailedError(message);
            } else return;
        }
        throw new AssertionFailedError(
                String.format("Expected %s to be thrown, but nothing was thrown.", expected.getName()));
    }
}

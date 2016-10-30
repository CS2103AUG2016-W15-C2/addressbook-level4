package seedu.address.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.model.task.Task;
import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.NewTaskListEvent;
import seedu.address.commons.events.model.TaskManagerChangedEvent;

import java.awt.Label;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

/**
 * Panel containing the list of tasks.
 */
public class TaskListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "TaskListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    private ListView<Task> overdueListView;
    @FXML
    private ListView<Task> favoriteListView;
    @FXML
    private ListView<Task> todayListView;
    @FXML
    private ListView<Task> tomorrowListView;
    @FXML
    private ListView<Task> weekAheadListView;
    @FXML
    private ListView<Task> othersListView;
    @FXML
    private ListView<Task> floatingListView;
    
    @FXML
    Label overdueLabel;
    
    private static ObservableList<Task> overdueTaskList;
    private static ObservableList<Task> favoriteTaskList;
    private static ObservableList<Task> floatingTaskList;
    private static ObservableList<Task> todayTaskList;
    private static ObservableList<Task> tomorrowTaskList;
    private static ObservableList<Task> weekAheadTaskList;
    private static ObservableList<Task> othersTaskList;
    
    private int index;
    
    

    public TaskListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static TaskListPanel load(Stage primaryStage, AnchorPane taskListPlaceholder,
                                       ArrayList<ObservableList<Task>> listOfLists) {
        TaskListPanel taskListPanel =
                UiPartLoader.loadUiPart(primaryStage, taskListPlaceholder, new TaskListPanel());
        taskListPanel.configure(listOfLists);
        return taskListPanel;
    }

    private static void assignLists(ArrayList<ObservableList<Task>> listOfLists) {
        overdueTaskList = listOfLists.get(0);
        favoriteTaskList = listOfLists.get(1);
        floatingTaskList = listOfLists.get(2);
        todayTaskList = listOfLists.get(3);
        tomorrowTaskList = listOfLists.get(4);
        weekAheadTaskList= listOfLists.get(5);
        othersTaskList= listOfLists.get(6);
    }

    private void configure(ArrayList<ObservableList<Task>> listOfLists) {
        setConnections(listOfLists);
        addToPlaceholder();
        registerAsAnEventHandler(this);
    }

    private void setConnections(ArrayList<ObservableList<Task>> listOfLists) {
        assignLists(listOfLists);
        overdueListView.setItems(overdueTaskList);
        favoriteListView.setItems(favoriteTaskList);
        floatingListView.setItems(floatingTaskList);
        todayListView.setItems(todayTaskList);
        tomorrowListView.setItems(tomorrowTaskList);
        weekAheadListView.setItems(weekAheadTaskList);
        othersListView.setItems(othersTaskList);
        index = 1;
        overdueListView.setCellFactory(listView -> new TaskListViewCell());
        index += overdueTaskList.size();
        favoriteListView.setCellFactory(listView -> new TaskListViewCell());
        floatingListView.setCellFactory(listView -> new TaskListViewCell());
        todayListView.setCellFactory(listView -> new TaskListViewCell());
//        index += todayTaskList.size();
        tomorrowListView.setCellFactory(listView -> new TaskListViewCell());
//        index += tomorrowTaskList.size();
        weekAheadListView.setCellFactory(listView -> new TaskListViewCell());
//        index += weekAheadTaskList.size();
        othersListView.setCellFactory(listView -> new TaskListViewCell());
    }

    private void addToPlaceholder() {
        placeHolderPane.getChildren().add(panel);
    }

//    public void scrollTo(int index) {
//        Platform.runLater(() -> {
//            taskListView.scrollTo(index);
//            taskListView.getSelectionModel().clearAndSelect(index);
//        });
//    }
    
    @Subscribe
    public void handleNewTaskListEvent(NewTaskListEvent abce) {
    	ArrayList<ObservableList<Task>> newTasks = abce.listOfLists;
		setConnections(newTasks);
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Refreshed task list"));
    }

    class TaskListViewCell extends ListCell<Task> {

        public TaskListViewCell() {
        }

        //@@author A0138978E
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);
            
            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } 
            else {
            	TaskCard currentCard = TaskCard.load(task, getIndex() + 1);
            	HBox cardPane = currentCard.getLayout();
               
            	setGraphic(cardPane);
            	
            	// Set the color of the card based on whether it's favorited
                if (task.isFavorite()) {
                	cardPane.setStyle("-fx-background-color: yellow;");
                } else {
                	cardPane.setStyle(null);
                }
            }
        }
    }
    
    class FloatingListViewCell extends ListCell<Task> {

        public FloatingListViewCell() {
        }

        //@@author A0138978E
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);
            
            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } 
            else {
                TaskCard currentCard = TaskCard.load(task, getIndex() + index);
                HBox cardPane = currentCard.getLayout();
               
                setGraphic(cardPane);
                
                // Set the color of the card based on whether it's favorited
                if (task.isFavorite()) {
                    cardPane.setStyle("-fx-background-color: yellow;");
                } else {
                    cardPane.setStyle(null);
                }
            }
        }
    }

}

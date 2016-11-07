package w15c2.tusk.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import w15c2.tusk.commons.core.LogsCenter;
import w15c2.tusk.commons.events.ui.IncorrectTaskCommandAttemptedEvent;
import w15c2.tusk.commons.util.FxViewUtil;
import w15c2.tusk.logic.Logic;
import w15c2.tusk.logic.commands.CommandResult;

public class CommandBox extends UiPart {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";

    private AnchorPane placeHolderPane;
    private AnchorPane commandPane;
    private ResultDisplay resultDisplay;
    String previousCommandTest;

    private Logic logic;
    
    //@@author A0138978E
    private boolean hasTextChangedForAutocomplete = true;
    
	private final ChangeListener<? super String> textChangedListener = (observable, newVal, oldVal) -> {
		hasTextChangedForAutocomplete = true;
	};
	
	//@@author
    @FXML
    private TextField commandTextField;
    private CommandResult mostRecentResult;

    public static CommandBox load(Stage primaryStage, AnchorPane commandBoxPlaceholder,
            ResultDisplay resultDisplay, Logic logic) {
        CommandBox commandBox = UiPartLoader.loadUiPart(primaryStage, commandBoxPlaceholder, new CommandBox());
        commandBox.configure(resultDisplay, logic);
        commandBox.addToPlaceholder();
        commandBox.setKeyListener();
        commandBox.setTextChangedListener();
        commandBox.requestFocus();
        return commandBox;
    }


    public void configure(ResultDisplay resultDisplay, Logic logic) {
        this.resultDisplay = resultDisplay;
        this.logic = logic;
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(commandPane, 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @Override
    public void setNode(Node node) {
        commandPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }


    @FXML
    private void handleCommandInputChanged() {
        //Take a copy of the command text
        previousCommandTest = commandTextField.getText();

        /* We assume the command is correct. If it is incorrect, the command box will be changed accordingly
         * in the event handling code {@link #handleIncorrectCommandAttempted}
         */
        setStyleToIndicateCorrectCommand();
        mostRecentResult = logic.execute(previousCommandTest);
        resultDisplay.postMessage(mostRecentResult.feedbackToUser);
        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }
    
    //@@author A0138978E
    private void setKeyListener() {
    	commandTextField.setOnKeyPressed(keyListener);
    }
    
    /*
     * Handles Up/Down keys to replace commandbox content with previous/next commands.
     * Makes sure that this isn't part of a control-up/down combination.
     * Handles Tab key by autocompleting command in the current box if it's a new command,
     * or cycles through autocomplete suggestions if nothing else has changed except the TAB press
     */
    private EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if(event.getCode() == KeyCode.UP && event.isControlDown() == false) {
            	String previousCommand = logic.getPreviousCommand();
            	commandTextField.setText(previousCommand);
            	setCaretPositionToEnd();
            	event.consume();
            	
            } else if(event.getCode() == KeyCode.DOWN && event.isControlDown() == false) {
            	String nextCommand = logic.getNextCommand();
            	commandTextField.setText(nextCommand);
            	setCaretPositionToEnd();
            	event.consume();
            	
            } else if(event.getCode() == KeyCode.TAB) {
            	// If we've gotten a totally new value in the text box - set the autocomplete souce
            	if (hasTextChangedForAutocomplete) {
            		logic.setTextToAutocomplete(commandTextField.getText());
            		hasTextChangedForAutocomplete = false;
            	}
            	
            	// Temporarily disable the text changed listener so that we don't update hasTextChangedForAutocomplete
            	removeTextChangedListener();
            	
            	// Get a new autocompleted command and update the commandTextField
            	String autocompletedCommand = logic.getNextAutocompleteSuggestion();
            	commandTextField.setText(autocompletedCommand);
            	setCaretPositionToEnd();
            	
            	// Add the listener back
            	setTextChangedListener();
            	
            	event.consume();
            	
            } else {
            	return;
            }
        }
    };
    
    private void setTextChangedListener() {
		commandTextField.textProperty().addListener(textChangedListener);
	}
    
    private void removeTextChangedListener() {
    	commandTextField.textProperty().removeListener(textChangedListener);
    }
    
    private void requestFocus() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                commandTextField.requestFocus();
                
            }
        });
    }
    
    //@@author
    /**
     * Sets the command box style to indicate a correct command.
     */
    private void setStyleToIndicateCorrectCommand() {
        commandTextField.getStyleClass().remove("error");
        commandTextField.setText("");
    }

    @Subscribe
    private void handleIncorrectCommandAttempted(IncorrectTaskCommandAttemptedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event,"Invalid command: " + previousCommandTest));
        setStyleToIndicateIncorrectCommand();
        restoreCommandText();
        setCaretPositionToEnd();
    }

    /**
     * Restores the command box text to the previously entered command
     */
    private void restoreCommandText() {
        commandTextField.setText(previousCommandTest);
    }

    /**
     * Sets the command box style to indicate an error
     */
    private void setStyleToIndicateIncorrectCommand() {
        commandTextField.getStyleClass().add("error");
    }
    
    //@@author A0138978E
    /**
     * Sets the caret of the textfield to the end position
     */
    private void setCaretPositionToEnd() {
    	commandTextField.positionCaret(Integer.MAX_VALUE);
    }

}

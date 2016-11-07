package w15c2.tusk.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import w15c2.tusk.model.Alias;

//@@author A0139708W
/*
 * Card for alias list panel
*/
public class AliasCard extends UiPart{
        private static final String FXML = "AliasListCard.fxml";

        @FXML
        private HBox cardPane;
        @FXML
        private Label cardAlias;
        @FXML
        private Label command;
        @FXML
        private Label id;
        @FXML
        private VBox colorTag;

        private Alias alias;
        private int displayedIndex;
        
        private static final String CARDPANE_CSS = "-fx-background-color: rgba(211, 174, 141, 0.5);";
        private static final String CARD_ALIAS_CSS = "-fx-text-fill: rgba(247, 246, 239, 0.7);";
        private static final String ID_CSS = "-fx-text-fill: rgba(244, 244, 244, 1.0);";
        private static final String COMMAND_CSS = ID_CSS;
        private static final String  COLORTAG_CSS = "-fx-background-color: rgb(186, 143, 106)";

        public AliasCard(){
        }
        /**
         * Loads alias information on to an alias card.
         *  
         * @param alias             Alias object to fill up card
         * @param displayedIndex    Index of card
         * @return                  Card with relevant alias info
         */
        public static AliasCard load(Alias alias, int displayedIndex){
            assert alias != null;
            assert displayedIndex >= 0;
            AliasCard card = new AliasCard();
            card.alias = alias;
            card.displayedIndex = displayedIndex;
            return UiPartLoader.loadUiPart(card);
        }
        
        @FXML
        public void initialize() {
            cardAlias.setText(alias.getShortcut());
            command.setText(alias.getSentence());
            id.setText(displayedIndex + ". ");
            
            cardPane.setStyle(CARDPANE_CSS);
        	cardAlias.setStyle(CARD_ALIAS_CSS);
        	command.setStyle(COMMAND_CSS);
        	id.setStyle(ID_CSS);
        	colorTag.setStyle(COLORTAG_CSS);
        }

        public HBox getLayout() {
            return cardPane;
        }

        @Override
        public void setNode(Node node) {
            cardPane = (HBox)node;
        }

        @Override
        public String getFxmlPath() {
            return FXML;
        }
}

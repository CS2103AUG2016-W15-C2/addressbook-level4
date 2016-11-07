package w15c2.tusk.logic;

import javafx.collections.ObservableList;
import w15c2.tusk.model.Alias;
import w15c2.tusk.model.Model;

//@@author A0143107U
/**
 * Replaces the shortcut of an alias with its sentence.
 */
public class ReplaceAlias {
    private Model model;
    
    public ReplaceAlias(Model model){
        this.model = model;
    }
    
    /** 
     * Uses the shortcut of an alias to find its respective sentence
     * @return the sentence of an alias shortcut 
     */ 
    public String getAliasCommandText(String commandText)
    {
        String s = commandText;
        ObservableList<Alias> aliasList = model.getAlias();
        
        // Find the word to check for an alias of by finding the first word in the command
        int indexOfFirstSpace = commandText.indexOf(" ");
        String wordToCheckForAlias = null;
        String restOfCommand = "";
        
        if (indexOfFirstSpace == -1) {
            wordToCheckForAlias = commandText;
            indexOfFirstSpace = 0; // for substring later
        } else {
            wordToCheckForAlias = commandText.substring(0, indexOfFirstSpace);
            restOfCommand = commandText.substring(indexOfFirstSpace);
        }
         
        
        if(!aliasList.isEmpty()){
            for(Alias alias : aliasList){
                if(alias.getShortcut().equals(wordToCheckForAlias)){
                    s = new String(alias.getSentence());
                    s = s.concat(restOfCommand);
                    break;
                }
            }
        }
        return s;
    }
}

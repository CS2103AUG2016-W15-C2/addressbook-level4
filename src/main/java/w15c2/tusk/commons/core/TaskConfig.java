package w15c2.tusk.commons.core;

import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class TaskConfig {

    public static final String DEFAULT_CONFIG_FILE = "taskconfig.json";

    // Config values customizable through config file
    private String appTitle = "Tusk";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "task-userpreferences.json";
    private String tasksFileName = "tasks.xml";
    private String tasksFilePath = "data/" + tasksFileName;
    private String aliasFileName = "alias.xml";
    private String aliasFilePath = "data/" + aliasFileName;
    private String taskManagerName = "Tusk";


    public TaskConfig() {
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    public void setUserPrefsFilePath(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    public String getTasksFilePath() {
        return tasksFilePath;
    }
    
    public void setTasksFilePath(String tasksFilePath) {
        this.tasksFilePath = tasksFilePath;
    }
    
    public String getAliasFilePath() {
        return aliasFilePath;
    }
    
    public void setAliasFilePath(String aliasFilePath) {
        this.aliasFilePath = aliasFilePath;
    }

    public String getTaskManagerName() {
        return taskManagerName;
    }

    public void setTaskManagerName(String taskManagerName) {
        this.taskManagerName = taskManagerName;
    }
    
    //@@author A0138978E
    /**
     * Modifes the task and alias storage file path based on the new storage location provided.
     * @param storageLocation new storage location for task and alias files
     */
    public void setStorageLocation(String storageLocation) {
    	this.setTasksFilePath(Paths.get(storageLocation, tasksFileName).toString());
    	this.setAliasFilePath(Paths.get(storageLocation, aliasFileName).toString());
    }


    @Override
    /**
     * Checks equality with any other task config object by comparing essential fields.
     * @param other the object to compare to
     */
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof TaskConfig)){ //this handles null as well.
            return false;
        }

        TaskConfig o = (TaskConfig)other;

        return Objects.equals(appTitle, o.appTitle)
                && Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(tasksFilePath, o.tasksFilePath)
                && Objects.equals(aliasFilePath, o.aliasFilePath)
                && Objects.equals(taskManagerName, o.taskManagerName);
    }

    //@@author 
    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, tasksFilePath, aliasFilePath, taskManagerName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + tasksFilePath);
        sb.append("\nLocal alias file location : " + aliasFilePath);
        sb.append("\nTask Manager name : " + taskManagerName);
        return sb.toString();
    }

}

# Developer Guide

* [Introduction](#introduction)
* [Setting Up](#setting-up)
* [Design](#design)
* [Implementation](#implementation)
* [Testing](#testing)
* [Dev Ops](#dev-ops)
* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E : Product Survey](#appendix-e-product-survey)
<!--@@author A0139817U-->

## Introduction
Welcome to the <i>Tusk</i> codebase! This guide aims to get you up to speed as soon as possible with the development environment, general architecture and in-depth implementation details, in that order.

Let's get started!
<!--@@author A0139817U-reused -->
## Setting up

#### Prerequisites

1. **JDK `1.8.0_60`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.

2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace


#### Importing the project into Eclipse

1. Fork this repo, and clone the fork to your computer
2. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given in the prerequisites above)
3. Click `File` > `Import`
4. Click `Gradle` > `Gradle Project` > `Next`
5. Click `Browse`, then locate the project's directory
6. Click `Next` > `Finish`

> * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
> * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (This is because Gradle downloads library files from servers during the project set up process)
> * If Eclipse auto-changed any settings files during the import process, you can discard those changes.

<br>
## Design

### Architecture
<img src="images/Architecture.png" width="600"><br>
The **_Architecture Diagram_** given above explains the high-level design of the App.
Given below is a quick overview of each component.

`Main` has only one class called [`MainApp`](../src/main/java/w15c2/tusk/MainApp.java). It is responsible for,
* At app launch: Initializing the components in the correct sequence, and connecting them up with each other.
* At shut down: Shutting down the components and invoking cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.
Three of those classes play important roles at the architecture level.
* `EventsCentre` : Used by components to communicate with other components using events (i.e. a form of _Event Driven_ design) (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
* `LogsCenter` : Used by many classes to write log messages to the App's log file.
* `UniqueItemCollection<T>` : Used to store unique lists of Tasks and Aliases.

The rest of the App consists of four components, where each components defines its API in an interface and implements its functionality in one main class.

Component Name | Purpose | Interface | Implementation |
-------- | :----------- | :----------- |:-----------
[**`UI`**](#ui-component) | Handles the <i>Tusk</i> UI | [`Ui.java`](../src/main/java/w15c2/tusk/ui/Ui.java) | [`UIManager.java`](../src/main/java/w15c2/tusk/ui/UiManager.java)
[**`Logic`**](#logic-component) | Executes commands from the UI | [`Logic.java`](../src/main/java/w15c2/tusk/logic/Logic.java) | [`LogicManager.java`](../src/main/java/w15c2/tusk/logic/LogicManager.java)
[**`Model`**](#model-component) | Holds all required data in-memory | [`Model.java`](../src/main/java/w15c2/tusk/model/task/Model.java) | [`TaskManager.java`](../src/main/java/w15c2/tusk/model/task/TaskManager.java)
[**`Storage`**](#storage-component) | Reads data from, and writes data to, the hard disk. | [`TaskStorage.java`](../src/main/java/w15c2/tusk/storage/task/TaskStorage.java) <br> [`AliasStorage.java`](../src/main/java/w15c2/tusk/storage/task/TaskStorage.java) | [`StorageManager.java`](../src/main/java/w15c2/tusk/storage/StorageManager.java)
<!--@@author A0139708W -->
### Integrated Behavior

The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
command `delete 3`.

<img src="images/SDforDeleteTask.png" width="800">

>Note how the `Model` simply raises a `TaskManagerChangedEvent` when any Task related data is changed,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk. <br>
<img src="images/SDforDeleteTaskEventHandling.png" width="800">

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct
  coupling between components.

The sections more thoroughly explain each component.

<br>

### UI component

<img src="images/UiClassDiagram.png" width="800"><br>

**API** : [`Ui.java`](../src/main/java/w15c2/tusk/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `TaskListPanel`,
`HelpPanel`, etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class
and they can be loaded using the `UiPartLoader`.

The `UI` component uses JavaFX UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/w15c2/tusk/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,
* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

<br>
<!--@@author A0139817U -->
### Logic component

<img src="images/LogicClassDiagram.png" width="800"><br>

**API** : [`Logic.java`](../src/main/java/w15c2/tusk/logic/Logic.java)

The `Logic` component,
* Uses the `TaskCommandsParser` class to call the `ParserSelector` class to select the appropriate parser.
* After the appropriate parser is selected, the parser prepares the `Command` object.
* This results in a `Command` object which is executed by the `LogicManager`.
* The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
* The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeleteTaskSdForLogic.png" width="800"><br>

<br>
<!--@@author A0138978E -->
### Model component

<img src="images/ModelClassDiagram.png" width="800"><br>

**API** : [`Model.java`](../src/main/java/w15c2/tusk/model/Model.java)

The `Model` component,
* stores a `UserPref` object that represents the user's preferences.
* stores the Task Manager data.
* exposes a `UnmodifiableObservableList<Task>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

<br>
<!--@@author A0143107U -->
### Storage component

<img src="images/StorageClassDiagram.png" width="800"><br>

**API** : [`Storage.java`](../src/main/java/w15c2/tusk/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the Task data in xml format and read it back.

<br>
### Common classes

Classes used by multiple components are in the `w15c2.tusk.commons` package.

<br>
## Implementation

### Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations. Take note that:

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration](#configuration))
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level
* Currently log messages are output through: `Console` and to a `.log` file.

**Logging Levels**

* `SEVERE` : Critical problem detected which may possibly cause the termination of the application
* `WARNING` : Warnings that indicate events that are somewhat serious but do not represent critical errors
* `INFO` : Information showing the noteworthy actions by the App
* `FINE` : Details that are not usually noteworthy but may be useful in debugging
  e.g. print the actual list instead of just its size

### Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file
(default: `taskconfig.json`):

### Autocomplete

* `AutocompleteEngine` pulls data from `AutocompleteSource` to match the appropriate command words.
* `AutocompleteResult` creates the iterator to allow for <kbd>TAB</kbd> to cycle through the matched commands.

### Alias

* Aliases are checked when a command is entered against a list of aliases in `Model`
* If a match is found, the alias is replaced before the command is parsed as per normal.

### Command History

* Every command that is entered is added to a list in the `CommandHistory` class.
* When <kbd>UP</kbd> is pressed, index pointer is reduced by one and previous command replaces text in the command box.
* When <kbd>DOWN</kbd> is pressed, index pointer is increased by one and following command replaces text in the command box.

### Undo
* The `ModelHistory` class handles both the undo and redo commands
* When an undo-able command is executed, a `UniqueItemCollection` of the previous state is saved.
* An undo command will revert the state to before the command was executed by replacing the `UniqueItemCollection` in the `Model` class
* Before the execution of an undo command, the current `UniqueItemCollection` is saved to support redo.

<br>
<!--@@author A0139708W-reused -->
## Testing

Tests can be found in the `./src/test/java` folder.

**In Eclipse**:
> If you are not using a recent Eclipse version (i.e. _Neon_ or later), enable assertions in JUnit tests
  as described [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option).

* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`
* To run a subset of tests, right-click on a test package, test class, or a test and choose
  to run as a JUnit test.

**Using Gradle**:
* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle.

We have two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire App by simulating user actions on the GUI.
   These are in the `guitests` package.

2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
   1. _Unit tests_ targeting the lowest level methods/classes. <br>
      e.g. `w15c2.tusk.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `w15c2.tusk.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `w15c2.tusk.logic.LogicManagerTest`

**Headless GUI Testing**:
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the headless mode.
 In the headless mode, GUI tests do not show up on the screen.
 That means that developers may do other things on their computers while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.

<br>
## Dev Ops

### Build Automation

See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

### Continuous Integration

We use [Travis CI](https://travis-ci.org/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) for more details.

### Making a Release

Here are the steps to create a new release.

 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 2. [Crete a new release using GitHub](https://help.github.com/articles/creating-releases/)
    and upload the JAR file your created.

### Managing Dependencies

A project often depends on third-party libraries. For example, Task Manager depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repository (this bloats the repository size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

<br>
<!--@@author A0139817U -->
## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`


Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see usage instructions | refer to instructions when I forget how to use the App
`* * *` | user | add a task with a simple description | record general tasks
`* * *` | user | add a task with a simple description and a deadline | record tasks that need to be done by a deadline
`* * *` | user | add a task with a simple description, a start date, an end date and times | record tasks that have a date range
`* * *` | user | search for tasks using their descriptions | look up a task quickly
`* * *` | user | delete a task | get rid of tasks that I no longer care to track
`* * *` | user | update tasks | change details of a task if they change or if I added wrongly
`* * *` | user | complete tasks | know which tasks are yet to be done
`* * *` | user | view all the tasks that I have created on a GUI | have a good overall picture of the tasks
`* * *` | user | specify the location of the file containing my task data | choose to store the data locally or in the cloud
`* * *` | user who has just executed a wrong command | undo the command | rectify my mistakes easily
`* *` | user | set aliases for certain keywords | type commands faster
`* *` | user | pin a particular task | see the tasks that have a higher priority
`*` | user | autocomplete the commands that I am typing | type commands faster

<br>
<!--@@author A0139708W -->
## Appendix B : Use Cases

(For all use cases below, the **System** is the `TaskManager` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Adding a task

**MSS**

1. User adds a task
2. <i>Tusk</i> adds the task<br>
Use case ends.

**Extensions**

1a. The format of the input is wrong

> 1a1. <i>Tusk</i> shows an error and prompts the user again reiterating the correct command format <br>
> Use Case resumes at step 1.

1b. The task limit has been reached

> 1b1. <i>Tusk</i> displays an error and prompts the user to delete a task <br>
> Use Case ends.

<br>
#### Use case: Deleting a task

**MSS**

1. User requests to list tasks
2. <i>Tusk</i> shows a list of tasks
3. User requests to delete a specific task in the list
4. <i>Tusk</i> deletes the task <br>
Use case ends.

**Extensions**

1a. The list of tasks is empty

> Use Case ends.

3a. The given index is invalid

> 3a1. <i>Tusk</i> displays an error <br>
> Use Case ends.

<br>
#### Use case: Undoing a command

**MSS**

1. User undos a command.
2. <i>Tusk</i> returns to state before command is executed.
3. <i>Tusk</i> displays that command that has been undone.<br>
Use case ends.

**Extensions**

1a. There is no command to be undone

> 1a1. <i>Tusk</i> displays an error <br>
> Use Case ends.

<br>
#### Use case: Adding an Alias

**MSS**

1. User adds an Alias
2. <i>Tusk</i> adds Alias<br>
Use case ends.

**Extensions**

1a. The format of the input is wrong

> 1a1. <i>Tusk</i> shows an error and prompts the user again reiterating the correct command format <br>
> Use Case resumes at step 1.

1b. The user attempts to create an alias of a pre-existing alias

> 1b1. <i>Tusk</i> displays an error <br>
> Use Case continues at step 1.

<br>
#### Use case: Updating a task

**MSS**

1. User lists all tasks
2. <i>Tusk</i> displays all tasks
3. User updates a task
4. <i>Tusk</i> updates the specified task<br>
Use case ends.

**Extensions**

1a. The format of the input is wrong

> 1a1. <i>Tusk</i> shows an error and prompts the user again reiterating the correct command format <br>
> Use Case resumes at step 1.

1a. The list of tasks is empty

> Use Case ends.

3a. The given index is invalid

> 3a1. <i>Tusk</i> displays an error <br>
> Use Case resumes at step 3.

<br>
#### Use case: Marking a task as pinned

**MSS**

1. User lists all tasks
2. <i>Tusk</i> displays all tasks
3. User pins a task
4. <i>Tusk</i> pins the specified task<br>
Use case ends.

**Extensions**

1a. The format of the input is wrong

> 1a1. <i>Tusk</i> shows an error and prompts the user again reiterating the correct command format <br>
> Use Case resumes at step 1.

1a. The list of tasks is empty

> Use Case ends.

3a. The given index is invalid

> 3a1. <i>Tusk</i> displays an error <br>
> Use Case resumes at step 3.

<br>
#### Use case: Clearing list of tasks

**MSS**

1. User clears all tasks
2. <i>Tusk</i> clears all tasks <br>
Use case ends.

**Extensions**

1a. The format of the input is wrong

> 1a1. <i>Tusk</i> shows an error and prompts the user again reiterating the correct command format <br>
> Use Case resumes at step 1.

1a. There are no tasks

> Use Case ends.

<br>
#### Use case: Autocomplete a Command

**MSS**

1. User types in part of a command
2. User presses <kbd>TAB</kbd>
3. <i>Tusk</i> completes the command<br>
Use case ends.

**Extensions**

3a. There is no command to autocomplete

> Use Case ends.

<br>
#### Use case: Complete a task

**MSS**

1. User requests to list tasks
2. <i>Tusk</i> shows a list of tasks
3. User requests to complete a specific task in the list
4. <i>Tusk</i> completes the task <br>
Use case ends.

**Extensions**

3a. The format of the input is wrong.

> 3a1. <i>Tusk</i> shows an error and prompts the user again reiterating the correct command format <br>
> Use Case resumes at step 1.

3a. The task is already completed.

> 3a1. <i>Tusk</i> shows that the task has already been completed. <br>
> Use Case ends.

<br>
#### Use case: Find a task

**MSS**

1. User requests to find a task
2. <i>Tusk</i> shows a list of tasks that match the keywords provided<br>
Use case ends.

**Extensions**

2a. The given keywords do not match any tasks.

> 3a1. <i>Tusk</i> shows an empty list and informs the user that no tasks were found <br>
> Use Case ends.

<br>
#### Use case: Change Storage location

**MSS**

1. User requests to change storage location
2. <i>Tusk</i> changes storage location
Use case ends.

**Extensions**

1a. The format of the input is wrong.

> 3a1. <i>Tusk</i> shows an error and prompts the user again reiterating the correct command format <br>
> Use Case resumes at step 1.

<br>
## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS](#mainstream-os) as long as it has Java `1.8.0_60` or higher installed.
2. Should be able to hold up to 1000 tasks.
3. Should come with automated unit tests and open source code.
4. Should process a user's request within 3 seconds.
5. Should be available 24/7.

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

<br>
<!--@@author A0143107U -->
## Appendix E : Product Survey

Task Managers | Google Calender | Wunderlist | HabitRPG | Ours
-------- | :-------- | :-------- | :-------- | :--------
Basic task manager features (CRUD) | Available | Available | Available (limited) | Available
Quick add | Available | Not available | Available | Available | Available
Undo | Available | Available | Available | Available
Internet connectivity required | Required | Required | Required | Not required
Sync across multiple devices | Available | Available | Available | Available
Extra features | Customizing reminders for tasks | Favoriting tasks <br> Tagging tasks | Tagging tasks | Favoriting tasks <br> Setting aliases

Looking at the above feature comparisons, our product covers most of the basic features for a user to manage his/her tasks efficiently. While there are some features in competing products that we have not implemented, it is not a cause for concern. We are targeting a specific group of power users who wants to manage tasks quickly through the usage of a command-line interface.


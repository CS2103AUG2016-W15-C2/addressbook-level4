# A0139708Wreused
###### /UsingGradle.md
``` md
# Using Gradle

[Gradle](https://gradle.org/) is a build automation tool. It can automate build-related tasks such as 
* Running tests
* Managing library dependencies
* Analyzing code for style compliance

The gradle configuration for this project is defined in the _build script_  [`build.gradle`](../build.gradle). 
> To learn more about gradle build scripts,
refer [Build Scripts Basics](https://docs.gradle.org/current/userguide/tutorial_using_tasks.html).

## Running Gradle Commands

To run a Gradle command, open a command window on the project folder and enter the Gradle command.
Gradle commands look like this:
* On Windows :`gradlew <task1> <task2> ...` e.g. `gradlew clean allTests`
* On Mac/Linux: `./gradlew <task1> <task2>...`  e.g. `./gradlew clean allTests`

> If you do not specify any tasks, Gradlew will run the default tasks `clean` `headless` `allTests` `coverage`

## Cleaning the Project

* **`clean`** <br>
  Deletes the files created during the previous build tasks (e.g. files in the `build` folder).<br>
  e.g. `./gradlew clean`
  
  >**Tip `clean` to force Gradle to execute a task**: <br>
  When running a Gradle task, Gradle will try to figure out if the task needs running at all. 
  If Gradle determines that the output of the task will be same as the previous time, it will not run
  the task. For example, it will not build the JAR file again if the relevant source files have not changed
  since the last time the JAR file was built. If we want to force Gradle to run a task, we can combine
  that task with `clean`. Once the build files have been `clean`ed, Gradle has no way to determine if
  the output will be same as before, so it will be forced to execute the task.
  
## Creating the JAR file

* **`shadowJar`** <br>
  Creates the `tusk.jar` file in the `build/jar` folder, _if the current file is outdated_.<br>
  e.g. `./gradlew shadowJar`

  > To force Gradle to create the JAR file even if the current one is up-to-date, you can '`clean`' first. <br>
    e.g. `./gradlew clean shadowJar` 

**Note: Why do we create a fat JAR?**
If we package only our own class files into the JAR file, it will not work properly unless the user has all the other
  JAR files (i.e. third party libraries) our classes depend on, which is rather inconvenient. 
  Therefore, we package all dependencies into a single JAR files, creating what is also known as a _fat_ JAR file. 
  To create a fat JAR fil, we use the Gradle plugin [shadow jar](https://github.com/johnrengelman/shadow).

## Running Tests

* **`allTests`**<br>
  Runs all tests.

* **`guiTests`**<br>
  Runs all tests in the `guitests` package
  
* **`nonGuiTests`**<br>
  Runs all non-GUI tests in the `w15c2.tusk` package
  
* **`headless`**<br>
  Sets the test mode as _headless_. 
  The mode is effective for that Gradle run only so it should be combined with other test tasks.
  
Here are some examples:

* `./gradlew headless allTests` -- Runs all tests in headless mode
* `./gradlew clean nonGuiTests` -- Cleans the project and runs non-GUI tests


## Updating Dependencies

There is no need to run these Gradle tasks manually as they are called automatically by other 
relevant Gradle tasks.

* **`compileJava`**<br>
 Checks whether the project has the required dependencies to compile and run the main program, and download 
 any missing dependencies before compiling the classes.<br>
 See `build.gradle` -> `allprojects` -> `dependencies` -> `compile` for the list of dependencies required.

* **`compileTestJava`**<br>
  Checks whether the project has the required dependencies to perform testing, and download 
  any missing dependencies before compiling the test classes.<br>
  See `build.gradle` -> `allprojects` -> `dependencies` -> `testCompile` for the list of 
  dependencies required.
```
###### /DeveloperGuide.md
``` md
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

A project often depends on third-party libraries. For example, Tusk depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repository (this bloats the repository size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

<br>
```
###### /UsingTravis.md
``` md
# Travis CI

[Travis CI](https://travis-ci.org/) is a _Continuous Integration_ platform for GitHub projects.

Travis CI can run the projects' tests automatically whenever new code is pushed to the repo.
This ensures that existing functionality and features have not been broken by the changes.

The current Travis CI set up performs the following things whenever someone push code to the repo:
  * Runs the `./gradlew clean headless allTests coverage coveralls -i` command
    (see [UsingGradle.md](UsingGradle.md) for more details on what this command means).
  * Automatically retries the build up to 3 times if a task fails.

If you would like to customise your travis build further, you can learn more about Travis
from [Travis CI Documentation](https://docs.travis-ci.com/).

## Setting up Travis CI

1. Fork the repo to your own organization.
2. Go to https://travis-ci.org/ and click `Sign in with GitHub`, then enter your GitHub account details if needed.<br>
![Signing into Travis CI](images/signing_in.png)

3. Head to the [Accounts](https://travis-ci.org/profile) page, and find the switch for the forked repository.
    * If the organization is not shown, click `Review and add` as shown below: <br>
      ![Review and add](images/review_and_add.png)<br>
      This should bring you to a GitHub page that manages the access of third-party applications.
      Depending on whether you are the owner of the repository, you can either grant access
      ![Grant Access](images/grant_access.png)<br>
      or request access<br>
      ![Request Access](images/request_access.png)<br>
      to Travis CI so that it can access your commits and build your code.
    
    * If repository cannot be found, click `Sync account`
4. Activate the switch.<br>
   ![Activate the switch](images/flick_repository_switch.png)
5. This repo comes with a [`.travis.yml`](.travis.yml) that tells Travis what to do.
   So there is no need for you to create one yourself.
6. To see the CI in action, push a commit to the master branch!  
    * Go to the repository and see the pushed commit. There should be an icon which will link you to the Travis build.<br>
      ![Commit build](images/build_pending.png)

    * As the build is run on a provided remote machine, we can only examine the logs it produces:<br>
      ![Travis build](images/travis_build.png)

7. If the build is successful, you should be able to check the coverage details of the tests
   at [Coveralls](http://coveralls.io/)
8. Update the link to the 'build status' badge at the top of the `README.md` to point to the build status of your
   own repo.
```

package seedu.address;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp_Task {

//    public static final String SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
//    protected static final String DEFAULT_PREF_FILE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("pref_testing.json");
//    public static final String APP_TITLE = "Test App";
//    protected static final String ADDRESS_BOOK_NAME = "Test";
//    protected Supplier<UniqueItemCollection<Task>> initialDataSupplier = () -> null;
//    protected String saveFileLocation = SAVE_LOCATION_FOR_TESTING;
//
//    public TestApp() {
//    }
//
//    public TestApp(Supplier<UniqueItemCollection<Task>> initialDataSupplier, String saveFileLocation) {
//        super();
//        this.initialDataSupplier = initialDataSupplier;
//        this.saveFileLocation = saveFileLocation;
//
//        // If some initial local data has been provided, write those to the file
//        if (initialDataSupplier.get() != null) {
//        	// TODO: Commented out some testutil stuff
//        	/*
//            TestUtil.createDataFileWithData(
//                    new XmlSerializableTaskManager(this.initialDataSupplier.get()),
//                    this.saveFileLocation);
//                    */
//        }
//    }
//
//    @Override
//    protected TaskConfig initConfig(String configFilePath) {
//        TaskConfig config = super.initConfig(configFilePath);
//        config.setAppTitle(APP_TITLE);
//        config.setAddressBookFilePath(saveFileLocation);
//        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
//        config.setAddressBookName(ADDRESS_BOOK_NAME);
//        return config;
//    }
//
//    @Override
//    protected UserPrefs initPrefs(TaskConfig config) {
//        UserPrefs userPrefs = super.initPrefs(config);
//        double x = Screen.getPrimary().getVisualBounds().getMinX();
//        double y = Screen.getPrimary().getVisualBounds().getMinY();
//        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
//        return userPrefs;
//    }
//
//
//    @Override
//    public void start(Stage primaryStage) {
//        ui.start(primaryStage);
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
}

package w15c2.tusk.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import w15c2.tusk.commons.core.LogsCenter;
import w15c2.tusk.commons.exceptions.DataConversionException;
import w15c2.tusk.commons.util.FileUtil;
import w15c2.tusk.model.UserPrefs;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class JsonUserPrefsStorage implements UserPrefsStorage{

    private static final Logger logger = LogsCenter.getLogger(JsonUserPrefsStorage.class);

    private String filePath;

    public JsonUserPrefsStorage(String filePath){
        this.filePath = filePath;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return readUserPrefs(filePath);
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        saveUserPrefs(userPrefs, filePath);
    }

    /**
     * Similar to {@link #readUserPrefs()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<UserPrefs> readUserPrefs(String prefsFilePath) throws DataConversionException {
        assert prefsFilePath != null;

        File prefsFile = new File(prefsFilePath);

        if (!prefsFile.exists()) {
            logger.info("Prefs file "  + prefsFile + " not found");
            return Optional.empty();
        }

        UserPrefs prefs;

        try {
            prefs = FileUtil.deserializeObjectFromJsonFile(prefsFile, UserPrefs.class);
        } catch (IOException e) {
            logger.warning("Error reading from prefs file " + prefsFile + ": " + e);
            throw new DataConversionException(e);
        }
        return Optional.of(prefs);
    }

    /**
     * Similar to {@link #saveUserPrefs(UserPrefs)}
     * @param prefsFilePath location of the data. Cannot be null.
     */
    private void saveUserPrefs(UserPrefs userPrefs, String prefsFilePath) throws IOException {
        assert userPrefs != null;
        assert prefsFilePath != null;

        FileUtil.serializeObjectToJsonFile(new File(prefsFilePath), userPrefs);
    }
}

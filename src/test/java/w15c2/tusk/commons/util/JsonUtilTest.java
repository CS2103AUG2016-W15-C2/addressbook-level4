package w15c2.tusk.commons.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import w15c2.tusk.commons.util.FileUtil;
import w15c2.tusk.commons.util.JsonUtil;
import w15c2.tusk.testutil.SerializableTestClass;
import w15c2.tusk.testutil.TestUtil;

//@@author A0139708W
/**
 * Tests JSON Read and Write.
 */
public class JsonUtilTest {
    private static final File SERIALIZATION_FILE = new File(TestUtil.getFilePathInSandboxFolder("serialize.json"));

    @Test 
    public void jsonUtil_readJsonStringToObjectInstance_correctObject() throws IOException {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();
        assertEquals(JsonUtil.fromJsonString(FileUtil.readFromFile(SERIALIZATION_FILE), SerializableTestClass.class)
                .JSON_STRING_REPRESENTATION,SerializableTestClass.JSON_STRING_REPRESENTATION);
        
    }

    @Test 
    public void jsonUtil_writeThenReadObjectToJson_correctObject() throws IOException {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();
        assertEquals(JsonUtil.toJsonString(serializableTestClass),SerializableTestClass.JSON_STRING_REPRESENTATION);
    }

}

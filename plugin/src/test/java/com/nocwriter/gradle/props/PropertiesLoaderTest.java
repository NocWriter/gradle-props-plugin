package com.nocwriter.gradle.props;

import com.nocwriter.Utils;
import org.gradle.internal.extensibility.DefaultExtraPropertiesExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Test suite for {@link PropertiesLoader}.
 *
 * @author Guy Raz Nir
 * @since 2022/04/14
 */
public class PropertiesLoaderTest {

    /**
     * Loader to test with.
     */
    @SuppressWarnings("FieldCanBeLocal")
    private PropertiesLoader loader;

    /**
     * Test fixture -- creates a new loader.
     */
    public void setUp() {
        Path classpathBase = Paths.get(Utils.getAbsolutePath("/"));
        loader = new PropertiesLoader(classpathBase, new DefaultExtraPropertiesExtension());
    }

    /**
     * Test should parse successfully a custom properties file.
     * The file contains standard key=value properties, comment lines and empty property (key=<nothing>).
     */
    @Test
    @DisplayName("Test should load and parse properties file successfully")
    public void shouldParsePropertiesFileSuccessfully() throws IOException {
        String path = Utils.getAbsolutePath("/PropertiesLoaderProps.properties");
        Map<String, String> props = PropertiesLoader.readProperties(Paths.get(path));

        Assertions.assertEquals(props.size(), 3);
        Assertions.assertEquals("data1", props.get("prop1"));
        Assertions.assertEquals("data2", props.get("prop2"));
        Assertions.assertFalse(props.containsKey("prop3"));
        Assertions.assertEquals("", props.get("prop4"));
    }
}

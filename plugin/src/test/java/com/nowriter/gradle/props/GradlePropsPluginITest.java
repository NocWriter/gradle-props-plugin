/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.nowriter.gradle.props;

import com.nowriter.Utils;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtraPropertiesExtension;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * An integration test for the 'com.nowriter.gradle.props' plugin.
 *
 * @author Guy Raz Nir
 * @since 2022/04/14
 */
@SuppressWarnings("FieldCanBeLocal")
class GradlePropsPluginITest {

    /**
     * Test project.
     */
    private Project project;

    /**
     * Plugin under test.
     */
    private Plugin<Project> plugin;

    /**
     * Extension to test.
     */
    private PropertiesExt ext;

    /**
     * Project's extra properties set.
     */
    private ExtraPropertiesExtension extraProps;

    /**
     * Test fixture -- generate test project, apply plugin and extract extension.
     */
    @SuppressWarnings("unchecked")
    @BeforeEach
    public void setUp() {
        // Create a test project.
        project = ProjectBuilder.builder().build();
        plugin = project.getPlugins().apply("com.nowriter.gradle.props");
        ext = (PropertiesExt) project.getExtensions().getByName("propertySources");
        extraProps = project.getExtensions().getExtraProperties();
    }

    /**
     * Test should ignore an optional non-existing properties file.
     */
    @Test
    @DisplayName("Test should fail silently on non existing optional file")
    void shouldFailSilentlyOnMissingOptionalFile() {
        ext.optional("nonExistingFile");
    }

    /**
     * Test should fail missing mandatory (required) properties file.
     */
    @Test
    @DisplayName("Test should fail on non existing required file")
    public void shouldFailOnMissingRequiredFile() {
        Assertions.assertThrows(MissingPropertiesFileException.class, () -> ext.required("nonExistingFile"));
    }

    /**
     * Test should load properties into Gradle's project extra properties set.
     */
    @Test
    @DisplayName("Test should load properties into extra properties.")
    public void shouldLoadPropertiesIntoProjectExtraProperties() {
        final String SAMPLE_PROPERTIES_FILE = "/sample.properties";
        final String SAMPLE_PROP_NAME = "sampleProp";
        final String SAMPLE_PROP_VALUE = "sampleData";

        String path = Utils.getAbsolutePath(SAMPLE_PROPERTIES_FILE);

        // Make sure our sample property does not exist.
        Assertions.assertFalse(extraProps.has(SAMPLE_PROP_NAME));

        // Load properties.
        ext.required(path);

        // Make sure our property was loaded and contains expected value.
        Assertions.assertTrue(extraProps.has(SAMPLE_PROP_NAME));
        Assertions.assertEquals(SAMPLE_PROP_VALUE, extraProps.get(SAMPLE_PROP_NAME));
    }

    /**
     * Test should load properties into system properties set ({@link System#getProperties()}).
     */
    @Test
    @DisplayName("Test should load properties into system properties set.")
    public void shouldLoadPropertiesIntoSystemPropertiesSet() {
        final String SAMPLE_PROPERTIES_FILE = "/sample.properties";
        final String SAMPLE_PROP_NAME = "sampleProp";
        final String SAMPLE_PROP_VALUE = "sampleData";

        String path = Utils.getAbsolutePath(SAMPLE_PROPERTIES_FILE);

        // Make sure our sample property does not exist.
        Assertions.assertFalse(extraProps.has(SAMPLE_PROP_NAME));

        // Load properties.
        ext.requiredSystemProperties(path);

        // Make sure our property was loaded and contains expected value.
        Assertions.assertTrue(System.getProperties().containsKey(SAMPLE_PROP_NAME));
        Assertions.assertEquals(SAMPLE_PROP_VALUE, System.getProperty(SAMPLE_PROP_NAME));
    }
}

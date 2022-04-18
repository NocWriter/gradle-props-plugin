package com.nowriter.gradle.props;

import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("SameParameterValue")
public class BaseGradlePropsPluginFunctionTest {

    /**
     * Temporary project directory created for the test.
     */
    @TempDir
    protected Path projectDir;

    /**
     * Generate {@code Path} object referring a file in the project's directory.
     *
     * @param filename Filename to generate for.
     * @return A new {@code File} referencing a file which its parent directory is {@link #projectDir}.
     */
    protected Path getProjectFile(String filename) {
        return projectDir.resolve(filename);
    }

    /**
     * @return Project build file (file may not exist in practice).
     */
    protected Path getBuildFile() {
        return getProjectFile("build.gradle.kts");
    }

    /**
     * @return Project settings file (file may not exist in practice).
     */
    protected Path getSettingsFile() {
        return getProjectFile("settings.gradle.kts");
    }

    /**
     * @return A path to default <i>gradle.properties</i> file.
     */
    protected Path getGradlePropertiesFile() {
        return getProjectFile("gradle.properties");
    }

    /**
     * Use given Gradle settings file (from classpath) for testing.
     *
     * @param settingsScriptFile Relative classpath path for settings to use.
     * @throws IOException If file could not be read.
     */
    protected void useSettingsScript(String settingsScriptFile) throws IOException {
        copyClasspathResource(settingsScriptFile, getSettingsFile());
    }

    /**
     * Use given Gradle build script for testing, within classpath.
     *
     * @param buildScriptPath Classpath relative reference to build script.
     * @throws IOException If file could not be read.
     */
    protected void useBuildScript(String buildScriptPath) throws IOException {
        copyClasspathResource(buildScriptPath, getBuildFile());
    }

    /**
     * Write contents to <i>settings.gradle.kts</i> file.
     *
     * @param contents Contents to write.
     * @throws IOException If file could not be written to.
     */
    protected void writeSettingsFile(String contents) throws IOException {
        writeString(getSettingsFile(), contents);
    }

    /**
     * Write contents to <i>gradle.build.kts</i> file.
     *
     * @param contents Contents to write.
     * @throws IOException If file could not be written to.
     */
    protected void writeBuildScript(String contents) throws IOException {
        writeString(getBuildFile(), contents);
    }

    /**
     * Write contents to <i>gradle.properties</i> file.
     *
     * @param contents Contents to write.
     * @throws IOException If file could not be written to.
     */
    protected void writeGradlePropertiesFile(String contents) throws IOException {
        writeString(getGradlePropertiesFile(), contents);
    }

    /**
     * Copy classpath resource into temporary project file.
     *
     * @param resourcePath      Classpath resource to read from.
     * @param projectTargetFile Target file (within project directory) to write to.
     * @throws IOException If any I/O error occurred during copy.
     */
    @SuppressWarnings("SameParameterValue")
    protected void copyClasspathResource(String resourcePath, String projectTargetFile) throws IOException {
        copyClasspathResource(resourcePath, getProjectFile(projectTargetFile));
    }

    /**
     * Copy classpath resource into temporary project file.
     *
     * @param resourcePath Classpath resource to read from.
     * @param targetPath   A target file to write to.
     * @throws IOException If any I/O error occurred during copy.
     */
    protected void copyClasspathResource(String resourcePath, Path targetPath) throws IOException {
        try (InputStream in = openClasspathResource(resourcePath)) {
            Files.copy(in, targetPath);
        }
    }

    /**
     * Open classpath resource to read from.
     *
     * @param resourcePath Resource path to read from. Should start with "/".
     * @return String with resource contents.
     * @throws FileNotFoundException If resource could not be read.
     */
    protected static InputStream openClasspathResource(String resourcePath) throws FileNotFoundException {
        InputStream in = GradlePropsPluginFunctionalTest.class.getResourceAsStream(resourcePath);
        if (in == null) {
            throw new FileNotFoundException("Classpath resource not found: " + resourcePath);
        }
        return in;
    }

    /**
     * Read class path resource as a string.
     *
     * @param resourcePath Resource path to read from. Should start with "/".
     * @return String with resource contents.
     * @throws RuntimeException If resource could not be read.
     */
    protected static String readClasspathResource(String resourcePath) throws IOException {
        try (InputStream in = openClasspathResource(resourcePath)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            byte[] buf = new byte[in.available()];
            //noinspection ResultOfMethodCallIgnored
            in.read(buf);
            return new String(buf);
        }
    }

    /**
     * Write contents as string to target path.
     *
     * @param path     Path of file to write to.
     * @param contents Contents to write.
     * @throws IOException If contents could not be written.
     */
    protected static void writeString(Path path, String contents) throws IOException {
        Files.write(path, contents.getBytes());
    }
}

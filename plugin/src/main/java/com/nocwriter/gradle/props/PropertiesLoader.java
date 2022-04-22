package com.nocwriter.gradle.props;

import org.gradle.api.Project;
import org.gradle.api.plugins.ExtraPropertiesExtension;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Loads properties into Gradle's project extra-properties set.
 *
 * @author Guy Raz Nir
 * @since 2022/04/14
 */
public class PropertiesLoader {

    /**
     * Project's directory. Use to resolve relative properties' path.
     */
    private final Path projectDir;

    /**
     * Gradle project's extra-properties extension to load properties into.
     */
    private final ExtraPropertiesExtension extraPropertiesExtension;

    /**
     * Class constructor.
     *
     * @param projectDir Gradle project directory.
     * @param ext        Gradle project extra properties.
     */
    protected PropertiesLoader(Path projectDir, ExtraPropertiesExtension ext) {
        Assertions.notNull(projectDir, "Project directory cannot be null.");
        Assertions.notNull(ext, "Extra properties set cannot be null.");

        this.projectDir = projectDir;
        this.extraPropertiesExtension = ext;
    }

    /**
     * Factory for creating a new properties' loader from a given Gradle project.
     *
     * @param project Gradle project.
     * @return New properties' loader.
     */
    public static PropertiesLoader create(Project project) {
        Assertions.notNull(project, "Project cannot be null.");
        return new PropertiesLoader(project.getProjectDir().toPath(), project.getExtensions().getExtraProperties());
    }

    /**
     * Load properties as project extra-properties set.
     *
     * @param properties Properties to load into extra-properties set.
     */
    public void setExtraProperties(Map<String, String> properties) {
        properties.forEach(extraPropertiesExtension::set);
    }

    /**
     * Load properties as system properties.
     *
     * @param properties Properties to load into system properties set.
     */
    public void setSystemProperties(Map<String, String> properties) {
        properties.forEach(System::setProperty);
    }

    /**
     * Load properties from file.
     *
     * @param pathname         Path to the file. Maybe either absolute or relative.
     * @param optional         {@code true} if file is optional, {@code false} if mandatory.
     * @param systemProperties {@code true} if properties should be loaded as system properties, {@code false} if
     *                         should be loaded in project's extra properties set.
     * @throws MissingPropertiesFileException If file is mandatory and is missing.
     * @throws PropertiesIOException          If properties file exist but could not be read.
     */
    public void loadFromPropertiesFile(String pathname, boolean optional, boolean systemProperties)
            throws MissingPropertiesFileException, PropertiesIOException {
        Path path = toNormalizedPath(pathname);

        try {
            Map<String, String> props = readProperties(path);

            if (systemProperties) {
                props.forEach(System::setProperty);
                System.out.printf("Loaded properties from file %s into system properties set.", path);
            } else {
                props.forEach(this.extraPropertiesExtension::set);
                System.out.printf("Loaded properties from file %s into project's extra properties set.", path);
            }
        } catch (FileNotFoundException | NoSuchFileException ex) {
            if (!optional) {
                throw new MissingPropertiesFileException("Missing mandatory properties file: " + path);
            }
        } catch (IOException ex) {
            throw new PropertiesIOException("Could not read properties file " + pathname, ex);
        }
    }

    /**
     * Normalize a given <i>pathname</i>. If the path is absolute (e.g.: <code>/etc/app/conf.properties</code>) then
     * it is returned as is. Otherwise, if it is relative (e.g.: <code>default.properties</code>,
     * <code>../conf/local.properties</code>,....) then it is resolved relative to Gradle project directory.
     *
     * @param pathname Path to normalize.
     * @return Normalized path.
     */
    protected Path toNormalizedPath(String pathname) {
        Assertions.notNull(pathname, "Pathname cannot be null.");
        return projectDir.resolve(Paths.get(pathname)).normalize();
    }

    /**
     * Read properties from a given <i>path</i>.
     *
     * @param path Path to read from.
     * @return Key/value map containing properties.
     * @throws FileNotFoundException If properties file could not be found.
     * @throws IOException           If any I/O error occurred.
     */
    protected static Map<String, String> readProperties(Path path) throws FileNotFoundException, IOException {
        Properties props = new Properties();
        props.load(Files.newInputStream(path));

        // Convert Properties to simple Map<String, String>.
        // Can be done simply by: return new HashMap<String, String>((Map)props), but it may raise errors.
        Map<String, String> map = new HashMap<>(props.size());
        props.forEach((key, value) -> map.put((String) key, (String) value));
        return map;
    }
}

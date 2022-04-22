package com.nocwriter.gradle.props;

/**
 * Plugin properties extension.
 *
 * @author Guy Raz Nir
 * @since 2022/04/14
 */
public class PropertiesExt {

    /**
     * Properties loader.
     */
    private final PropertiesLoader loader;

    /**
     * Class constructor.
     *
     * @param loader Properties loader to use.
     */
    public PropertiesExt(PropertiesLoader loader) throws IllegalArgumentException {
        Assertions.notNull(loader, "Loader cannot be null.");
        this.loader = loader;
    }

    /**
     * Load properties file. Calling this method makes the properties mandatory.
     *
     * @param pathname Path to properties file. If path is considered absolute (e.g.: <code>/opt/values.properties</code> for
     *                 Linux-based filesystem or <code>c:\conf\values.properties</code> for Windows-based filesystem),
     *                 the path is treated as-is. If path is non absolute (e.g.: <code>local.properties</code> or
     *                 <code>../conf/local.properties</code>), then the path is relative to Gradle's project directory.
     * @throws MissingPropertiesFileException If file could not be found.
     * @throws PropertiesIOException          If properties file exist but could not be read or parsed.
     */
    public void required(String pathname) throws MissingPropertiesFileException, PropertiesIOException {
        addSource(pathname, false, false);
    }

    /**
     * Load properties file. File is skipped if it does not exist.
     *
     * @param pathname Path to properties file. If path is considered absolute (e.g.: <code>/opt/values.properties</code> for
     *                 Linux-based filesystem or <code>c:\conf\values.properties</code> for Windows-based filesystem),
     *                 the path is treated as-is. If path is non absolute (e.g.: <code>local.properties</code> or
     *                 <code>../conf/local.properties</code>), then the path is relative to Gradle's project directory.
     * @throws PropertiesIOException If properties file exist but could not be read or parsed.
     */
    public void optional(String pathname) {
        addSource(pathname, true, false);
    }

    /**
     * Load properties file. Calling this method makes the properties mandatory.
     *
     * @param pathname Path to properties file. If path is considered absolute (e.g.: <code>/opt/values.properties</code> for
     *                 Linux-based filesystem or <code>c:\conf\values.properties</code> for Windows-based filesystem),
     *                 the path is treated as-is. If path is non absolute (e.g.: <code>local.properties</code> or
     *                 <code>../conf/local.properties</code>), then the path is relative to Gradle's project directory.
     * @throws MissingPropertiesFileException If file could not be found.
     * @throws PropertiesIOException          If properties file exist but could not be read or parsed.
     */
    public void requiredSystemProperties(String pathname) throws MissingPropertiesFileException, PropertiesIOException {
        addSource(pathname, false, true);
    }

    /**
     * Load properties file. File is skipped if it does not exist.
     *
     * @param pathname Path to properties file. If path is considered absolute (e.g.: <code>/opt/values.properties</code> for
     *                 Linux-based filesystem or <code>c:\conf\values.properties</code> for Windows-based filesystem),
     *                 the path is treated as-is. If path is non absolute (e.g.: <code>local.properties</code> or
     *                 <code>../conf/local.properties</code>), then the path is relative to Gradle's project directory.
     * @throws PropertiesIOException If properties file exist but could not be read or parsed.
     */
    public void optionalSystemProperties(String pathname) {
        addSource(pathname, true, true);
    }

    /**
     * Load properties file into the project's properties set.
     *
     * @param pathname         Path to properties file. Either absolute or project's directory relative.
     * @param optional         {@code true} if optional, {@code false} if mandatory.
     * @param systemProperties {@code true} if properties should be loaded as system properties, {@code false} to
     *                         load into project's extra properties set.
     * @throws MissingPropertiesFileException If <i>optional</i> is {@code false} (file is mandatory) and the file
     *                                        could not be found.
     * @throws PropertiesIOException          If properties file exist but could not be read or parsed.
     */
    private void addSource(String pathname, boolean optional, boolean systemProperties) {
        Assertions.notNull(pathname, "Properties file pathname cannot be null.");
        loader.loadFromPropertiesFile(pathname, optional, systemProperties);
    }
}

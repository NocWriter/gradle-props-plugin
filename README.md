Gradle properties plugin
========================

General
-------
Gradle plugin that imports properties from external file (other than `./gradle.properties`) into the project.
The plugin support mandatory and optional properties and both project extra properties and system properties.

Usage
-----
Gradle support two major property sets:

* Project properties (defined either by command-line, system properties, environment or `./gradle.properties` file).
* Extra properties set (custom properties that may be added by various plugins and tasks).

To import to either sets, simply include the plugin in your Gradle build script and configure it:

    // Kotlin DSL
    plugins {
        id("com.nocwriter.gradle.props")
    }

    propertySources {
        // Load into project extra properties.
        required("./default.properties")
        optional("./local.properties")
        optional("./prod.properties")

        // Load into system properties.
        requiredSystemProperties("/.default_system_props.properties")
        optionalSystemProperties("/.production_sys_props.properties")
    }

You can than easily access properties in various ways:

    // Kotlin DSL

    // Fetch 'sampleProp' property from project's extra property set.
    val sampleProp: String by project.extra

    // Fetch 'sampleSysProp' property from system properties set.
    val sampleSysProp: String = property("sampleSysProp")

Requires and optional statements
--------------------------------
Property files marked as required will cause failure if file does not exist.
Optional files will be skipped if file does not exist.

Either mandatory (required) or optional files that **does** exist but cannot be read or parsed for some reason,
will cause failure (by design).

Properties file format
----------------------
This plugin supports standard properties file conventions:

    # Comment line
    key1=value
    key2=value

Special usage notes
-------------------
Properties are applied only after the `propertySources { ... }` block is evaluated. Trying to address properties prior
to the sources blocks will fail.

Take a look at the following example (assuming `default.properties` contains a key named _sampleProp_):

    // Kotlin DSL
    plugins {
        id("com.nocwriter.gradle.props")
    }

    // Should fail, because 'default.properties' has not been loaded yet.
    val sampleProp: String by project.extra

    propertySources {
        required("default.properties")
    }

    // That's fine, because 'default.properties' already loaded into project's extra properties set.
    val sampleProp: String by project.extra

Compatability
-------------

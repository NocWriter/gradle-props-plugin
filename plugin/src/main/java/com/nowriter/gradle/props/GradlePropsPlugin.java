/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.nowriter.gradle.props;

import org.gradle.api.Project;
import org.gradle.api.Plugin;

/**
 * Gradle properties plugin.
 *
 * @author Guy Raz Nir
 * @since 2022/04/14
 */
public class GradlePropsPlugin implements Plugin<Project> {

    /**
     * Identifier of this plugin.
     */
    public static String PLUGIN_ID = "com.nowriter.gradle.props";

    @SuppressWarnings("NullableProblems")
    public void apply(Project project) {
        // Configure a new properties loading with a given Gradle project.
        PropertiesLoader loader = PropertiesLoader.create(project);

        // Create a new plugin extension and inject it properties loader.
        project.getExtensions().create("propertySources", PropertiesExt.class, loader);
    }

}

package com.walker.plugins.cmake;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;

import javax.inject.Inject;
import java.util.ArrayList;


// Recreated from org.gradle.samples.plugins.cmake
public class CMakeExtension {
    private final Property<String> binary;
    private final DirectoryProperty includeDirectory;
    private final DirectoryProperty projectDirectory;

    private final Property<String[]> cmakeArguments;

    @Inject
    public CMakeExtension(ProjectLayout projectLayout, ObjectFactory objectFactory) {
        binary = objectFactory.property(String.class);
        cmakeArguments = objectFactory.property(String[].class);
        includeDirectory = objectFactory.directoryProperty();
        projectDirectory = objectFactory.directoryProperty();
        projectDirectory.set(projectLayout.getProjectDirectory());
        includeDirectory.set(projectDirectory.dir("include"));
    }

    public final Property<String> getBinary() {
        return binary;
    }

    public final DirectoryProperty getIncludeDirectory() {
        return includeDirectory;
    }

    public final DirectoryProperty getProjectDirectory() {
        return projectDirectory;
    }

    public final Property<String[]> getCmakeArguments() {
        return cmakeArguments;
    }
}

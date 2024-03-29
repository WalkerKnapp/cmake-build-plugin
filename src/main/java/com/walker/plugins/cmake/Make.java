package com.walker.plugins.cmake;

import org.gradle.api.DefaultTask;
import org.gradle.api.Task;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskProvider;

public class Make extends DefaultTask {
    //private final DirectoryProperty variantDirectory = getProject().getObjects().directoryProperty();
    private final DirectoryProperty projectDirectory = getProject().getObjects().directoryProperty();
    private final ConfigurableFileCollection makeFiles = getProject().files();
    //private final DirectoryProperty outputDirectory = getProject().getObjects().directoryProperty();
    private final RegularFileProperty binary = getProject().getObjects().fileProperty();
    private final ListProperty<String> arguments = getProject().getObjects().listProperty(String.class).empty();

    @TaskAction
    public void executeMake() {
        final String makeExecutable = System.getenv().getOrDefault("MAKE_EXECUTABLE", "make");
        getProject().exec(execSpec -> {
            execSpec.setWorkingDir(getProjectDirectory());

            execSpec.setExecutable(makeExecutable);
            execSpec.args(getArguments().get());
        });
    }

    public void generatedBy(final TaskProvider<? extends Task> task) {
        /*variantDirectory.set(task.flatMap(it -> {
            if (it instanceof CMake) {
                return ((CMake) it).getVariantDirectory();
            } else if (it instanceof ConfigureTask) {
                return ((ConfigureTask) it).getMakeDirectory();
            } else {
                throw new IllegalArgumentException("Make task cannot extract build information from \'" + it.getClass().getName() + "\' task");
            }
        }));*/
        /*outputDirectory.set(task.flatMap(it -> {
            if (it instanceof CMake) {
                return ((CMake) it).getVariantDirectory();
            } else if (it instanceof ConfigureTask) {
                return ((ConfigureTask) it).getPrefixDirectory();
            } else {
                throw new IllegalArgumentException("Make task cannot extract build information from \'" + it.getClass().getName() + "\' task");
            }
        }));*/
        dependsOn(task);
        makeFiles.setFrom(task.map(it -> {
            if (it instanceof CMake) {
                return ((CMake) it).getCmakeFiles();
            } else if (it instanceof ConfigureTask) {
                return ((ConfigureTask) it).getOutputs().getFiles();
            } else {
                throw new IllegalArgumentException("Make task cannot extract build information from \'" + it.getClass().getName() + "\' task");
            }
        }));
    }

    /*@Internal
    public final DirectoryProperty getVariantDirectory() {
        return variantDirectory;
    }*/

    @Internal
    public final DirectoryProperty getProjectDirectory() {
        return projectDirectory;
    }

    @InputFiles
    public final ConfigurableFileCollection getMakeFiles() {
        return makeFiles;
    }

    /*@OutputDirectory
    public final DirectoryProperty getOutputDirectory() {
        return outputDirectory;
    }*/

    @OutputFile
    public final RegularFileProperty getBinary() {
        return binary;
    }

	@Internal
    public final ListProperty<String> getArguments() {
        return arguments;
    }
}

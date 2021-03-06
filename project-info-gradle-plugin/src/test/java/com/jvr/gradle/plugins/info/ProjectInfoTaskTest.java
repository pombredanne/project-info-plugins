package com.jvr.gradle.plugins.info;

import org.gradle.api.Task;
import org.junit.Test;
import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.api.Project;

import static com.jvr.build.info.api.Constants.TASK_NAME;
import static org.junit.Assert.*;

public class ProjectInfoTaskTest {

    @Test
    public void canAddTaskToProject() {
        Project project = ProjectBuilder.builder().build();
        Task task = project.getTasks().create(TASK_NAME, ProjectInfoTask.class);
        assertTrue(task != null);
    }

    @Test
    public void canExecuteOnEmptyProject() {

        Project project = ProjectBuilder.builder().build();
        ProjectInfoTask task = project.getTasks().create(TASK_NAME, ProjectInfoTask.class);
        assertTrue(task != null);

        task.infoTask();

    }

}

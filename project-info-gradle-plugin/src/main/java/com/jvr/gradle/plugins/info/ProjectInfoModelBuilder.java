package com.jvr.gradle.plugins.info;

import com.jvr.build.info.api.Module;
import com.jvr.build.info.api.ProjectRoot;
import com.jvr.gradle.model.ProjectInfoModel;
import com.jvr.gradle.model.impl.ProjectInfoModelImpl;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.result.ResolutionResult;
import org.gradle.api.tasks.diagnostics.internal.graph.nodes.RenderableDependency;
import org.gradle.api.tasks.diagnostics.internal.graph.nodes.RenderableModuleResult;
import org.gradle.tooling.provider.model.ToolingModelBuilder;

import java.util.Map;
import java.util.Set;

public class ProjectInfoModelBuilder implements ToolingModelBuilder {

    @Override
    public boolean canBuild(String modelName) {
        return modelName.equals(ProjectInfoModel.class.getName());
    }

    @Override
    public Object buildAll(String modelName, Project project) {
        try {
            ProjectRoot projectRoot = getProjectRoot(project);
            return new ProjectInfoModelImpl(projectRoot);
        } catch (Exception e) {
            String msg = String.format("Failed to convert %s project to ProjectRoot", project.getName());
            throw new GradleException(msg, e);
        }
    }

    ProjectRoot getProjectRoot(Project gradleProject) {

        GradleNodeVisitor visitor = new GradleNodeVisitor();
        Set<Configuration> projectConfigurations = gradleProject.getConfigurations();

        for( Configuration configuration : projectConfigurations) {
            if (configuration.isCanBeResolved()) {
                ResolutionResult result = configuration.getIncoming().getResolutionResult();
                RenderableDependency root = new RenderableModuleResult(result.getRoot());
                visitor.visit(root, configuration.getName());
            }
        }

        ProjectRoot rootProject = visitor.getRoot();
        if (rootProject == null) {
            rootProject = new ProjectRoot();
        }

        rootProject.setName(gradleProject.getName());
        rootProject.setDescription(gradleProject.getDescription());
        if (gradleProject.getParent() != null) {
            // TODO: do gradle projects have parents?
//                com.jvr.build.info.api.Project parent = GradleNodeVisitor.toProject(project.getParent().getArtifact());
//                rootProject.setParent(parent);
        }

        Map<String, Project> children = gradleProject.getChildProjects();
        for (String moduleFolder : children.keySet()) {
            Project moduleProject = children.get(moduleFolder);
            ProjectRoot module = getProjectRoot(moduleProject);
            rootProject.addModule(new Module(moduleFolder, module));
        }

        return rootProject;
    }

}

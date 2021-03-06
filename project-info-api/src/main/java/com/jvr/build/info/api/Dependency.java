package com.jvr.build.info.api;

import java.io.Serializable;

/**
 * Expresses dependency on another project
 */
public class Dependency implements Serializable {

    private String scope = null;
    private Project dependency = null;

    public Dependency() {
    }

    public Dependency(String scope, Project dependency) {
        this.scope = scope;
        this.dependency = dependency;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Project getDependency() {
        return dependency;
    }

    public void setDependency(Project dependency) {
        this.dependency = dependency;
    }

}

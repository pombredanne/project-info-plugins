# project-info-plugins 
[![Build Status](https://travis-ci.org/JV-ration/project-info-plugins.svg?branch=master)](https://travis-ci.org/JV-ration/project-info-plugins) 
[![Code Climate](https://codeclimate.com/github/JV-ration/project-info-plugins/badges/gpa.svg)](https://codeclimate.com/github/JV-ration/project-info-plugins)

Plugins for build tools like Maven and Gradle to generate unified information about the projects

## Using from command line

Currently only Maven plugin is implemented. After building the plugins locally and installing them in 
local maven repository, execute command below on any maven project you have
 
```
mvn com.jv-ration.maven.plugins:project-info-maven-plugin:0.0.6-SNAPSHOT:info -DoutputFile=info.json
```

The generated JSON will contains project information and can be loaded using classes 
from `project-info-api` module

## Using from code

Invoking Maven to generate JSON file with Project description

```
Properties buildProperties = new Properties();
buildProperties.setProperty("outputFile", "info.json");

List<String> goals = Collections.singletonList("com.jv-ration.maven.plugins:project-info-maven-plugin:0.0.5:info");

InvocationRequest request = new DefaultInvocationRequest();
request.setGoals(goals).setProperties(buildProperties).setInteractive(false);

Invoker invoker = new DefaultInvoker();
invoker.execute(request);
```

Invoking Gradle to generate JSON file with Project description

```
// not available yet
```

Using project information in your code. This code is the same for Maven and Gradle projects
```
String json = FileUtils.readFileToString(new File("info.json"));
ProjectRoot project = ProjectJson.fromJson(json);
```

## Releases

Currently the project releases are deployed to [Bintray](https://bintray.com/jv-ration/maven/project-info-plugins). 
Open this page an lookup the latest release

By adding the following to your `pom.xml` you can use Maven plugin without building it locally

```
<pluginRepositories>
    <pluginRepository>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <id>bintray-jv-ration-maven</id>
        <name>bintray-plugins</name>
        <url>http://dl.bintray.com/jv-ration/maven</url>
    </pluginRepository>
</pluginRepositories>
```

Execute this from the command line

```
mvn com.jv-ration.maven.plugins:project-info-maven-plugin:0.0.5:info -q
```
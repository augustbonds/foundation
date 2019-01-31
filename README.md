# foundation
[![build](https://img.shields.io/circleci/project/github/augustbonds/foundation/master.svg?style=flat&maxAge=2592000)](https://circleci.com/gh/augustbonds/foundation/tree/master)
[![Maven Central](https://img.shields.io/maven-central/v/com.augustbonds/foundation.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.augustbonds%22%20AND%20a:%22foundation%22)

A Swift-inspired Java standard library.

## Usage

### Maven
```xml
<dependency>
  <groupId>com.augustbonds</groupId>
  <artifactId>foundation</artifactId>
  <version>0.2.1</version>
</dependency>
```
### Gradle
```
implementation 'com.augustbonds:foundation:0.2.1'
```

### Bazel
```
maven_jar(
    name = "foundation",
    artifact = "com.augustbonds:foundation:0.2.1",
    sha1 = "016fe32aeb8c080a57f6677256c2b8c096c84ea0",
)
```

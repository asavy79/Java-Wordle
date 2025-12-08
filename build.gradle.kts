plugins {
    id("java")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("application")
}

group = "org.wordle_project"
version = "1.0-SNAPSHOT"

javafx {
    version = "21"
    modules("javafx.controls", "javafx.fxml")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("org.wordle_project.Main")
}

tasks.test {
    useJUnitPlatform()
}
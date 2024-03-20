description = "Alter Servers Plugins"

dependencies {
    implementation(projects.gameServer)
    implementation(projects.util)
    implementation(projects.net)
    implementation(kotlin("script-runtime"))
    implementation(project(":game-api"))

    implementation("org.jetbrains.exposed:exposed-core:0.48.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.48.0")
    implementation("mysql:mysql-connector-java:8.0.33")
}

tasks.named<Jar>("jar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

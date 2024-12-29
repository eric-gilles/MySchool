// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("org.sonarqube") version "6.0.1.5171"
}

sonar {
  properties {
      property("sonar.projectKey", "eric-gilles_MySchool")
      property("sonar.organization", "eric-gilles-1")
      property("sonar.host.url", "https://sonarcloud.io")
  }
}
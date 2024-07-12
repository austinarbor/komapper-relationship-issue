import dev.aga.gradle.versioncatalogs.Generator.generate
import dev.aga.gradle.versioncatalogs.GeneratorConfig

rootProject.name = "komapper-relationship-issue"

plugins { id("dev.aga.gradle.version-catalog-generator") version "1.5.0" }

dependencyResolutionManagement {
  repositories {
    mavenCentral()
  }
  versionCatalogs {
    generate("springLibs") { from(toml("spring-springBootDependencies")) }
    generate("komapperLibs") {
      from(toml("komapper-platform"))
      aliasPrefixGenerator = GeneratorConfig.NO_PREFIX
    }
  }
}

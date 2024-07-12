# Instructions
1. Create local database `komapper_test`
   1. The project is currently configured to use postgres, so if you use a different database you will need to update the dependencies in `build.gradle.kts`
2. Update `application.yml` in `src/main/resources` to update the database url (if required)
3. Run `src/test/kotlin/dev/aga/RelationshipTest`

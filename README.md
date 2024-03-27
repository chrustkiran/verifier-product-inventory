# verifier-product-inventory
Small application to store the inventory and it supports immutability, thready safety in multi threaded environment. I followed TDD approach to develop this so it has 100% code coverage.

To Run this program

install Java 17 since I have used gradle 7.5.1

There are two ways to run this project 
1. in your IDE.
    * Open this project in the ide, and make sure you are using gradle from gradle-wrapper.properties file to avoid unnecessary errors
    * or you can install gradle 7.5.1 in your environment and point the directory to your ide gradle.
    * Then you can load it as a gradle project.
    * Open the test folder, inside that you would be able to find some test classes, so you can use the IDE 'Run Test' feature to execute.
2. Terminal
   * Since this is gradle project it has gradlew it will fetch the required version
   * please don't forget to give necessary permission to gradlew file `chmod +x gradlew`
   * So please run `./gradlew clean test --info`
   * you can see the report in `build/reports/tests/test` folder, you can use your browser to view the report.

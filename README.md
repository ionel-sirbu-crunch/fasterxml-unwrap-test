# fasterxml-unwrap-test
Testcase for showcasing unwrapping usage with fasterXML.

#### Using v. 2.11.4

Run with
```shell
mvn clean test
```
& expect tests to pass.


#### Using v. 2.11.4

Run with
```shell
mvn clean test -Djackson-bom.version=2.12.3
```
& expect the `deserializeWithUnwrap` test to fail.
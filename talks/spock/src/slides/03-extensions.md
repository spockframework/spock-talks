# Extensions

## Extensions

Spock has a very functional extension API.

Types…

* **Listeners** - observe test execution.
* **Interceptors** - augment test execution.

Activation…

* **Annotation-driven** - explicitly activated
* **Global** - implicitly activated

## Built-in Extensions

* `@Ignore` - don't run this test
* `@IgnoreRest` - run only this test
* `@FailsWith` - test should fail
* `@Timeout` - kill the test if it takes too long
* `@AutoCleanup` - close test resources
* `@Stepwise` - aggregate test
* `@Rule` - [JUnit Rule](http://www.junit.org/node/580) support

# Demo

extension/*
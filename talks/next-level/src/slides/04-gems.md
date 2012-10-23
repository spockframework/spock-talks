# Hidden Gems

Stuff you might not know about!

## Data Driven

Spock makes writing *parameterised* tests trivial.

    def "some math"() {
        expect:
        a + b == c
        
        where:
        a | b | c
        1 | 1 | 2
        5 | 5 | 10
    }

## Inputs/Outputs

You can visually separate the input and output with a double pipe (`||`), if you like.

    def "some math"() {
        expect:
        a + b == c

        where:
        a | b || c
        1 | 1 || 2
        5 | 5 || 10
    }

## Parameter Typing

You can add type information to the method (for IDE support).

    def "some math"(Integer a, Integer b, Integer c) {
        expect:
        a + b == c

        where:
        a | b || c
        1 | 1 || 2
        5 | 5 || 10
    }

# Demo

datadriven/*

## Interaction Based Testing

Spock has built in support for mocking.

    def sub = Mock(Subscriber)
    Subscriber sub = Mock()

And a DSL for specifying interactions.

    1 * sub.receive("msg")
    (1..3) * sub.receive(_)
    (1.._) * sub.receive(_ as String)
    1 * sub.receive(!null)
    1 * sub.receive({it.contains("m")})
    1 * _./rec.*/("msg")

## Stubbing

Implementations can be stubbed out easily.

    // now returns status code
    String receive(String msg) { ... } 

<!-- -->

    sub.receive(_) >> "ok"
    sub.receive(_) >>> ["ok", "ok", "fail"]
    sub.receive(_) >>> { msg -> msg.size() > 3 ? "ok" : "fail" }
    
    // mixed mocking/stubbing
    3 * sub.receive(_) >>> ["ok", "ok", "fail"]

## Stub Result Chaining

Method results can be chained.

    Producer producer = Mock()

    producer.next()
        >> 1
        >> 2
        >> { throw new RuntimeException("no more!") }

## Stubbed Method Parameters

Rich information is available about the parameters of stubbed methods.

    interface Person {
        void greet(Person other, String greeting)
    }

    Person person = Mock()
    person.greet(*_) >> { args -> println args[1] }
    person.greet(*_) >> { Person p, String g -> println g }
    person.greet(*_) >> { IMockInvocation invocation ->
        println invocation.mockObject
        println invocation.method
        println invocation.arguments
    }

## Configuration

Spock has a configuration API. 

Looks for (in order)…
    
* `-Dspock.configuration=path/to/file`
* `SpockConfig.groovy` file on class path 
* `~/.spock/SpockConfig.groovy`

<!-- -->

    runner {
        filterStackTrace true
        optimizeRunOrder false
        include Database
        exclude UI
    }

## Include/Exclude

Configure which tests to run.

    runner {
        include Fast
        exclude Slow
    }

<!-- -->

    @Fast class MyFastSpec extends Specification {
        def "I’m fast as hell!"() { … }

        @Slow "sorry, can’t keep up"() { … }
    }

# Demo

extension/builtin/IncludeExcludeSpec

## Optimized Run Order

Spock can optimize the order of tests, for fast feedback.

This is off by default.

    runner {
        optimizeRunOrder true
    }

Fast/passing before slow/failing tests.

# Demo

extension/builtin/OptimizeRunOrder

## Under the hood

Spock works by rewriting your code at compile time. You write…

    expect: sum(a, b) == c

Spock rewrites as (kinda):

    def rec = new ValueRecorder()
    verifier.expect(
        rec.record(
            rec.record(
                sum(rec.record(a), rec.record(b)) == rec.record(c)
            )
        )
    )

## @AutoCleanup

Automatically clean up/close resources.

    @AutoCleanup Reader names = new FileReader("names.txt")
    
    def "can read the first name"() {
        expect:
        names.readLine() == "Spock!"
    }

# Demo

extension/builtin/UseAutoCleanup

## Condition Diffing

Spock builds on IDEA and Eclipse's JUnit integration with [`ComparisonFailure`](http://junit.sourceforge.net/javadoc/org/junit/ComparisonFailure.html)

Allows the exact difference between two compared objects to be visualised.

Best shown by example…

# Demo 

condition/ConditionDiffing

## Async testing

Spock has builtin support for testing concurrent code.

    import spock.util.concurrent.BlockingVariable
    
    def "can test async stuff"() {
        given:
        def value = new BlockingVariable(10, TimeUnit.SECONDS)
        
        when:
        Thread.start {
            value.set("foo")
        }
        
        then:
        value.get() == "foo"
    }

# Demo 

async/TheOracleSpec

## Dynamic ignores

Spock allows you to dynamically ignore tests…

    @IgnoreIf({ Calendar.instance.get(DAY_OF_WEEK) == MONDAY })
    def "make money"() {
        when:
        work()
        
        then:
        money > old(money)
    }

# Demo

extension/builtin/UseIgnore

## JUnit Rules

Spock has built in support for [JUnit Rules](http://www.junit.org/node/580).

Rules “mixin” to the test execution and can provide functionality via API.

# Demo

extension/builtin/UseRule

## Stepwise Tests

`@Stepwise` makes the test object a composite test, executing each test method *in sequence*.

Great for larger integration or functional tests.

# Demo

extension/builtin/UseStepwise
# Spock

Next Level

## About Spock

Spock is a testing and specification framework for Java and Groovy applications.

* Fully JUnit compatible (IDEs, CI etc.)
* Developer focussed
* Packed with innovation
* Excellent IDE support

Runs just like JUnit.

## About Me

* Principal Engineer @ [Gradleware](http://gradleware.com/)
* [luke.daley@gradleware.com](mailto:luke.daley@gradleware.com)
* [@ldaley](http://twitter.com/ldaley)

Committer to the Spock project, and colleague of Spock lead [Peter Niederwieser](http://twitter.com/pniederw).

## Agenda

* Brief introduction to Spock
* Integrations
* Extensions
* Hidden gems
* Next version features

## Java JUnit Example

    import org.junit.Test;
    import java.math.BigDecimal;
    import static org.junit.Assert.assertEquals;

    public class AccountTest {
        @Test
        public void withdrawSomeAmount() {
            Account account = new Account(BigDecimal.valueOf(5));
            account.withdraw(BigDecimal.valueOf(2));
            assertEquals(BigDecimal.valueOf(3), account.getBalance());
        }
    }

## Spock Example

    import spock.lang.Specification

    class AccountSpec2 extends Specification {
        def "withdraw some amount"() {
            given:
            def account = new Account(5.0)

            when:
            account.withdraw(2.0)

            then:
            account.balance == 3.0
        }
    }

# Demo

fundamentals/BasicsSpec

## Blocks

Spock introduces blocks into the test language.

    // fixtures
    setup: cleanup: 
    
    // actions
    given: when: 
    
    // verification
    expect: then: 
    
    // parameterisation
    where: 
    
    // used to break up big blocks
    and:

## Fixture Methods

Lifecycle hooks.

    setupSpec() // before any test
    setup() // before each test
    cleanup() // after each test
    cleanupSpec() // after all tests

## State

Instance variables are *per test method*. 

Actually, each test uses a different instance of the test class.

Use the `@Shared` annotation on a field to share the value *among* test methods.

    String foo // reset each test
    @Shared String bar // test global

`@Shared` != `static`.

## Exceptions

Testing code that should throw exceptions.

    when:
    someBadMethod()
    
    then:
    def exception = thrown(IllegalStateException)
    exception.message == "bad stuff happened!"

## Old

The `old()` method allows you to retrieve a value as it was *before the preceeding block*.

    given:
    def a = 1
    
    when:
    a = 2
    
    then:
    old(a) == 1
    a == 2

# Demo

fundamentals/ExceptionsAndOldSpec

## Data Driven

Spock makes writing *parameterised* tests trivial.

    def "some math"() {
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

# Demo

interaction/*

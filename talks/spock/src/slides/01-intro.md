# Spock

Next Level

## About Me

* Principal Engineer @ [Gradleware](http://gradleware.com/)
* [luke.daley@gradleware.com](mailto:luke.daley@gradleware.com)
* [@ldaley](http://twitter.com/ldaley)

Committer to the Spock project, and colleague of Spock lead [Peter Niederwieser](http://twitter.com/pniederw).

## Agenda

* Brief introduction to Spock
* Integrations
* Hidden Gems 
* Next version features

## About Spock

Spock is a testing and specification framework for Java and Groovy applications.

* Fully JUnit compatible (IDEs, CI etc.)
* Developer focussed
* Packed with innovation
* Excellent IDE support

Runs just like JUnit.

Version 0.7 released last week!.

## Documentation!

We are working on top quality documentation for Spock!

[http://spock-framework.readthedocs.org/](http://spock-framework.readthedocs.org/).


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

    class AccountSpec extends Specification {
        def "withdraw some amount"() {
            given:
            def account = new Account(5.0)

            when:
            account.withdraw(2.0)

            then:
            account.balance == 3.0
        }
    }

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
    cleanupSpec() // after last test

## Verification Styles

Verification conditions (or assertions) in Spock can be any expression.

    expect:
    [1,2,3].contains(2)
    [1,2,3].any { it > 2 }

Including Hamcrest Matchers.

    import static spock.util.matcher.HamcrestMatchers.closeTo
    import static spock.util.matcher.HamcrestSupport.that
    
    expect:
    that 2.04, closeTo(2, 0.05) 

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

fundamentals/*

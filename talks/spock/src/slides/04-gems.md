# Hidden Gems

## Configuration

`~/.spock/SpockConfig.groovy`, or on class path, or with `-Dspock.configuration`

    runner {
        filterStackTrace false
        include Fast
        exclude Slow
        optimizeRunOrder true
    }

<!-- -->

    @Fast class MyFastSpec extends Specification {
        def "I’m fast as hell!"() { … }

        @Slow "sorry, can’t keep up"() { … }
    }

## IDE Diff Dialog

IntelliJ IDEA and Eclipse provide diff dialogs that highlight *exactly* how two values differ.

Demo coming up…

# Demo

coolstuff/*

## Advanced Mocking

Method results can be chained.

    Producer producer = Mock()

    producer.next()
        >> 1
        >> 2
        >> { throw new RuntimeException("no more!") }

## Advanced Mocking (cont.)

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
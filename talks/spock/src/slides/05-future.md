# Spock 0.7

Released!

## with()

A new syntax for multiple assertions on the one object. Old…

    expect:
    person.name == "fred"
    person.age == 42
    person.sex == "male"

New…

    expect:
    with(person) {
      name == "fred"
      age == 42
      sex == "male"
    }

## Multiple Interactions

Old…

    Person person = Mock()
    person.speak() >> "Hi there!"
    person.sleep() >> "Zzzzzz..."
    1 * person.clapOn("shoulder")

New…

    Person person = Mock {
        speak() >> "Hi there!"
        sleep() >> "Zzzzzz..."
        1 * clapOn("shoulder")
    }

# Future features

## Easier reuse

It will be easier to reuse code and compose tests from components.

Old…

* Base spec
* JUnit Rule
* Spock Extension

The new mechanism is mixin based.

## Functionality Mixins

Mixins can provide API, and participate in the lifecycle.

    class MessagingSupport extends SpecificationMixin {
        @Inject Broker broker
        String brokerUrl = "vm://localhost"

        def setup() { broker.start(brokerUrl) }
        def cleanup() { broker.shutDown() }

        void sendMessage(String msg) { broker.send(msg) }
        String receiveMessage() { broker.receive() }
    }

## Functionality Mixins (cont.) 

Mixins are declared as instance variables, and Spock manages them.

    class MyMessagingSpec extends Specification {
      MessagingSupport messaging = new MessagingSupport(
          brokerUrl: "tcp://localhost:61616"
      )
      DatabaseSupport database

      def "store message in database"() {
        def msg = messaging.receiveMessage()
        database.store(msg)
      }
    }

## Contract Mixins

Mixins can also add test methods, which is convenient for contract tests.

    class EqualsAndHashCodeContract extends SpecificationMixin {
      Specification owner
 
      def setup() {
        assert !owner.object1.is(owner.object2)
        assert !owner.object1.equals(owner.object2)
      }
      
      def "is equal to itself"() {
        expect:
        owner.object1.equals(owner.object1)
        owner.object2.equals(owner.object2)
      }
    }

## Contract Mixin (cont.)

Any test methods in the mixin get used.

    class PersonSpec extends Specification {
      EqualsAndHashCodeContract contract

      def object1 = new Person(name: "fred", age: 42)
      def object2 = new Person(name: "barney", age: 21)
    }

## Configuration

Spock will have a vastly improved configuration mechanism.

Configure via: Groovy, JSON, XML, system properties, properties files…

Will also allow the configuration to be embedded in the `build.gradle` or `pom.xml`.

## Questions?

Links…

* Homepage - [http://spockframework.org](http://spockframework.org)
* Documentation (WIP) - [http://spock-framework.readthedocs.org](spock-framework.readthedocs.org)
* Google Group - [https://groups.google.com/…](https://groups.google.com/forum/?fromgroups#!forum/spockframework)
* Source Code - [http://github.spockframework.org](http://github.spockframework.org)
* Spock Web Console - [http://webconsole.spockframework.org](http://webconsole.spockframework.org)
* Spock Example Project - [http://examples.spockframework.org](http://examples.spockframework.org)

# Thank You

[www.spockframework.org](http://www.spockframework.org)

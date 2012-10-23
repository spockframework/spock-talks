package interaction

import spock.lang.Specification
import spock.lang.Ignore

class MockingImprovements extends Specification {
    def "declare interactions upfront"() {
        def person = Mock(Person) {
            sing() >> "I can't get no, . . ., sa-tis-fac-tion"
        }

        expect:
        person.sing() == "I can't get no, . . ., sa-tis-fac-tion"
    }

    @Ignore
    def "more informative error message when too many invocations occur"() {
        def person = Mock(Person)

        when:
        person.setName("Fred")
        person.setName("Wilma")
        person.setName("Barney")

        then:
        2 * person.setName(_)
    }

    @Ignore
    def "more informative error message when too few invocations occur"() {
        def person = Mock(Person)

        when:
        person.setName("Barney")
        person.setAge(42)

        then:
        2 * person.setName("Fred")
    }
}

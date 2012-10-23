package interaction

// we are going to stub, mock, and spy on this poor soul
class Person {
    String name
    int age

    Person() {
        this("Mr. Unknown", 0)
    }

    Person(String name, int age) {
        this.name = name
        this.age = age
    }

    String sing() { "tra-la-la" }

    Person getBestFriend() { this }

    static Person findSpock() { new Person("Spock", 222) }
}

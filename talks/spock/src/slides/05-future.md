# Spock 0.7

In the works…

## with()

A new syntax for multiple assertions on the one object.

Old…

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

## More 0.7 stuff…
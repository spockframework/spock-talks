package async

import java.util.concurrent.TimeUnit

import spock.lang.Specification
import spock.util.concurrent.BlockingVariable
import spock.util.concurrent.BlockingVariables

class TheOracleSpec extends Specification {
  def "talk to the oracle, flawed approach"() {
    def oracle = new TheOracle()

    expect:
    oracle.ask("What's the coolest language in town?") { answer ->
      // fails, but too late and in wrong thread
      // in IDEA, this ends up being displayed as output of subsequent test
      assert answer == "Cobol"
    }
  }

  def "talk to the oracle, correct approach"() {
    def oracle = new TheOracle()
    def answer2 = new BlockingVariable<String>(10, TimeUnit.SECONDS)
    def answer1 = new BlockingVariable<String>(10, TimeUnit.SECONDS)

    when:
    oracle.ask("What's the coolest language in town?") { answer ->
      answer1.set(answer)
    }
    oracle.ask("No, really. What's the coolest language?") { answer ->
      answer2.set(answer)
    }

    then:
    // waits for answer until it arrives or timeout occurs
    answer1.get() == "Java"
    answer2.get() == "Groovy. But don't tell anyone that you heard it from me."
  }

  def "talk to the oracle, slick approach"() {
    def oracle = new TheOracle()
    // dynamic alternative to BlockingVariable (or multiple thereof)
    def answers = new BlockingVariables(10, TimeUnit.SECONDS)

    when:
    oracle.ask("What's the coolest language in town?") { answer ->
      answers.first = answer
    }
    oracle.ask("No, really. What's the coolest language?") { answer ->
      answers.second = answer
    }

    then:
    // order of access does not matter
    answers.second == "Groovy. But don't tell anyone that you heard it from me."
    answers.first == "Java"
  }
}

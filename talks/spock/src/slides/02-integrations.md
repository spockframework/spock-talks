# Integrations

## Spring Framework

Works just like Spring + Java JUnit.

    @ContextConfiguration("/base-context.xml")
    class InjectionExamples extends Specification {
        @Autowired
        IService1 byType

        @Resource
        IService1 byName

        @Autowired
        ApplicationContext context
    }

## Grails Framework

New for Grails 2.0, built-in support.

    package org.example
    import grails.test.mixin.*
    
    @TestFor(PostController)
    class PostControllerSpec extends spock.lang.Specification {
        def "Index action should redirect to list page"() {
            when:
            controller.index()
        
            then:
            response.redirectedUrl == "/post/list"
        }
    }

Deprecates old `UnitSpec` class.

## More Integrations…

* Google Guice
* Apache Tapestry 
* Unitils
* Griffon
* Arquillian
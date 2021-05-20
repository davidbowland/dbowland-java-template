package com.dbowland.javatemplate

import com.dbowland.javatemplate.controller.ExampleController
import com.dbowland.javatemplate.controller.StatusController
import com.dbowland.javatemplate.service.ExampleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JavaTemplateApplicationTests extends Specification {

	@Autowired(required = false)
	private ExampleController exampleController

	@Autowired(required = false)
	private ExampleService exampleService

	@Autowired(required = false)
	private StatusController statusController

	def "when context is loaded expect all beans created"() {
		expect:
		exampleController
		exampleService
		statusController
	}

	def ""() {

	}

}

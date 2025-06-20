package boki.jenkinsdemo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/")
    fun index() = "Hello World"

    @GetMapping("/hello/{name}")
    fun hello(@PathVariable name: String) = "Hello $name"

}
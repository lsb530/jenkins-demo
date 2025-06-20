package boki.jenkinsdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JenkinsDemoApplication

fun main(args: Array<String>) {
    runApplication<JenkinsDemoApplication>(*args)
}

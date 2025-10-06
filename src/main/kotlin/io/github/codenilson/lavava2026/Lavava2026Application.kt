package io.github.codenilson.lavava2026

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class Lavava2026Application

fun main(args: Array<String>) {
	runApplication<Lavava2026Application>(*args)
}

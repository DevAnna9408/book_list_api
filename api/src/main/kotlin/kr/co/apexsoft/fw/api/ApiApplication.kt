package kr.co.apexsoft.fw.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication(
    scanBasePackages = ["kr.co.apexsoft.fw.domain", "kr.co.apexsoft.fw.lib", "kr.co.apexsoft.fw.api"]
)
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}

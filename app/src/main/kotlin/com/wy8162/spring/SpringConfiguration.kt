package com.wy8162.spring

import com.wy8162.config.httpClientInstance
import io.ktor.client.HttpClient
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.ApplicationStopped
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.hooks.MonitoringEvent
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@Configuration
@PropertySource(
    "classpath:application.properties",
    "classpath:application-\${KTOR_ENV:local}.properties"
)
@ComponentScan(basePackages = ["com.wy8162"])
open class SpringConfiguration {
    @Bean
    open fun propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer =
        PropertySourcesPlaceholderConfigurer()

    @Bean
    open fun prometheusMeterRegistry(): PrometheusMeterRegistry = PrometheusMeterRegistry(
        PrometheusConfig.DEFAULT
    )

    @Bean
    open fun httpClient(): HttpClient = httpClientInstance()
}

val _springApplicationContext = AnnotationConfigApplicationContext(SpringConfiguration::class.java)
fun <T> ApplicationCall.inject(type: Class<T>) = _springApplicationContext.getBean(type)

val SpringIntegration = createApplicationPlugin(name = "SpringIntegrationPlugin") {
    on(MonitoringEvent(ApplicationStopped)) {
        _springApplicationContext.close()
    }
}

inline fun <reified T : Any> inject() =
    lazy { _springApplicationContext.getBean(T::class.java) }

inline fun <reified T : Any> inject(type: Class<T>) =
    lazy { _springApplicationContext.getBean(type) }

inline fun <reified T : Any> inject(type: Class<T>, vararg args: Any) =
    lazy { _springApplicationContext.getBean(type, args) }

inline fun <reified T : Any> inject(name: String) =
    lazy { _springApplicationContext.getBean(name) }

inline fun <reified T : Any> inject(name: String, type: Class<T>) =
    lazy { _springApplicationContext.getBean(name, type) }

inline fun <reified T : Any> inject(name: String, vararg args: Any) =
    lazy { _springApplicationContext.getBean(name, args) }

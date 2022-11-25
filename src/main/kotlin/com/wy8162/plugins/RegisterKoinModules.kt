package com.wy8162.plugins

import com.wy8162.config.httpClientInstance
import com.wy8162.controller.HrController
import com.wy8162.controller.UserController
import com.wy8162.service.DatabaseService
import com.wy8162.service.DatabaseServiceImpl
import com.wy8162.service.HelloService
import com.wy8162.service.HelloServiceImpl
import com.wy8162.service.HrService
import com.wy8162.service.HrServiceImpl
import com.wy8162.service.SmsService
import com.wy8162.service.SmsServiceImpl
import com.wy8162.service.UserService
import com.wy8162.service.UserServiceImpl
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger

fun Application.registerKoinModules() {
    install(Koin) {
        SLF4JLogger()
        modules(koinModule)
    }
}

val koinModule = module {
    singleOf(::UserController)
    singleOf(::UserServiceImpl) bind UserService::class
    singleOf(::HrController)
    singleOf(::HrServiceImpl) bind HrService::class
    singleOf(::SmsServiceImpl) bind SmsService::class
    singleOf(::DatabaseServiceImpl) bind DatabaseService::class
    singleOf(::HelloServiceImpl) bind HelloService::class
    single { PrometheusMeterRegistry(PrometheusConfig.DEFAULT) }
    single { httpClientInstance() }
}

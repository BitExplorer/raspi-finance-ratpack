import com.fasterxml.jackson.databind.ObjectMapper
import com.zaxxer.hikari.HikariConfig
import finance.domain.Account
import finance.domain.Category
import finance.domain.Description
import finance.services.AccountService
import finance.services.CategoryService
import finance.services.DescriptionService
import finance.services.PaymentService
import javax.net.ssl.SSLContext
import io.netty.internal.tcnative.SSLContext
import ratpack.ssl.SSLContexts

//import postgres.PostgresConfig
//import postgres.PostgresModule
import ratpack.config.ConfigData
import ratpack.config.ConfigDataBuilder
import ratpack.handling.Context
import ratpack.hikari.HikariModule
import ratpack.server.ServerConfigBuilder

import static groovy.json.JsonOutput.toJson
import static ratpack.groovy.Groovy.ratpack

ratpack {
    serverConfig { ServerConfigBuilder config ->
        port(5050)
        json('db_config.json')
        ssl SSLContexts.sslContext(new File('ssl/hornsup-raspi-finance.jks'), 'monday1')

        //https://github.com/merscwog/ratpack-ssl-test/tree/03b8d325708ae1a3fd20e3c35a5ead178b883703
    }

    bindings {
        Properties hikariConfigProperties = serverConfig.get("/database", Properties)
        moduleConfig(HikariModule, new HikariConfig(hikariConfigProperties))

        bind(CategoryService)
        bind(DescriptionService)
        bind(AccountService)
        bind(PaymentService)
    }

    handlers {
//        get("config") { PostgresConfig config ->
//            render toJson(config)
//        }

        get ('accounts') {
            Context context, AccountService accountService ->
                context.request.getBody().then { typed ->
                    List<Account> accounts = accountService.selectAllAccounts()
                    ObjectMapper objectMapper = new ObjectMapper()
                    String json = objectMapper.writeValueAsString(accounts)
                    render(json)
                }
        }

        get ('categories') {
            Context context, CategoryService categoryService ->
                context.request.getBody().then { typed ->
                    List<Category> categories = categoryService.selectAllCategories()
                    ObjectMapper objectMapper = new ObjectMapper()
                    String json = objectMapper.writeValueAsString(categories)
                    render(json)
                }
        }

        get ('descriptions') {
            Context context, DescriptionService descriptionService ->
                context.request.getBody().then { typed ->
                    List<Description> descriptions = descriptionService.selectAllDescriptions()
                    ObjectMapper objectMapper = new ObjectMapper()
                    String json = objectMapper.writeValueAsString(descriptions)
                    render(json)
                }
        }

        //files { dir 'public' }
    }
}

import com.fasterxml.jackson.databind.ObjectMapper
import com.zaxxer.hikari.HikariConfig
import finance.domain.Category
import finance.services.CategoryService

import postgres.PostgresConfig
import postgres.PostgresModule
import postgres.SecurityConfig
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
        yaml("config.yml")
        sysProps()
        env()
        require("/postgres", PostgresConfig)
//        config {
//            port(5050)
//            yaml('application.yml')
//            require("/postgres", PostgresConfig)
//            //json('db_config.json')
//        }

        //json('db_config.json')
        //require("/postgres", PostgresConfig)
    }

    bindings {
        final ConfigData configData = ConfigData.of { ConfigDataBuilder builder ->
            builder.props(
                    ['user'        : 'henninb',
                     'password'    : 'monday1',
                     'portNumber'  : '5432',
                     'databaseName': 'finance_db',
                     'serverName'  : '192.168.100.124'])
            builder.build()
        }

        module HikariModule, { HikariConfig hikariConfig
            hikariConfig.dataSource =
                    new PostgresModule().dataSource(
                            configData.get('', PostgresConfig)

                    )
        }

        bind(CategoryService)
    }

    handlers {
        get("config") { PostgresConfig config ->
            render toJson(config)
        }

        get {
            Context context, CategoryService categoryService ->
                context.request.getBody().then { typed ->
                    List<Category> categories = categoryService.selectAllCategories()
                    ObjectMapper objectMapper = new ObjectMapper()
                    String json = objectMapper.writeValueAsString(categories)
                    println serverConfig.getBaseDir()
                    render(json)
                }
        }

        files { dir 'public' }
    }
}

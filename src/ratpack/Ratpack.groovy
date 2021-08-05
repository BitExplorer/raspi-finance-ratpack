import com.fasterxml.jackson.databind.ObjectMapper
import com.zaxxer.hikari.HikariConfig
import finance.domain.Category
import finance.services.CategoryService

import postgres.PostgresConfig
import postgres.PostgresModule
import ratpack.config.ConfigData
import ratpack.config.ConfigDataBuilder
import ratpack.handling.Context
import ratpack.hikari.HikariModule

import static ratpack.groovy.Groovy.ratpack

ratpack {
    serverConfig {
        port(5050)
        json('db_config.json')
    }
    bindings {

        final ConfigData configData = ConfigData.of { ConfigDataBuilder builder ->
            builder.props(
                    ['postgres.user'        : 'henninb',
                     'postgres.password'    : 'monday1',
                     'postgres.portNumber'  : '5432',
                     'postgres.databaseName': 'finance_db',
                     'postgres.serverName'  : '192.168.100.124'])
            builder.build()
        }

        //module MarkupTemplateModule

        module HikariModule, { HikariConfig config ->
            config.dataSource =
                    new PostgresModule().dataSource(
                            configData.get('/postgres', PostgresConfig))
        }

        bind(CategoryService)
    }

    handlers {

        get {
            Context context, CategoryService categoryService ->
                context.request.getBody().then { typed ->
                    List<Category> categories = categoryService.selectAllCategories()
                    ObjectMapper objectMapper = new ObjectMapper()
                    String json = objectMapper.writeValueAsString(categories)
                    render(json)
                }
        }

        files { dir 'public' }
    }
}

import com.zaxxer.hikari.HikariConfig
import finance.services.CategoryService

//import org.grails.orm.hibernate.HibernateDatastore
import postgres.PostgresConfig
import postgres.PostgresModule
import ratpack.config.ConfigData
import ratpack.config.ConfigDataBuilder
import ratpack.groovy.template.MarkupTemplateModule
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
                    categoryService.selectAllCategories()
                    render('see logs for details')
                }
        }

        files { dir 'public' }
    }
}

import com.zaxxer.hikari.HikariConfig
import finance.services.CategoryService

//import org.grails.orm.hibernate.HibernateDatastore
import postgres.PostgresConfig
import postgres.PostgresModule
import ratpack.config.ConfigData
import ratpack.config.ConfigDataBuilder
import ratpack.exec.Blocking
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.hikari.HikariModule
import ratpack.service.Service
import ratpack.service.StartEvent

import static ratpack.groovy.Groovy.groovyMarkupTemplate
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
                     'postgres.serverName'  : 'localhost'])
            builder.build()
        }

        module MarkupTemplateModule
        //module GormModule

//        bindInstance new Service() {
//            void onStart(StartEvent e) throws Exception {
//                e.getRegistry().get(HibernateDatastore)
//                Blocking.exec {
//                    Category.withNewSession {
//                        //if( !Person.count() ) {
//                        new Category(category: "test-me", activeStatus: true).save()
//                        //new Person(firstName: "Homer", lastName: "Simpson").save(flush:true)
//                        //new Person(firstName: "Liza", lastName: "Simpson").save(flush:true)
//                        //}
//                    }
//                }
//            }
//        }



        module(HikariModule) { config ->
            config.dataSourceClassName = 'org.postgresql.ds.PGPoolingDataSource'

            config.addDataSourceProperty("serverName", "localhost")
            config.addDataSourceProperty("databaseName", "finance_test_db")
            config.addDataSourceProperty("portNumber", "5432")
            config.addDataSourceProperty("user", "henninb")
            config.addDataSourceProperty("password", "monday1")

            config.setMaximumPoolSize(10)
            config.setMinimumIdle(30000) // mill
            config.setIdleTimeout(1) // minutes
            config.setConnectionTimeout(1500) // mill
        }

        module HikariModule, { HikariConfig config ->
            config.dataSource =
                    new PostgresModule().dataSource(
                            configData.get('/postgres', PostgresConfig))
        }

        bind(CategoryService)
    }

    handlers {
        //get("health", HealthCheckHandler)
        get {
            render groovyMarkupTemplate('index.gtpl', title: 'My Finance App')
        }

//        get( 'example') { Context ctx, ExampleService exampleService ->
//
//            ctx.request.getBody().then{typed ->
//                //render json(executor.execute(typed.text))
//                exampleService.createTables()
//                render('test')
//            }
//        }

//        get('transaction') { Context ctx ->
//            Connection connection = ctx.get(DataSource.class).getConnection()
//            PreparedStatement queryStatement = connection.prepareStatement('SELECT description FROM t_transaction WHERE transaction_id=?')
//            queryStatement.setInt(1, Integer.parseInt('10001'))
//            ResultSet resultSet = queryStatement.executeQuery()
//            resultSet.next()
//            render resultSet.getString(1)
//        }

        files { dir 'public' }
    }
}

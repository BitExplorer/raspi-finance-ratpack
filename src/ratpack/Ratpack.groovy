import com.fasterxml.jackson.databind.ObjectMapper
import com.zaxxer.hikari.HikariConfig
import finance.domain.Category
import finance.services.CategoryService

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
//        yaml("config.yml")
//        sysProps()
//        env()
//        require("/postgres", PostgresConfig)

    }

    bindings {
        Properties hikariConfigProperties = serverConfig.get("/database", Properties)
        moduleConfig(HikariModule, new HikariConfig(hikariConfigProperties))

        bind(CategoryService)
    }

    handlers {
//        get("config") { PostgresConfig config ->
//            render toJson(config)
//        }

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

package finance.services

import groovy.util.logging.Slf4j
import org.flywaydb.core.Flyway
import ratpack.service.Service
import ratpack.service.StartEvent

import javax.inject.Inject
import javax.sql.DataSource


import org.flywaydb.core.Flyway
import ratpack.service.Service
import ratpack.service.StartEvent

import javax.sql.DataSource

class FlywayService implements Service {
    void onStart(StartEvent e) {
        DataSource dataSource = e.registry.get(DataSource)

        Flyway flyway = new Flyway()

        flyway.setDataSource(dataSource)

        flyway.migrate()
    }
}
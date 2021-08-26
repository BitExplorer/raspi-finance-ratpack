package finance.repositories

import com.google.inject.Inject
import finance.domain.Category
import finance.domain.Description
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import ratpack.exec.Blocking
import ratpack.exec.Operation

import javax.sql.DataSource

import static org.jooq.generated.Tables.T_CATEGORY
import static org.jooq.generated.Tables.T_DESCRIPTION

@Log
@CompileStatic
class DescriptionRepository {
    private final DSLContext dslContext

    @Inject
    DescriptionRepository(DataSource ds) {
        this.dslContext = DSL.using(ds, SQLDialect.POSTGRES)
    }

    List<Description> descriptions() {
        return dslContext.selectFrom(T_DESCRIPTION).where(T_DESCRIPTION.ACTIVE_STATUS.endsWith(true)).fetchInto(Description)
    }
}







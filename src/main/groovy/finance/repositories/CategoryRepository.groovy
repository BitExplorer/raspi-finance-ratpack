package finance.repositories

import finance.domain.Category
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import org.jooq.DSLContext
import org.jooq.Result
import org.jooq.SQLDialect
import org.jooq.generated.tables.records.TCategoryRecord
import org.jooq.impl.DSL
import ratpack.exec.Blocking
import ratpack.exec.Operation


import com.google.inject.Inject
import javax.sql.DataSource

import static org.jooq.generated.Tables.T_CATEGORY

@Log
class CategoryRepository {
    private final DSLContext dslContext

    @Inject
    CategoryRepository(DataSource ds) {
        this.dslContext = DSL.using(ds, SQLDialect.POSTGRES)
    }

    Operation insertCategory(Category category) {
        return Blocking.op({ -> dslContext.newRecord(T_CATEGORY, category).store() });
    }

    Category selectAllCategories() {
        Result<TCategoryRecord> categories =
                dslContext.selectFrom(T_CATEGORY)
                        .fetch()
        log.info categories.toString()
        return new Category()
    }

}







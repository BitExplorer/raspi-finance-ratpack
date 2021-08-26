package finance.repositories

import finance.domain.Category
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import ratpack.exec.Blocking
import ratpack.exec.Operation
import com.google.inject.Inject
import javax.sql.DataSource

import static org.jooq.generated.Tables.T_CATEGORY

@Log
@CompileStatic
class CategoryRepository {
    private final DSLContext dslContext

    @Inject
    CategoryRepository(DataSource ds) {
        this.dslContext = DSL.using(ds, SQLDialect.POSTGRES)
    }

    Operation insertCategory(Category category) {
        return Blocking.op({ -> dslContext.newRecord(T_CATEGORY, category).store() })
    }

    List<Category> categories() {
        return dslContext.selectFrom(T_CATEGORY).where(T_CATEGORY.ACTIVE_STATUS.eq(true))
                .orderBy(T_CATEGORY.CATEGORY_NAME.asc())
                .fetchInto(Category)
    }

}







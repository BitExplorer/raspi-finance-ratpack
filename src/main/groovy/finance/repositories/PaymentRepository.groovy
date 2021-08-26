package finance.repositories

import com.google.inject.Inject
import finance.domain.Description
import finance.domain.Payment
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL

import javax.sql.DataSource

import static org.jooq.generated.Tables.T_DESCRIPTION
import static org.jooq.generated.Tables.T_PAYMENT

@Log
@CompileStatic
class PaymentRepository {
    private final DSLContext dslContext

    @Inject
    PaymentRepository(DataSource ds) {
        this.dslContext = DSL.using(ds, SQLDialect.POSTGRES)
    }

    List<Payment> payments() {
        return dslContext.selectFrom(T_PAYMENT).where().fetchInto(Payment)
    }
}







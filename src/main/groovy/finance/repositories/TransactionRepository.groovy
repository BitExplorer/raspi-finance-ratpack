package finance.repositories

import com.google.inject.Inject
import finance.domain.Transaction
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import ratpack.exec.Blocking
import ratpack.exec.Operation

import javax.sql.DataSource

import static org.jooq.generated.Tables.T_TRANSACTION

@Log
@CompileStatic
class TransactionRepository {
    private final DSLContext dslContext

    @Inject
    TransactionRepository(DataSource ds) {
        this.dslContext = DSL.using(ds, SQLDialect.POSTGRES)
    }

    Operation insertTransaction(Transaction account) {
        return Blocking.op({ -> dslContext.newRecord(T_TRANSACTION, account).store() })
    }

    List<Transaction> selectAllTransactions() {
        return dslContext.selectFrom(T_TRANSACTION).where().fetchInto(Transaction.class)
    }
}







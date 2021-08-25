package finance.repositories

import com.google.inject.Inject
import finance.domain.Category
import finance.domain.Summary
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import ratpack.exec.Blocking
import ratpack.exec.Operation

import javax.sql.DataSource

import static org.jooq.generated.Tables.T_CATEGORY
import static org.jooq.generated.Tables.T_TRANSACTION

@Log
@CompileStatic
class SummaryRepository {
    private final DSLContext dslContext

    @Inject
    SummaryRepository(DataSource ds) {
        this.dslContext = DSL.using(ds, SQLDialect.POSTGRES)
    }


    //SELECT SUM(amount), count(amount), transaction_state FROM t_transaction WHERE account_name_owner = :accountNameOwner AND active_status = true GROUP BY transaction_state

    //SELECT COALESCE(A.debits, 0.0) - COALESCE(B.credits, 0.0) FROM ( SELECT SUM(amount) AS debits FROM t_transaction WHERE account_type = 'debit' AND transaction_state = :transactionState AND active_status = true) A,( SELECT SUM(amount) AS credits FROM t_transaction WHERE account_type = 'credit' and transaction_state = :transactionState AND active_status = true) B

//    Operation insertCategory(Category category) {
//        return Blocking.op({ -> dslContext.newRecord(T_CATEGORY, category).store() })
//    }
//
//    Summary summary(String accountNameOwner) {
//
//        //create.select(sum(BOOK.ID))
//        //      .from(BOOK)
//
//        return dslContext.select(T_TRANSACTION.AMOUNT.sum()).from(T_TRANSACTION).where(T_TRANSACTION.ACTIVE_STATUS.eq(true))
//                //.and(T_TRANSACTION.ACCOUNT_NAME_OWNER.eq(accountNameOwner))
//                //.and
//                //.groupBy(T_TRANSACTION.TRANSACTION_STATE)
//                //.fetchInto(Summary.class)
//    }

}







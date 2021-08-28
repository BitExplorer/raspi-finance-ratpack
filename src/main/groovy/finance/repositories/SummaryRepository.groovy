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
//import static org.jooq.impl.DSL.*;

import org.jooq.*;
import org.jooq.impl.*;

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
    Summary summary(String accountNameOwner) {

        //select
        //(select sum(amount) from t_transaction where active_status=true and account_name_owner='chase_kari') as totals,
        //(select sum(amount) from t_transaction where transaction_state='cleared' and active_status=true and account_name_owner='chase_kari') as totalsCleared,
        //(select sum(amount) from t_transaction where transaction_state='outstanding' and active_status=true and account_name_owner='chase_kari') as totalsOutstanding,
        //(select sum(amount) from t_transaction where transaction_state='future' and active_status=true and account_name_owner='chase_kari') as totalsFuture;

        def  TOTALS = dslContext.select(DSL.coalesce(DSL.sum(T_TRANSACTION.AMOUNT), 0.0).as("totals"))
                .from(T_TRANSACTION)
                .where(T_TRANSACTION.ACTIVE_STATUS.eq(true) & T_TRANSACTION.ACCOUNT_NAME_OWNER.eq(accountNameOwner))
                 //.fetchOneInto(BigDecimal)
                .asField("totals")

        def TOTALS_CLEARED = dslContext.select(DSL.coalesce(DSL.sum(T_TRANSACTION.AMOUNT), 0.0).as("totalsCleared"))
                .from(T_TRANSACTION)
                .where(T_TRANSACTION.ACTIVE_STATUS.eq(true) & T_TRANSACTION.ACCOUNT_NAME_OWNER.eq(accountNameOwner) & T_TRANSACTION.TRANSACTION_STATE.eq("cleared"))
                .asField("totalsCleared")

        def TOTALS_OUTSTANDING = dslContext.select(DSL.coalesce(DSL.sum(T_TRANSACTION.AMOUNT), 0.0).as("totalsOutstanding"))
                .from(T_TRANSACTION)
                .where(T_TRANSACTION.ACTIVE_STATUS.eq(true) & T_TRANSACTION.ACCOUNT_NAME_OWNER.eq(accountNameOwner) & T_TRANSACTION.TRANSACTION_STATE.eq("outstanding"))
                .asField("totalsOutstanding")

        def TOTALS_FUTURE = dslContext.select(DSL.coalesce(DSL.sum(T_TRANSACTION.AMOUNT), 0.0).as("totalsFuture"))
                .from(T_TRANSACTION)
                .where(T_TRANSACTION.ACTIVE_STATUS.eq(true) & T_TRANSACTION.ACCOUNT_NAME_OWNER.eq(accountNameOwner) & T_TRANSACTION.TRANSACTION_STATE.eq("future"))
                .asField("totalsFuture")

        return dslContext.select(TOTALS, TOTALS_CLEARED, TOTALS_OUTSTANDING, TOTALS_FUTURE)
                .fetchOneInto(Summary)

    }

}







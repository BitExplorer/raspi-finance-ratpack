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

    Summary summaryAll() {
//        select
//        (SELECT COALESCE(A.debits, 0.0) - COALESCE(B.credits, 0.0) FROM ( SELECT SUM(amount) AS debits FROM t_transaction WHERE account_type = 'debit' AND active_status = true) A,( SELECT SUM(amount) AS credits FROM t_transaction WHERE account_type = 'credit' AND active_status = true) B) as totals,
//        (SELECT COALESCE(A.debits, 0.0) - COALESCE(B.credits, 0.0) FROM ( SELECT SUM(amount) AS debits FROM t_transaction WHERE account_type = 'debit' AND transaction_state = 'cleared' AND active_status = true) A,( SELECT SUM(amount) AS credits FROM t_transaction WHERE account_type = 'credit' and transaction_state = 'cleared' AND active_status = true) B) as totalsCleared,
//        (SELECT COALESCE(A.debits, 0.0) - COALESCE(B.credits, 0.0) FROM ( SELECT SUM(amount) AS debits FROM t_transaction WHERE account_type = 'debit' AND transaction_state = 'outstanding' AND active_status = true) A,( SELECT SUM(amount) AS credits FROM t_transaction WHERE account_type = 'credit' and transaction_state = 'outstanding' AND active_status = true) B) as totalsOutstanding,
//        (SELECT COALESCE(A.debits, 0.0) - COALESCE(B.credits, 0.0) FROM ( SELECT SUM(amount) AS debits FROM t_transaction WHERE account_type = 'debit' AND transaction_state = 'future' AND active_status = true) A,( SELECT SUM(amount) AS credits FROM t_transaction WHERE account_type = 'credit' and transaction_state = 'future' AND active_status = true) B) as totalsFuture;

        Field TOTALS_DEBITS = dslContext.select(DSL.coalesce(DSL.sum(T_TRANSACTION.AMOUNT), 0.0))
                .where(T_TRANSACTION.ACTIVE_STATUS.eq(true) & T_TRANSACTION.ACCOUNT_TYPE.eq("debit")).asField("debits")

        Field TOTALS_CREDITS = dslContext.select(DSL.coalesce(DSL.sum(T_TRANSACTION.AMOUNT), 0.0))
                .where(T_TRANSACTION.ACTIVE_STATUS.eq(true) & T_TRANSACTION.ACCOUNT_TYPE.eq("credits")).asField("credits")

        // does not work
//        Field TOTALS = dslContext.select( TOTALS_DEBITS - TOTALS_CREDITS)
//                .asField("totals")

        Summary summary = dslContext.select()
                .fetchOneInto(Summary)

        return summary
    }

    Summary summary(String accountNameOwner) {

        //select
        //(select sum(amount) from t_transaction where active_status=true and account_name_owner='chase_kari') as totals,
        //(select sum(amount) from t_transaction where transaction_state='cleared' and active_status=true and account_name_owner='chase_kari') as totalsCleared,
        //(select sum(amount) from t_transaction where transaction_state='outstanding' and active_status=true and account_name_owner='chase_kari') as totalsOutstanding,
        //(select sum(amount) from t_transaction where transaction_state='future' and active_status=true and account_name_owner='chase_kari') as totalsFuture;

        Field TOTALS = dslContext.select( DSL.coalesce(DSL.sum(T_TRANSACTION.AMOUNT), 0.0).as("totals"))
                .from(T_TRANSACTION)
                .where(T_TRANSACTION.ACTIVE_STATUS.eq(true) & T_TRANSACTION.ACCOUNT_NAME_OWNER.eq(accountNameOwner))
                .asField("totals")

        Field TOTALS_CLEARED = dslContext.select(DSL.coalesce(DSL.sum(T_TRANSACTION.AMOUNT), 0.0).as("totalsCleared"))
                .from(T_TRANSACTION)
                .where(T_TRANSACTION.ACTIVE_STATUS.eq(true) & T_TRANSACTION.ACCOUNT_NAME_OWNER.eq(accountNameOwner) & T_TRANSACTION.TRANSACTION_STATE.eq("cleared"))
                .asField("totalsCleared")

        Field TOTALS_OUTSTANDING = dslContext.select(DSL.coalesce(DSL.sum(T_TRANSACTION.AMOUNT), 0.0).as("totalsOutstanding"))
                .from(T_TRANSACTION)
                .where(T_TRANSACTION.ACTIVE_STATUS.eq(true) & T_TRANSACTION.ACCOUNT_NAME_OWNER.eq(accountNameOwner) & T_TRANSACTION.TRANSACTION_STATE.eq("outstanding"))
                .asField("totalsOutstanding")

        Field TOTALS_FUTURE = dslContext.select(DSL.coalesce(DSL.sum(T_TRANSACTION.AMOUNT), 0.0).as("totalsFuture"))
                .from(T_TRANSACTION)
                .where(T_TRANSACTION.ACTIVE_STATUS.eq(true) & T_TRANSACTION.ACCOUNT_NAME_OWNER.eq(accountNameOwner) & T_TRANSACTION.TRANSACTION_STATE.eq("future"))
                .asField("totalsFuture")

        //Field<String> field = new F
        Summary summary = dslContext.select(TOTALS, TOTALS_CLEARED, TOTALS_OUTSTANDING, TOTALS_FUTURE)
                .fetchOneInto(Summary)
        summary.accountNameOwner = accountNameOwner
        return summary

    }

}







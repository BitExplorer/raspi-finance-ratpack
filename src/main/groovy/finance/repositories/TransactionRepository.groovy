package finance.repositories

import com.google.inject.Inject
import finance.domain.Parameter
import finance.domain.Transaction
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import ratpack.exec.Blocking
import ratpack.exec.Operation

import javax.sql.DataSource

import static org.jooq.generated.Tables.T_PARAMETER
import static org.jooq.generated.Tables.T_PARAMETER
import static org.jooq.generated.Tables.T_TRANSACTION

@Log
@CompileStatic
class TransactionRepository {
    private final DSLContext dslContext

    @Inject
    TransactionRepository(DataSource dataSource) {
        this.dslContext = DSL.using(dataSource, SQLDialect.POSTGRES)
    }

    //Operation insertTransaction(Transaction transaction) {
    boolean insertTransaction(Transaction transaction) {
        dslContext.newRecord(T_TRANSACTION, transaction).store()
        return true
//
//        // Load a jOOQ-generated BookRecord from your POJO
//        BookRecord book = create.newRecord(BOOK, myBook);
//
//// Insert it (implicitly)
//        book.store();
//
//// Insert it (explicitly)
//        create.executeInsert(book);

        //return Blocking.op({ -> dslContext.newRecord(T_TRANSACTION, transaction).store() })
    }

    List<Transaction> transactionsAll() {
        return dslContext.selectFrom(T_TRANSACTION).where().fetchInto(Transaction)
    }

    List<Transaction> transactions(String accountNameOwner) {
        return dslContext.selectFrom(T_TRANSACTION)
                         .where(T_TRANSACTION.ACCOUNT_NAME_OWNER.equal(accountNameOwner))
                         .orderBy(T_TRANSACTION.TRANSACTION_STATE.desc(), T_TRANSACTION.TRANSACTION_DATE.desc())
                         .fetchInto(Transaction)
    }

    Transaction findByGuid(String guid) {
        return dslContext.selectFrom(T_TRANSACTION).where(T_TRANSACTION.GUID.equal(guid)).fetchOneInto(Transaction)
    }

    boolean deleteTransaction(String guid) {
        dslContext.delete(T_TRANSACTION)
                  .where(T_TRANSACTION.GUID.equal(guid))
                  .execute()
        return true
    }
}







package finance.repositories

import com.google.inject.Inject
import finance.domain.Account
import finance.domain.Description
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL

import javax.sql.DataSource

import static org.jooq.generated.Tables.T_ACCOUNT
import static org.jooq.generated.Tables.T_DESCRIPTION

@Log
@CompileStatic
class AccountRepository {
    private final DSLContext dslContext

    @Inject
    AccountRepository(DataSource ds) {
        this.dslContext = DSL.using(ds, SQLDialect.POSTGRES)
    }

    List<Account> selectAllAccounts() {
        return dslContext.selectFrom(T_ACCOUNT).where().fetchInto(Account.class)
    }
}







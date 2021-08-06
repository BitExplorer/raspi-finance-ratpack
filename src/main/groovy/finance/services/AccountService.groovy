package finance.services

import finance.domain.Account
import finance.domain.Description
import finance.repositories.AccountRepository
import finance.repositories.DescriptionRepository
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import ratpack.service.Service

import javax.inject.Inject

@Log
@CompileStatic
class AccountService implements Service {

    private AccountRepository accountRepository

    @Inject
    AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository
    }

    List<Account> selectAllAccounts() {
        return accountRepository.selectAllAccounts()
    }
}

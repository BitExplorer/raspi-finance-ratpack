package finance.services

import finance.domain.Account
import finance.domain.Transaction
import finance.repositories.AccountRepository
import finance.repositories.TransactionRepository
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import ratpack.service.Service

import javax.inject.Inject
import java.sql.Timestamp

@Log
@CompileStatic
class TransactionService implements Service {

    private TransactionRepository transactionRepository
    private AccountRepository accountRepository

    @Inject
    TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository
        this.accountRepository = accountRepository
    }

    List<Transaction> transactionsAll() {
        return transactionRepository.transactionsAll()
    }

    List<Transaction> transactions(String accountNameOwner) {
        return transactionRepository.transactions(accountNameOwner)
    }

    boolean deleteTransaction(String guid) {
        Transaction transaction = transactionRepository.findByGuid(guid)
        if(transaction) {
            return transactionRepository.deleteTransaction(guid)
        }
        return false
    }

    Transaction insertTransaction(Transaction transaction) {
        transaction.dateUpdated = new Timestamp(System.currentTimeMillis())
        transaction.dateAdded = new Timestamp(System.currentTimeMillis())

        Account account = accountRepository.findByAccountNameOwner(transaction.accountNameOwner)
        if(account) {
            transaction.accountType = account.accountType
            transaction.accountId = account.accountId
        }
        transactionRepository.insertTransaction(transaction)
        log.info('inserted transaction')
        return transaction
    }
}

package finance.services

import finance.domain.Account
import finance.domain.Category
import finance.domain.Transaction
import finance.repositories.AccountRepository
import finance.repositories.CategoryRepository
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
    private CategoryRepository categoryRepository

    @Inject
    TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository
        this.accountRepository = accountRepository
        this.categoryRepository = categoryRepository
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

        Category category = categoryRepository.findByCategoryName(transaction.category)
        if(!category) {
            categoryRepository.insertCategory(
                    new Category(categoryName: transaction.category, activeStatus: true)
            )
        }
        Account account = accountRepository.findByAccountNameOwner(transaction.accountNameOwner)
        if(account) {
            transaction.accountType = account.accountType
            transaction.accountId = account.accountId
            transactionRepository.insertTransaction(transaction)
            log.info("inserted transaction ${transaction.guid}")
            return transaction
        }
        throw new RuntimeException("no account found for transaction ${transaction.guid}")
    }
}

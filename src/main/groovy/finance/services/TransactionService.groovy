package finance.services

import finance.domain.Transaction
import finance.repositories.TransactionRepository
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import ratpack.service.Service

import javax.inject.Inject

@Log
@CompileStatic
class TransactionService implements Service {

    private TransactionRepository transactionRepository

    @Inject
    TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository
    }

    List<Transaction> transactionsAll() {
        return transactionRepository.transactionsAll()
    }

    List<Transaction> transactions(String accountNameOwner) {
        return transactionRepository.transactions(accountNameOwner)
    }
}

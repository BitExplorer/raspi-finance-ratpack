package finance.domain

import java.sql.Date
import java.sql.Timestamp

class Transaction {
    String guid
    Long accountId
    AccountType accountType
    String accountNameOwner
    Date transactionDate
    String description
    String category
    BigDecimal amount
    TransactionState transactionState
    ReoccurringType reoccurringType
    String notes
    Boolean activeStatus
    Timestamp dateUpdated = new Timestamp(System.currentTimeMillis())
    Timestamp dateAdded = new Timestamp(System.currentTimeMillis())
}

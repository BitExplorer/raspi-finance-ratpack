package finance.domain

import java.sql.Timestamp

class Transaction {
    String guid
    Long accountId
    AccountType accountType
    String accountNameOwner
    //Changed from java.sql.Date to String
    String transactionDate
//    String transactionDate() {
//        println('called getter.')
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
//        println(simpleDateFormat.format(this.transactionDate))
//        return simpleDateFormat.format(this.transactionDate)
//    }
//    void transactionDate(String date) {
//        this.transactionDate = date
//    }
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

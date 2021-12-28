package finance.domain

import groovy.transform.ToString

import java.sql.Date
import java.sql.Timestamp

@ToString
class Payment {
    Long paymentId
    String accountNameOwner
    BigDecimal amount
    Date transactionDate
    //String transactionDate
    String guidSource
    String guidDestination
    Boolean activeStatus
    Timestamp dateUpdated = new Timestamp(System.currentTimeMillis())
    Timestamp dateAdded = new Timestamp(System.currentTimeMillis())
}

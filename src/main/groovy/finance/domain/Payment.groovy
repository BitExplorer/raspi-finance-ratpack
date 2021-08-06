package finance.domain

import groovy.transform.ToString

import java.sql.Date

@ToString
class Payment {
    Long paymentId
    String accountNameOwner
    BigDecimal amount
    Date transactionDate
    String guidSource
    String guidDestination
    Boolean activeStatus
}

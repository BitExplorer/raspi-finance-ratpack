package finance.domain

import groovy.transform.ToString

import java.sql.Timestamp

@ToString
class Account {
    Long accountId
    String accountNameOwner
    AccountType accountType
    Boolean activeStatus
    String moniker
    BigDecimal outstanding
    BigDecimal future
    BigDecimal cleared
    Timestamp dateClosed
}

package finance.domain

import com.fasterxml.jackson.annotation.JsonGetter
import groovy.transform.ToString

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

@ToString
class Payment {
    Long paymentId
    String accountNameOwner
    BigDecimal amount
    Date transactionDate
    String guidSource
    String guidDestination
    Boolean activeStatus
    Timestamp dateUpdated = new Timestamp(System.currentTimeMillis())
    Timestamp dateAdded = new Timestamp(System.currentTimeMillis())

    @JsonGetter("transactionDate")
    String jsonGetterTransactionDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
        simpleDateFormat.lenient = false
        return simpleDateFormat.format(this.transactionDate)
    }
}

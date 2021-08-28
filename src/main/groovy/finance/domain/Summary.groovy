package finance.domain

class Summary {
    String accountNameOwner
    BigDecimal totals = new BigDecimal(0)
    BigDecimal totalsCleared = new BigDecimal(0)
    BigDecimal totalsOutstanding = new BigDecimal(0)
    BigDecimal totalsFuture = new BigDecimal(0)
}

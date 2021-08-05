package finance.domain

import groovy.transform.ToString

@ToString
class Category {
    Long categoryId
    String category
    Boolean activeStatus
}

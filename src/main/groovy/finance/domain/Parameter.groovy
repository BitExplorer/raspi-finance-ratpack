package finance.domain

import groovy.transform.ToString

@ToString
class Parameter {
    Long parameterId
    String parameterName
    String parameterValue
    Boolean activeStatus
}

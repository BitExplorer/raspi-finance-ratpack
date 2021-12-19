package finance.services

import finance.domain.ValidationAmount
import finance.repositories.ValidationAmountRepository
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import ratpack.service.Service
import javax.inject.Inject

@Log
@CompileStatic
class ValidationAmountService implements Service {

    private ValidationAmountRepository validationAmountRepository

    @Inject
    ValidationAmountService(ValidationAmountRepository validationAmountRepository) {
        this.validationAmountRepository = validationAmountRepository
    }

    List<ValidationAmount> validationAmounts() {
        return validationAmountRepository.validationAmounts()
    }
}

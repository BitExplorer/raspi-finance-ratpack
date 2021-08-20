package finance.services


import finance.domain.Payment
import finance.repositories.PaymentRepository
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import ratpack.service.Service

import javax.inject.Inject

@Log
@CompileStatic
class PaymentService implements Service {

    private PaymentRepository paymentRepository

    @Inject
    PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository
    }

    List<Payment> payments() {
        return paymentRepository.payments()
    }
}

package finance.services


import finance.domain.Description
import finance.repositories.DescriptionRepository
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import ratpack.service.Service

import javax.inject.Inject

@Log
@CompileStatic
class DescriptionService implements Service {

    private DescriptionRepository descriptionRepository

    @Inject
    DescriptionService(DescriptionRepository descriptionRepository) {
        this.descriptionRepository = descriptionRepository
    }

    List<Description> descriptions() {
        return descriptionRepository.descriptions()
    }

    boolean descriptionInsert(Description description) {
        if(descriptionRepository.description(description.descriptionName)) {
            return false
        }
        return descriptionRepository.descriptionInsert(description)
    }
}

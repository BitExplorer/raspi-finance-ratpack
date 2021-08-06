package finance.services

import finance.domain.Category
import finance.domain.Description
import finance.repositories.CategoryRepository
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

    List<Description> selectAllDescriptions() {
        return descriptionRepository.selectAllDescriptions()
    }
}

package finance.service

import finance.repositories.CategoryRepository
import finance.services.CategoryService
import spock.lang.Ignore
import spock.lang.Specification

class CategoryServiceSpec extends  Specification {
    CategoryRepository categoryRepositoryMock = Mock(CategoryRepository)
    CategoryService categoryService = new CategoryService(categoryRepositoryMock)

    @Ignore
    void 'select all categories' () {
        when:
        categoryService.categories()

        then:
        //actualResult == expectedResult
        0 * _

    }
}

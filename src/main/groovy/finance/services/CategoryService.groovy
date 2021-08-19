package finance.services

import finance.domain.Category
import finance.repositories.CategoryRepository
import groovy.transform.CompileStatic
import groovy.util.logging.Log
import ratpack.service.Service
import javax.inject.Inject

@Log
@CompileStatic
class CategoryService implements Service {

    private CategoryRepository categoryRepository

    @Inject
    CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository
    }

    List<Category> selectAllCategories() {
        return categoryRepository.selectAllCategories()
    }

//    Category insertCategory( Category category ) {
//        this.categoryRepository.insertCategory(category)
//    }
}

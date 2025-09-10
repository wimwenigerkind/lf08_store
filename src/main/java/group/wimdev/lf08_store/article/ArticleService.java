package group.wimdev.lf08_store.article;

import group.wimdev.lf08_store.exceptionhandling.ResourceNotFoundException;
import group.wimdev.lf08_store.supplier.SupplierService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final SupplierService supplierService;

    public ArticleService(ArticleRepository articleRepository, SupplierService supplierService) {
        this.articleRepository = articleRepository;
        this.supplierService = supplierService;
    }

    public ArticleEntity create(ArticleEntity article) {
        return articleRepository.save(article);
    }

    public List<ArticleEntity> readAll() {
        return articleRepository.findAll();
    }

    public ArticleEntity readById(Long id) {
        Optional<ArticleEntity> article = articleRepository.findById(id);
        if (article.isEmpty()) {
            throw new ResourceNotFoundException("Article with id " + id + " not found");
        }
        return article.get();
    }

    public ArticleEntity update(ArticleEntity article) {
        ArticleEntity updatedArticle = readById(article.getId());
        updatedArticle.setDesignation(article.getDesignation());
        updatedArticle.setPrice(article.getPrice());
        updatedArticle.setSupplier(article.getSupplier());
        updatedArticle.setLastUpdateDate(LocalDateTime.now());
        return articleRepository.save(updatedArticle);
    }

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    public ArticleEntity readByDesignation(String designation) {
        Optional<ArticleEntity> article = articleRepository.findByDesignation(designation);
        if (article.isEmpty()) {
            throw new ResourceNotFoundException("Article with designation " + designation + " not found");
        }
        return article.get();
    }

    public ArticleEntity createForSupplier(ArticleEntity article, Long supplierId) {
        var supplier = supplierService.readById(supplierId);
        article.setSupplier(supplier);
        article.setCreateDate(LocalDateTime.now());
        article.setLastUpdateDate(LocalDateTime.now());
        return articleRepository.save(article);
    }
}

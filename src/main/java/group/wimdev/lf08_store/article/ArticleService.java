package group.wimdev.lf08_store.article;

import group.wimdev.lf08_store.currency.CurrencyService;
import group.wimdev.lf08_store.exceptionhandling.ResourceNotFoundException;
import group.wimdev.lf08_store.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final SupplierService supplierService;
    private final CurrencyService currencyService;
    private final MappingService mappingService;

    public ArticleService(ArticleRepository articleRepository, SupplierService supplierService,
                         CurrencyService currencyService, @Qualifier("articleMappingService") MappingService mappingService) {
        this.articleRepository = articleRepository;
        this.supplierService = supplierService;
        this.currencyService = currencyService;
        this.mappingService = mappingService;
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

    // Currency conversion methods
    public List<GetArticleDto> readAllWithCurrency(String currency) {
        List<ArticleEntity> articles = readAll();
        List<GetArticleDto> dtoList = new ArrayList<>();

        for (ArticleEntity article : articles) {
            GetArticleDto dto = mappingService.mapArticleToGetArticleDto(article);
            if (currency != null) {
                dto.setPrice(currencyService.convert(dto.getPrice(), dto.getCurrency(), currency));
                dto.setCurrency(currency);
            }
            dtoList.add(dto);
        }

        return dtoList;
    }

    public GetArticleDto readByIdWithCurrency(Long id, String currency) {
        ArticleEntity entity = readById(id);
        GetArticleDto dto = mappingService.mapArticleToGetArticleDto(entity);

        if (currency != null) {
            dto.setPrice(currencyService.convert(dto.getPrice(), dto.getCurrency(), currency));
            dto.setCurrency(currency);
        }

        return dto;
    }

    public GetArticleDto readByDesignationWithCurrency(String designation, String currency) {
        ArticleEntity entity = readByDesignation(designation);
        GetArticleDto dto = mappingService.mapArticleToGetArticleDto(entity);

        if (currency != null) {
            dto.setPrice(currencyService.convert(dto.getPrice(), dto.getCurrency(), currency));
            dto.setCurrency(currency);
        }

        return dto;
    }
}

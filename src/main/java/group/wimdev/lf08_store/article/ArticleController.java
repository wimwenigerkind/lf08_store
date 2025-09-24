package group.wimdev.lf08_store.article;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store/article")
public class ArticleController {

    private final ArticleService articleService;
    private final MappingService mappingService;

    public ArticleController(ArticleService articleService, @Qualifier("articleMappingService") MappingService mappingService) {
        this.articleService = articleService;
        this.mappingService = mappingService;
    }

    @GetMapping
    public ResponseEntity<List<GetArticleDto>> findAllArticles(@RequestParam(required = false) String currency) {
        List<GetArticleDto> dtoList = this.articleService.readAllWithCurrency(currency);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetArticleDto> getArticleById(@PathVariable final Long id, @RequestParam(required = false) String currency) {
        final GetArticleDto dto = this.articleService.readByIdWithCurrency(id, currency);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/designation/{designation}")
    public ResponseEntity<GetArticleDto> getArticleByDesignation(@PathVariable final String designation, @RequestParam(required = false) String currency) {
        final GetArticleDto dto = this.articleService.readByDesignationWithCurrency(designation, currency);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<GetArticleDto> createArticleForSupplier(@PathVariable final Long id, @Valid @RequestBody final AddArticleDto dto) {
        ArticleEntity newArticle = this.mappingService.mapAddArticleDtoToArticle(dto);
        newArticle = this.articleService.createForSupplier(newArticle, id);
        final GetArticleDto response = this.mappingService.mapArticleToGetArticleDto(newArticle);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
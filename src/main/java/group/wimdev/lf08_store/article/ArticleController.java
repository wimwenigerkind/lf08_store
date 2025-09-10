package group.wimdev.lf08_store.article;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
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
    public ResponseEntity<List<GetArticleDto>> findAllArticles() {
        List<ArticleEntity> all = this.articleService.readAll();
        List<GetArticleDto> dtoList = new LinkedList<>();
        for (ArticleEntity article : all) {
            dtoList.add(this.mappingService.mapArticleToGetArticleDto(article));
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetArticleDto> getArticleById(@PathVariable final Long id) {
        final var entity = this.articleService.readById(id);
        final GetArticleDto dto = this.mappingService.mapArticleToGetArticleDto(entity);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
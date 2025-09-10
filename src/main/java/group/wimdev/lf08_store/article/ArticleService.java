package group.wimdev.lf08_store.article;

import group.wimdev.lf08_store.exceptionhandling.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
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
}

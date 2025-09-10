package group.wimdev.lf08_store.article;

import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleEntity create(ArticleEntity article) {
        return articleRepository.save(article);
    }
}

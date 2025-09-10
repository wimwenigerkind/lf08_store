package group.wimdev.lf08_store.article;

import org.springframework.stereotype.Service;

@Service
public class MappingService {
    public ArticleEntity mapAddArticleDtoToArticle(AddArticleDto dto) {
        ArticleEntity newArticle = new ArticleEntity();
        newArticle.setDesignation(dto.getDesignation());
        newArticle.setPrice(dto.getPrice());
        return newArticle;
    }

    public GetArticleDto mapArticleToGetArticleDto(ArticleEntity article) {
        GetArticleDto dto = new GetArticleDto();
        dto.setId(article.getId());
        dto.setDesignation(article.getDesignation());
        dto.setPrice(article.getPrice());
        return dto;
    }

    public void updateArticleFromDto(ArticleEntity article, AddArticleDto dto) {
        article.setDesignation(dto.getDesignation());
        article.setPrice(dto.getPrice());
    }
}
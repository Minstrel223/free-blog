package cn.sakurablossom.freeblog;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TagReposity extends CrudRepository<Tag, Integer> {
    @Query(value = "SELECT * FROM tag t WHERE t.article_id=?1", nativeQuery = true)
    Iterable<Tag> findByArticleId(int articleId);
}
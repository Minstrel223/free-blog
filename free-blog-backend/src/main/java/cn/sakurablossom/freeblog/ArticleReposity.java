package cn.sakurablossom.freeblog;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArticleReposity extends CrudRepository<Article, Integer> {
    @Query(value = "SELECT * FROM article c ORDER BY c.article_id DESC LIMIT ?1,?2", nativeQuery = true)
    Iterable<Article> getSection(int start, int end);

    @Query(value = "SELECT * FROM article c WHERE c.catagory_id = ?1 ORDER BY c.article_id DESC", nativeQuery = true)
    Iterable<Article> findByCatagoryId(int id);

    @Query(value = "SELECT * FROM article WHERE article_title LIKE ?1 OR article_content LIKE ?1 ORDER BY article_id DESC", nativeQuery = true)
    Iterable<Article> Search(String keyword);
}
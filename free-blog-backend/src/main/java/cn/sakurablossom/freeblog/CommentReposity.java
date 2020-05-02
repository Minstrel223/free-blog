package cn.sakurablossom.freeblog;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CommentReposity extends CrudRepository<Comment, Integer> {
    @Query(value = "SELECT * FROM comment c WHERE c.article_id=?1 ORDER BY c.comment_id DESC", nativeQuery = true)
    Iterable<Comment> findbyArticleId(Integer article_id);

    @Query(value = "SELECT * FROM comment ORDER BY comment_id DESC LIMIT ?1,?2", nativeQuery = true)
    Iterable<Comment> getSection(Integer start, Integer length);
}
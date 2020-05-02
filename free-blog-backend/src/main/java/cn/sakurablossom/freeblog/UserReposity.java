package cn.sakurablossom.freeblog;

import org.springframework.data.repository.CrudRepository;

public interface UserReposity extends CrudRepository<User, Integer> {

}
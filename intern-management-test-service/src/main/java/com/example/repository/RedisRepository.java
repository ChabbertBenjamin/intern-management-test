package com.example.repository;

import com.example.model.Intern;
import com.example.model.InternRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RedisRepository extends CrudRepository<InternRedis,Long> {
}

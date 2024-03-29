package com.example.myhome.repository;

import com.example.myhome.domain.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    @Query("{'date' : { $gt : ?0, $lt: ?1 }, " +
            "'message' : { '$regex' : ?2 , $options: 'i'}, " +
            "'device' : { '$regex' : ?3 , $options: 'i'}}")
    List<Message> search(Date after, Date before, String message, String device);
}

package siitnocu.bezbednost.repositories;

import java.util.Date;
import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import siitnocu.bezbednost.data.Logs;

public interface LogRepository extends MongoRepository<Logs, String> {
	
	@Query("{'date' : { $gt : ?0, $lt: ?1 }, " +
            "'level' : { '$regex' : ?2 , $options: 'i'}, " +
            "'message': { '$regex' : ?3 , $options: 'i'}, " +
            "'sourceApp': { '$regex' : ?4 , $options: 'i'}, " +
            "'sourceUser' : { '$regex' : ?5 , $options: 'i'}}")
    List<Logs> search(Date after, Date before, String level, String message, String app, String user);

}
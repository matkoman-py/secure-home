package siitnocu.bezbednost.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import siitnocu.bezbednost.data.Logs;

public interface LogRepository extends MongoRepository<Logs, String> {
}
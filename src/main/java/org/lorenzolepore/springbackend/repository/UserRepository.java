package org.lorenzolepore.springbackend.repository;

import org.bson.types.ObjectId;
import org.lorenzolepore.springbackend.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findByEmail(String email);
    // User findBy_id(ObjectId id);
}

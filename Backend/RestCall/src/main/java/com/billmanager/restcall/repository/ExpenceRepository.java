package com.billmanager.restcall.repository;

import com.billmanager.restcall.dao.ExpenceDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenceRepository extends MongoRepository<ExpenceDetails,String> {
}

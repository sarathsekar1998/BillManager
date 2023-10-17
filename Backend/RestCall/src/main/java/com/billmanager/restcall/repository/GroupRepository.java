package com.billmanager.restcall.repository;

import com.billmanager.restcall.dao.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository <Group,String>{
}

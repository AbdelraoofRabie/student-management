package com.boubyan.studentmanagement.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<Entity extends BaseEntity<ID>,ID extends Serializable> extends JpaRepository<Entity, ID> {

}

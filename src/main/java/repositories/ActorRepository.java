/*
 * ActorRepository.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Administrator;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccountId(int userAccountId);

	@Query("select a from Actor a where a.id = ?1 and a.userAccount.active=true")
	Actor findOneIfActive(int actorId);

	@Query("select a from Administrator a")
	Collection<Administrator> findAllAdministrators();

	@Query("select a2 from Actor a, Actor a2 where a member of a2.follows and a.id=?1")
	Collection<Actor> findFollowers(int id);

	@Query("select distinct(t) from Actor a join a.topics t")
	Collection<String> findAllTopics();
}

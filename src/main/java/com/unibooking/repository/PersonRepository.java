package com.unibooking.repository;

import com.unibooking.domain.Person;
import com.unibooking.domain.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    Optional<Person> findByEmail(String email);

    Optional<Person> findByCode(String code);

    List<Person> findAllByRole(Role role);
}

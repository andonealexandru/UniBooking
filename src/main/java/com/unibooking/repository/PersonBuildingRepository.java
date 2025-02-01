package com.unibooking.repository;

import com.unibooking.domain.Person;
import com.unibooking.domain.PersonBuilding;
import com.unibooking.domain.PersonBuildingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonBuildingRepository extends JpaRepository<PersonBuilding, PersonBuildingId> {

    Optional<PersonBuilding> findByPersonBuildingId(PersonBuildingId personBuildingId);

    List<PersonBuilding> findAllByPersonBuildingId_Person(Person person);

    List<PersonBuilding> findAllByPersonBuildingId_PersonOrderByPersonBuildingId_Building_Code(Person person);
}

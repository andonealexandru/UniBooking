package com.unibooking.repository;

import com.unibooking.domain.Building;
import com.unibooking.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByCode(String code);

    List<Room> findAllByBuilding(Building building);
}

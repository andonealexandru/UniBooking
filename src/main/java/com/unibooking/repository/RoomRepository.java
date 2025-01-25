package com.unibooking.repository;

import com.unibooking.domain.Building;
import com.unibooking.domain.Room;
import com.unibooking.domain.enumeration.WorkstationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    Optional<Room> findByCode(String code);

    List<Room> findAllByBuildingOrderByCode(Building building);

    List<Room> findAllByBuilding_CodeOrCodeOrWorkstationTypeOrCapacityOrWorkstationCount(String buildingCode,
                                                                                    String roomCode,
                                                                                    WorkstationType workstationType,
                                                                                    Integer capacity,
                                                                                    Integer workstationCount);
}

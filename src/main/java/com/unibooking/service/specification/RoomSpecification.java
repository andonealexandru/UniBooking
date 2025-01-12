package com.unibooking.service.specification;

import com.unibooking.domain.Building;
import com.unibooking.domain.Room;
import com.unibooking.domain.enumeration.WorkstationType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecification {

    public static Specification<Room> noSpecifiation() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public static Specification<Room> hasBuildingCode(String buildingCode) {
        return (root, query, criteriaBuilder) -> {
            Join<Building, Room> roomsBuilding = root.join("building");
            return criteriaBuilder.equal(roomsBuilding.get("code"), buildingCode);
        };
    }

    public static Specification<Room> hasRoomCode(String roomCode) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("code"), roomCode);
    }

    public static Specification<Room> hasWorkstationType(WorkstationType workstationType) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("workstationType"), workstationType);
    }

    public static Specification<Room> hasMinimumCapacity(Integer capacity) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("capacity"), capacity);
    }

    public static Specification<Room> hasMinimumWorkstationCount(Integer workstationCount) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("workstationCount"), workstationCount);
    }
}

package com.unibooking.service.specification;

import com.unibooking.domain.Person;
import com.unibooking.domain.enumeration.Role;
import org.springframework.data.jpa.domain.Specification;

public class PersonSpecification {

    public static Specification<Person> noSpecifiation() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public static Specification<Person> emailContains(String email) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), '%' + email + '%'));
    }

    public static Specification<Person> codeContains(String code) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), '%' + code + '%'));
    }

    public static Specification<Person> hasRole(Role role) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("role"), role.toString()));
    }

    public static Specification<Person> firstNameContains(String fistName) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), '%' + fistName + '%'));
    }

    public static Specification<Person> lastNameContains(String lastName) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), '%' + lastName + '%'));
    }
}

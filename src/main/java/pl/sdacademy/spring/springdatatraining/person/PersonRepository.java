package pl.sdacademy.spring.springdatatraining.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

//    @Query(value = "SELECT p FROM Person p WHERE p.name = :name", nativeQuery = false)
//    List<Person> findMultiplePeopleByName(@Param("name") String name);
//
//    List<Person> findAllByName(String name);

    List<Person> findByNameIgnoreCase(String name);

    List<Person> findAllBySexIgnoreCase(String sex);

    List<Person> findAllBySexAndLastName(String sex, String name);

    List<Person> findAllByLastNameIgnoreCaseOrderByBirthDateAsc(String lastName);
}

package pl.sdacademy.spring.springdatatraining;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.sdacademy.spring.springdatatraining.person.Person;
import pl.sdacademy.spring.springdatatraining.person.PersonReader;
import pl.sdacademy.spring.springdatatraining.person.PersonRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
class SpringDataTrainingApplicationTests {

    @Autowired
    private PersonReader personReader;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void initializeTestData() {
        List<Person> readPeople = personReader.read();
        personRepository.saveAll(readPeople);
    }

    @Test
    void shouldFindAllPeople() {
        // when
        List<Person> people = personRepository.findAll(); // TODO call a proper method on the repository so the test passes;

        // then
        Assertions.assertEquals(2000, people.size());
    }

//    @Test
//    void shouldRemovePersonWithId1000() {
//        // when
//        personRepository.deleteById(1000L); // TODO call a method to remove a person with id = 1000
//        List<Person> people = personRepository.findAll(); // TODO find all remaining people
//
//        // then
//        Assertions.assertEquals(1999, people.size());
//    }

    @Test
    void shouldRemovePersonWithGivenId() {
        // given
        long id = 1000L;
        Assertions.assertTrue(personRepository.findById(id).isPresent());

        // when
        personRepository.deleteById(id);

        // then
        Assertions.assertFalse(personRepository.findById(id).isPresent());
    }

    @Test
    void shouldFindAllPeopleWithNameKrystyna() {
        // given
        String name = "KRYSTYNA";

        // when
        List<Person> people = personRepository.findByNameIgnoreCase(name);

        // then
        Assertions.assertEquals(9, people.size());
    }

    @Test
    void shouldFindAllMalePeople() {
        // given
        String sex = "M";

        // when
        List<Person> people = personRepository.findAllBySexIgnoreCase(sex);

        // then
        Assertions.assertEquals(1012, people.size());
    }

    @Test
    void shouldFindAllFemalesWithLastNameAdamczyk() {
        // given
        String lastName = "ADAMCZYK";
        String sex = "F";

        // when
        List<Person> people = personRepository.findAllBySexAndLastName(sex, lastName);

        // then
        Assertions.assertEquals(9, people.size());
    }

    @Test
    void shouldFindAllPeopleWithLastNameAdamczykAndSortThemByAgeDescending() {
        // given
        String lastName = "Adamczyk"; // TODO tu jest haczyk :)

        // when
        List<Person> people = personRepository.findAllByLastNameIgnoreCaseOrderByBirthDateAsc(lastName);
//        people = people.stream()
//            .sorted(Comparator.comparing(person -> Period.between(person.getBirthDate(), LocalDate.now()).getYears(), Comparator.reverseOrder()))
//            .collect(Collectors.toList());

        // then
        Assertions.assertEquals(21, people.size());
        Assertions.assertEquals("Wincenty", people.get(0).getName());
        Assertions.assertEquals("Sylwester", people.get(20).getName());
    }

    @Test
    void shouldGetAPageOfPeople() {
        // given
        PageRequest pageRequest = PageRequest.of(1, 50);

        // when
        Page<Person> people = personRepository.findAll(pageRequest);

        // then
        Assertions.assertEquals(40, people.getTotalPages());
        Assertions.assertEquals(50, people.getSize());
    }
}

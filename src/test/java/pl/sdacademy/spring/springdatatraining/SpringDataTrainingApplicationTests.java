package pl.sdacademy.spring.springdatatraining;

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

import java.util.List;

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
        List<Person> people = personRepository.methodCall(); // TODO call a proper method on the repository so the test passes;

        // then
        Assertions.assertEquals(2000, people.size());
    }

    @Test
    void shouldRemovePersonWithId1000() {
        // when
        personRepository.methodCall(1000L); // TODO call a method to remove a person with id = 1000
        List<Person> people = personRepository.methodCall(); // TODO find all remaining people

        // then
        Assertions.assertEquals(1999, people.size());
    }

    @Test
    void shouldFindAllPeopleWithNameKrystyna() {
        // given
        String name = "Krystyna";

        // when
        List<Person> people = personRepository.methodCall(name);

        // then
        Assertions.assertEquals(9, people.size());
    }

    @Test
    void shouldFindAllMalePeople() {
        // given
        String sex = "M";

        // when
        List<Person> people = personRepository.methodCall(sex);

        // then
        Assertions.assertEquals(1012, people.size());
    }

    @Test
    void shouldFindAllFemalesWithLastNameAdamczyk() {
        // given
        String lastName = "ADAMCZYK";
        String sex = "F";

        // when
        List<Person> people = personRepository.methodCall(lastName, sex);

        // then
        Assertions.assertEquals(9, people.size());
    }

    @Test
    void shouldFindAllPeopleWithNameAdamczykAndSortThemByAgeDescending() {
        // given
        String lastName = "Adamczyk"; // TODO tu jest haczyk :)

        // when
        List<Person> people = personRepository.methodCall(lastName);

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
        Page<Person> people = personRepository.methodCall(pageRequest);

        // then
        Assertions.assertEquals(40, people.getTotalPages());
        Assertions.assertEquals(50, people.getSize());
    }
}

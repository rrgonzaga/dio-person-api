package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.dto.ResponseDTO;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public ResponseDTO createPerson(PersonDTO personDTO) {

        Person personToSave = personMapper.toModel(personDTO);

        Person savedPerson = personRepository.save(personToSave);
        return ResponseDTO
                .builder()
                .message("Created person with ID " + savedPerson.getId())
                .data(savedPerson)
                .build();
    }


    public ResponseDTO listAll() {
        List<Person> allPeople = personRepository.findAll();

        if(allPeople.size() > 0) {
            List<PersonDTO> allPeopleDTO = allPeople
                    .stream()
                    .map(personMapper::toDTO)
                    .collect(Collectors.toList());

            return ResponseDTO
                    .builder()
                    .message("Amount people: " + allPeopleDTO.size())
                    .data(allPeopleDTO)
                    .build();

        } else {
            return ResponseDTO
                    .builder()
                    .message("Amount people: 0")
                    .build();
        }
    }
}

package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.dto.ResponseDTO;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
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
        return createResponseDTO(savedPerson, "Created person with ID ");
    }


    public ResponseDTO listAll() {
        List<Person> allPeople = personRepository.findAll();

        if(allPeople.size() > 0) {
            List<PersonDTO> allPeopleDTO = allPeople
                    .stream()
                    .map(personMapper::toDTO)
                    .collect(Collectors.toList());
            return this.createResponseDTO("Amount people: " + allPeopleDTO.size(),allPeopleDTO);
        } else {
            return this.createResponseDTO("Amount people: 0",null);
        }
    }

    public ResponseDTO findById(Long id) {
        Person person = getPerson(id);
        return ResponseDTO.builder()
                .data(personMapper.toDTO(person))
                .build();
    }

    public void delete(Long id) {
        Person person = getPerson(id);
        personRepository.delete(person);
    }    

    public ResponseDTO updateById(Long id, PersonDTO personDTO) {

        Person person = getPerson(id);

        personDTO.setId(id);
        Person personToSave = personMapper.toModel(personDTO);

        Person updatedPerson = personRepository.save(personToSave);
        return createResponseDTO(updatedPerson, "Updated person with ID ");
    }

    private ResponseDTO createResponseDTO(Person savedPerson, String msg) {
        return ResponseDTO
                .builder()
                .message(msg + savedPerson.getId())
                .data(savedPerson)
                .build();
    }

    private ResponseDTO createResponseDTO(String msg, Object data) {
        return ResponseDTO
                .builder()
                .message(msg)
                .data(data)
                .build();
    }

    private Person getPerson(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }
}

package erceg_kritzl.service;

import erceg_kritzl.data.PersonRecord;
import erceg_kritzl.data.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Ist fuer die Verwendung der Datenbank mit Hilfe der Repository Klasse zustaendig.
 */
@Service
public class PersonService implements PersonServiceDeclaration{

    private PersonRepository repository;

    @Autowired
    PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public PersonRecord create(PersonRecord personRecord) {

        repository.save(personRecord);
        return personRecord;
    }

    @Override
    public PersonRecord delete(String id) {
        PersonRecord deleted = repository.findOne(id);
        repository.delete(deleted);
        return deleted;
    }

    @Override
    public PersonRecord findById(String id) throws EmptyResultDataAccessException {
        PersonRecord found = repository.findOne(id);
        if (found == null)
            throw new EmptyResultDataAccessException("There is no data record with id " + id, 1);
        return found;
    }

    @Override
    public List<PersonRecord> findTop50ByNameContainingIgnoreCase(String name) {
        return repository.findTop50ByNameContainingIgnoreCase(name);
    }

    @Override
    public List<PersonRecord> findTop50() {
        return repository.findAll(new PageRequest(0, 50)).getContent();
    }

    @Override
    public PersonRecord update(PersonRecord personRecord) {
        repository.save(personRecord);
        return personRecord;
    }
}

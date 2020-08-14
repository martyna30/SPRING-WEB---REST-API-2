package com.crud.tasks.respository;

import com.crud.tasks.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {//. Wewnątrz ostrych nawiasów podajemy obiekt,
    // który będziemy pobierać //CrudRepository, która jest udostępniana w bibliotekach springframework i
    // udostępnia metody oraz logikę pobierania danych z bazy danych.
    @Override
    List<Task> findAll();

    @Override
    Task save(Task task);

    @Override
    Optional<Task> findById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    long count();
}

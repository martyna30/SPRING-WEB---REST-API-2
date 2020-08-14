package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import org.junit.Assert;
import org.junit.Test;//ta adnotacja przy testach
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTest {

    @Autowired
    DbService dbService;

    @Test
    public void testSaveTask() {
        //Given
        List<Task> tasklist = new ArrayList<>();
        tasklist.add(new Task("test", "test content"));

        Task task = tasklist.get(0);

        //When
        dbService.saveTask(task);
        Long id = task.getId();

        List<Task> tasklis = dbService.getAllTasks();

        Optional<Task> tasklists = dbService.getTask(id);

        //Then
        Assert.assertTrue(tasklists.isPresent());
        Assert.assertNotNull(task);
        Assert.assertNotEquals(0, tasklis.size());

        //Clean-up:
        dbService.deleteTask(id);
    }
}

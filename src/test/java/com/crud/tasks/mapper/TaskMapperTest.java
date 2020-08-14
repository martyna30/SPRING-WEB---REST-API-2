package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTest {

   private TaskMapper taskMapper = new TaskMapper();

    @Test
    public void testMapToTask() {
     //Given
    TaskDto taskDto = new TaskDto(1L, "test", "test_content");

    //When
     Task task = taskMapper.mapToTask(taskDto);

     //Then
     Assert.assertNotNull(task);
     Assert.assertEquals(java.util.Optional.of(1L), java.util.Optional.of(task.getId()));
     Assert.assertEquals("test", task.getTitle());

     Assert.assertEquals("test_content", task.getContent());

    }

    @Test
    public void testMapToTaskDto() {
     //Given
     Task task = new Task(1L, "test", "test_content");

     //When
     TaskDto taskDto = taskMapper.mapToTaskDto(task);

     //Then
     Assert.assertNotNull(taskDto);
     Assert.assertEquals("test", task.getTitle());

     Assert.assertEquals("test_content", task.getContent());

    }

    @Test
    public void testMapToTaskDtoList() {
     //Given
     List<Task> tasklist = new ArrayList<>();
     tasklist.add(new Task(1L,"test", "test_content"));

     List<TaskDto> tasklistDto = new ArrayList<>();
     tasklistDto.add(new TaskDto(1L,"test","test_content"));

     //When
     List<TaskDto> tasklistDtos = taskMapper.mapToTaskDtoList(tasklist);

     //Then
     Assert.assertNotNull(tasklistDtos);
     Assert.assertEquals(1, tasklistDtos.size());
     Assert.assertEquals("test_content", tasklistDtos.get(0).getContent());

     Assert.assertEquals("test", tasklistDtos.get(0).getTitle());
    }
}
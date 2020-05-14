package com.cleanup.todoc.repository;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
    // --- GET --- //
    public LiveData<List<Task>> getTasks (){ return this.taskDao.getTasks(); }
    public LiveData<List<Task>> getTasksById (long id_project){ return this.taskDao.getTaskByProjectParent(id_project); }
    // --- INSERT --- //
    public void createTask (Task task) { this.taskDao.createTask(task);}
    // --- DELETE --- //
    public void deleteTask (Task task) { this.taskDao.deleteTask(task.getId()); }
    // --- UPDATE --- //
    public void updateTask (Task task) { this.taskDao.update(task); }
}

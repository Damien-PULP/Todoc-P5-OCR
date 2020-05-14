package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    // --- INSERT A NEW TASK --- //
    @Insert
    void createTask(Task task);

    // --- GET ALL TASK --- //
    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    // --- GET A TASK BY PROJECT PARENT --- //
    @Query("SELECT * FROM Task WHERE projectId = :id_project")
    LiveData<List<Task>> getTaskByProjectParent(long id_project);

    // --- UPDATE TASK --- //
    @Update
    int update(Task task);

    // --- DELETE TASK --- //
    @Query("DELETE FROM Task WHERE id = :id_task")
    int deleteTask(long id_task);
}

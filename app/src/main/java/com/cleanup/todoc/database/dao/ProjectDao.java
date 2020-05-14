package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    // --- INSERT A NEW PROJECT --- //
    @Insert
    void createProject(Project project);

    // --- GET ALL PROJECT --- //
    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getProjects();

    // --- GET A PROJECT BY ID --- //
    @Query("SELECT * FROM Project WHERE id = :id_project")
    Project getProjectById(long id_project);

    // --- UPDATE PROJECT --- //
    @Update
    int update(Project project);
}

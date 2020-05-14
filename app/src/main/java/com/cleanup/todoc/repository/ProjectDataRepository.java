package com.cleanup.todoc.repository;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao){ this.projectDao = projectDao; }

    // --- GET --- //
    public LiveData<List<Project>> getProjects () { return this.projectDao.getProjects(); }
    public Project getProjectById (long projectId) { return this.projectDao.getProjectById(projectId);}
    // --- CREATE --- //
    public void createProject (Project project){ this.projectDao.createProject(project); }
    // --- UPDATE --- //
    public void updateProject(Project project){ this.projectDao.update(project); }
}

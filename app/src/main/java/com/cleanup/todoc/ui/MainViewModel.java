package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {

    private final ProjectDataRepository projectDataSource;
    private final TaskDataRepository taskDataSource;

    private final Executor executor;

    public MainViewModel(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    // --- FOR PROJECT --- //
    public LiveData<List<Project>> getProjects (){ return this.projectDataSource.getProjects(); }
    public Project getProjectById ( long projectId){ return this.projectDataSource.getProjectById(projectId); }
    public void createProject (Project project){ executor.execute(()->this.projectDataSource.createProject(project) );}
    public void updateProject (Project project){ executor.execute(() -> this.projectDataSource.updateProject(project));}

    // --- FOR TASK --- //
    public LiveData<List<Task>> getTasks (){ return this.taskDataSource.getTasks(); }
    public LiveData<List<Task>> getTasksByProjectParent (long projectId) { return this.taskDataSource.getTasksById(projectId); }
    public void createTask (Task task){ executor.execute(()->this.taskDataSource.createTask(task)); }
    public void deleteTask (Task task){ executor.execute(()->this.taskDataSource.deleteTask(task)); }
    public void updateTask (Task task){ executor.execute(()->this.taskDataSource.updateTask(task)); }

}

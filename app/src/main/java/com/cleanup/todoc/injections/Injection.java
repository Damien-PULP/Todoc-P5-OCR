package com.cleanup.todoc.injections;

import android.content.Context;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static ProjectDataRepository provideProjectDataSource (Context context){
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new ProjectDataRepository(database.projectDao());
    }
    public static TaskDataRepository provideTaskDataSource (Context context){
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDao());
    }
    public static Executor provideExecutor (){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory (Context context){

        ProjectDataRepository dataSourceProject = provideProjectDataSource(context);
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceProject, dataSourceTask, executor);
    }
}

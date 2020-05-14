package com.cleanup.todoc.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Comparator;
import java.util.Objects;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(foreignKeys = @ForeignKey(entity = Project.class,
        parentColumns = "id",
        childColumns = "projectId"))
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long projectId;
    // Suppress warning because setName is called in constructor
    @SuppressWarnings("NullableProblems")
    @NonNull
    private String name;
    private long creationTimestamp;


    public Task(long projectId, @NonNull String name, long creationTimestamp) {
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

    // --- GETTER --- //
    public long getId() {
        return id;
    }
    public long getProjectId (){ return projectId; }
    @NonNull
    public String getName() {
        return name;
    }
    public long getCreationTimestamp(){ return creationTimestamp; }

    // --- SETTER --- //
    public void setId(long id){ this.id = id; }
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
    public void setName(@NonNull String name) {
        this.name = name;
    }
    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    // --- TASK COMPARATOR --- //
    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.name.toLowerCase().compareTo(right.name.toLowerCase());
        }
    }
    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.name.toLowerCase().compareTo(left.name.toLowerCase());
        }
    }
    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }
    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }

    // --- EQUAL --- //

    @Override
    public boolean equals(Object mo) {
        if (this == mo) return true;
        if (mo == null || getClass() != mo.getClass()) return false;
        Task mmTask = (Task) mo;
        return id ==
                mmTask.id &&
                projectId == mmTask.projectId &&
                creationTimestamp == mmTask.creationTimestamp &&
                name.equals(mmTask.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, name, creationTimestamp);
    }
}

package todocOCR.cleanup.todoc.unit;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import todocOCR.cleanup.todoc.database.TodocDatabase;
import todocOCR.cleanup.todoc.model.Project;
import todocOCR.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase database;

    private static final Task TASK1 = new Task(1L,"AAA", 123);
    private static final Task TASK2 = new Task(2L,"ZZZ", 125);
    private static final Task TASK3 = new Task(3L,"HHH", 124);

    private static final Project PROJECT1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
    private static final Project PROJECT2 = new Project(2L, "Projet Lucidia", 0xFFB4CDBA);
    private static final Project PROJECT3 = new Project(3L, "Projet Circus", 0xFFA3CED2);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb () throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
        this.database.projectDao().createProject(PROJECT1);
        this.database.projectDao().createProject(PROJECT2);
        this.database.projectDao().createProject(PROJECT3);
    }
    @After
    public void closeDb () throws Exception {
        database.close();
    }

    // --- GET --- //
    @Test
    public void getProjectsWhenInsertedInDatabase () throws InterruptedException {
        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDao().getProjects());
        assertTrue(projects.size() == 3);
    }
    // --- INSERT --- //
    @Test
    public void insertAndGetTask () throws InterruptedException {
        this.database.taskDao().createTask(TASK1);
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.size() == 1 & tasks.get(0).getName().equals("AAA"));
    }
    // --- DELETE --- //
    @Test
    public void insertAndDeleteTask () throws InterruptedException {
        this.database.taskDao().createTask(TASK1);

        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasks()).get(0);
        this.database.taskDao().deleteTask(taskAdded.getId());

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.size() == 0);
    }
    // --- COMPARATOR --- //
    @Test
    public void test_AZ_comparator () throws InterruptedException {
        this.database.taskDao().createTask(TASK1);
        this.database.taskDao().createTask(TASK2);
        this.database.taskDao().createTask(TASK3);

        final List<Task> tasks = new ArrayList<>(LiveDataTestUtil.getValue(this.database.taskDao().getTasks()));
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertEquals(tasks.get(0).getName(), TASK1.getName());
        assertEquals(tasks.get(1).getName(), TASK3.getName());
        assertEquals(tasks.get(2).getName(), TASK2.getName());
    }
    @Test
    public void test_ZA_comparator () throws InterruptedException {
        this.database.taskDao().createTask(TASK1);
        this.database.taskDao().createTask(TASK2);
        this.database.taskDao().createTask(TASK3);

        final List<Task> tasks = new ArrayList<>(LiveDataTestUtil.getValue(this.database.taskDao().getTasks()));
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertEquals(tasks.get(0).getName(), TASK2.getName());
        assertEquals(tasks.get(1).getName(), TASK3.getName());
        assertEquals(tasks.get(2).getName(), TASK1.getName());
    }
    @Test
    public void test_recent_comparator () throws InterruptedException {
        this.database.taskDao().createTask(TASK1);
        this.database.taskDao().createTask(TASK2);
        this.database.taskDao().createTask(TASK3);

        final List<Task> tasks = new ArrayList<>(LiveDataTestUtil.getValue(this.database.taskDao().getTasks()));
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertEquals(tasks.get(0).getCreationTimestamp(), TASK2.getCreationTimestamp());
        assertEquals(tasks.get(1).getCreationTimestamp(), TASK3.getCreationTimestamp());
        assertEquals(tasks.get(2).getCreationTimestamp(), TASK1.getCreationTimestamp());
    }
    @Test
    public void test_older_comparator () throws InterruptedException {
        this.database.taskDao().createTask(TASK1);
        this.database.taskDao().createTask(TASK2);
        this.database.taskDao().createTask(TASK3);

        final List<Task> tasks = new ArrayList<>(LiveDataTestUtil.getValue(this.database.taskDao().getTasks()));
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertEquals(tasks.get(0).getCreationTimestamp(), TASK1.getCreationTimestamp());
        assertEquals(tasks.get(1).getCreationTimestamp(), TASK3.getCreationTimestamp());
        assertEquals(tasks.get(2).getCreationTimestamp(), TASK2.getCreationTimestamp());
    }

}

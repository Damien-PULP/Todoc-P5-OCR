package todocOCR.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import todocOCR.cleanup.todoc.database.dao.ProjectDao;
import todocOCR.cleanup.todoc.database.dao.TaskDao;
import todocOCR.cleanup.todoc.model.Project;
import todocOCR.cleanup.todoc.model.Task;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    // --- SINGLETON --- //
    private static volatile TodocDatabase INSTANCE;

    // --- DAO --- //
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    // --- INSTANCE --- //
    public static TodocDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (TodocDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "TodocDatabasev1.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValuesProject1 = new ContentValues();
                contentValuesProject1.put("id",1L);
                contentValuesProject1.put("name","Projet Tartampion");
                contentValuesProject1.put("color",0xFFEADAD1);

                ContentValues contentValuesProject2 = new ContentValues();
                contentValuesProject2.put("id",2L);
                contentValuesProject2.put("name","Projet Lucidia");
                contentValuesProject2.put("color",0xFFB4CDBA);

                ContentValues contentValuesProject3 = new ContentValues();
                contentValuesProject3.put("id",3L);
                contentValuesProject3.put("name","Projet Circus");
                contentValuesProject3.put("color",0xFFA3CED2);

                db.insert("Project", OnConflictStrategy.IGNORE, contentValuesProject1);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValuesProject2);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValuesProject3);
            }
        };
    }
}

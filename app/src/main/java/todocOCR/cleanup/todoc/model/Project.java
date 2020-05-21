package todocOCR.cleanup.todoc.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

/**
 * <p>Models for project in which tasks are included.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity
public class Project {

    @PrimaryKey
    private long id;
    @NonNull
    private String name;
    @ColorInt
    private int color;

    public Project(long id, @NonNull String name, @ColorInt int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    // --- GETTER --- //
    public long getId() {
        return id;
    }
    @NonNull
    public String getName() {
        return name;
    }
    @ColorInt
    public int getColor() {
        return color;
    }

    // --- SETTER  --- //
    public void setId (long id){
        this.id = id;
    }
    public void setName (String name){
        this.name = name;
    }
    public void setColor (int color){
        this.color = color;
    }


    @Override
    @NonNull
    public String toString() {
        return getName();
    }

}

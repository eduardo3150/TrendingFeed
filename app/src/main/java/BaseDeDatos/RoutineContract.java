package BaseDeDatos;

import android.provider.BaseColumns;

/**
 * Created by Eduardo_Chavez on 4/4/2017.
 */

public class RoutineContract implements BaseColumns {
    public static final String TABLE_NAME = "Rutinas";
    public static final String _ID = "id";
    public static final String COLUMN_NAME_ROUTINE = "name";
    public static final String COLUMN_DESCRIPTION_ROUTINE = "description";
    public static final String COLUMN_TYPE_ROUTINE = "type";
    public static final String COLUMN_MINUTES_ROUTINE = "time";


    //CREACION DE TABLA 1
    public static final String SQL_CREATE_TABLE_ROUTINE = "CREATE TABLE "+
            TABLE_NAME + " ( " +
            _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME_ROUTINE + " TEXT NOT NULL, " +
            COLUMN_DESCRIPTION_ROUTINE + " TEXT NOT NULL, " +
            COLUMN_TYPE_ROUTINE + " TEXT NOT NULL, " +
            COLUMN_MINUTES_ROUTINE + " REAL NOT NULL ) ";

    public static final String SQL_DELETE_ENTRIES_ROUTINE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}

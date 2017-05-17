package BaseDeDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Eduardo_Chavez on 4/4/2017.
 */

public class RoutineDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "EventDb.db";

    public RoutineDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RoutineContract.SQL_CREATE_TABLE_ROUTINE);
        Log.d("Creando tabla 1", RoutineContract.SQL_CREATE_TABLE_ROUTINE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RoutineContract.SQL_DELETE_ENTRIES_ROUTINE);
        onCreate(db);
    }
}

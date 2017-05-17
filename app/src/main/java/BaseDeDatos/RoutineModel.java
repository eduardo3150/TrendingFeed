package BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Eduardo_Chavez on 4/4/2017.
 */

public class RoutineModel {
    public Context context;
    public RoutineDbHelper routineDbHelper;
    public SQLiteDatabase database;

    public RoutineModel(Context context) {
        this.context = context;
        routineDbHelper = new RoutineDbHelper(context);
        database = routineDbHelper.getWritableDatabase();
    }


    public long insertRoutine(String nombre, String descripcion, String type, int minutes){
        ContentValues values = new ContentValues();
        values.put(RoutineContract.COLUMN_NAME_ROUTINE,nombre);
        values.put(RoutineContract.COLUMN_DESCRIPTION_ROUTINE,descripcion);
        values.put(RoutineContract.COLUMN_TYPE_ROUTINE,type);
        values.put(RoutineContract.COLUMN_MINUTES_ROUTINE,minutes);

        long newRowID = database.insert(RoutineContract.TABLE_NAME,null,values);

        return newRowID;
    }

    public Cursor listAllRoutines(){
        database = routineDbHelper.getReadableDatabase();

        String[] projection = {
                RoutineContract._ID,
                RoutineContract.COLUMN_NAME_ROUTINE,
                RoutineContract.COLUMN_DESCRIPTION_ROUTINE,
                RoutineContract.COLUMN_TYPE_ROUTINE,
                RoutineContract.COLUMN_MINUTES_ROUTINE
        };

        Cursor c = database.query(RoutineContract.TABLE_NAME,projection,
                null,
                null,
                null,
                null,
                null);
        return c;
    }

    public Cursor getRoutineData(int ID){
        database = routineDbHelper.getReadableDatabase();

        String[] projection = {
                RoutineContract._ID,
                RoutineContract.COLUMN_NAME_ROUTINE,
                RoutineContract.COLUMN_DESCRIPTION_ROUTINE,
                RoutineContract.COLUMN_TYPE_ROUTINE,
                RoutineContract.COLUMN_MINUTES_ROUTINE
        };

        String where = RoutineContract._ID + " = ?";

        String [] seleccionArgs = {String.valueOf(ID)};

        Cursor c = database.query(RoutineContract.TABLE_NAME,
                projection,where,seleccionArgs,null,null,null);

        return c;
    }

    public void updateRoutine(int ID, String name, String description, String type, int minutes){
        ContentValues values = new ContentValues();
        values.put(RoutineContract.COLUMN_NAME_ROUTINE,name);
        values.put(RoutineContract.COLUMN_DESCRIPTION_ROUTINE,description);
        values.put(RoutineContract.COLUMN_TYPE_ROUTINE,type);
        values.put(RoutineContract.COLUMN_MINUTES_ROUTINE,minutes);

        String seleccion = RoutineContract._ID + " = ?";

        String [] seleccionArgs = {
                String.valueOf(ID)
        };

        database.update(RoutineContract.TABLE_NAME,values,seleccion,seleccionArgs);
    }

    public  void deleteRoutine(int ID){
        String seleccion = RoutineContract._ID + " = ?";
        String[] seleccionArgs = {
          String.valueOf(ID)
        };

        database.delete(RoutineContract.TABLE_NAME,seleccion,seleccionArgs);
    }


}

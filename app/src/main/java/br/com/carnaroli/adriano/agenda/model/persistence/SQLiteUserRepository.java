package br.com.carnaroli.adriano.agenda.model.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.carnaroli.adriano.agenda.model.entities.User;
import br.com.carnaroli.adriano.agenda.util.AppUtil;

/**
 * Created by Administrador on 30/07/2015.
 */
public class SQLiteUserRepository implements UserRepository {

    private static SQLiteUserRepository singletonInstance;

    private SQLiteUserRepository(){
        super();
    }

    public static SQLiteUserRepository getInstance(){
        if(SQLiteUserRepository.singletonInstance == null){
            SQLiteUserRepository.singletonInstance = new SQLiteUserRepository();
        }
        return SQLiteUserRepository.singletonInstance;
    }

    @Override
    public User getUser() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(UserContract.TABLE, UserContract.COLUMNS, null, null, null, null, UserContract.USERNAME);

        User user = UserContract.bind(cursor);

        db.close();
        helper.close();
        return user;
    }
}

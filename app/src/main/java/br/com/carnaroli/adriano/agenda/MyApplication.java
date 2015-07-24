package br.com.carnaroli.adriano.agenda;

import android.app.Application;

import br.com.carnaroli.adriano.agenda.util.AppUtil;

/**
 * Created by Administrador on 23/07/2015.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        AppUtil.CONTEXT = getApplicationContext();
        super.onCreate();
    }
}

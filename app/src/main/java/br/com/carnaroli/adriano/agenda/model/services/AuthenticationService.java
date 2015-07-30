package br.com.carnaroli.adriano.agenda.model.services;

import br.com.carnaroli.adriano.agenda.model.entities.User;

/**
 * Created by Administrador on 30/07/2015.
 */
public final class AuthenticationService {

    private AuthenticationService(){
        super();
    }

    public static boolean isAuthenticationValid(User user){
        User userInfo = User.getUser();
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();

        if(username.equals(user.getUsername()) && password.equals(user.getPassword())){
            return true;
        }
        return false;
    }





}

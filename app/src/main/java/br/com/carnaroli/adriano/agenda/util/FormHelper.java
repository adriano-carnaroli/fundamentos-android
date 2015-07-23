package br.com.carnaroli.adriano.agenda.util;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

import br.com.carnaroli.adriano.agenda.R;

/**
 * Created by Administrador on 22/07/2015.
 */
public final class FormHelper {

    private FormHelper(){
        super();
    }

    public static boolean requiredValidate(Context context, EditText... editTexts){
        boolean isValid = true;
        for (EditText editText : editTexts){
            String value = editText.getText() == null ? null : editText.getText().toString();
            if(editText.getText() == null || value.trim().isEmpty()){
                String errorMessage = context.getString(R.string.requiredField);
                editText.setError(errorMessage);
                isValid = false;
            }
        }
        return  isValid;
    }

}

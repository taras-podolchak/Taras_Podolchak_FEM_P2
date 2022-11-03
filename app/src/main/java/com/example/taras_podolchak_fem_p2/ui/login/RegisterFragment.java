package com.example.taras_podolchak_fem_p2.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.taras_podolchak_fem_p2.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private EditText et_email;
    private EditText et_contrasenna1;
    private EditText et_contrasenna2;
    private CheckBox cb_condicioneslegales;
    private Button btn_aceptar;
    private Button btn_volver;


    private FirebaseAuth fba = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        et_email = view.findViewById(R.id.et_email);
        et_contrasenna1 = view.findViewById(R.id.et_contrasenna1);
        et_contrasenna2 = view.findViewById(R.id.et_contrasenna2);
        cb_condicioneslegales = view.findViewById(R.id.cb_condicioneslegales);
        btn_aceptar = view.findViewById(R.id.btn_aceptar);
        btn_volver = view.findViewById(R.id.btn_volver);


        btn_aceptar.setOnClickListener(view12 -> registrarUsuario(view12));

        btn_volver.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.nav_login));
        return view;
    }

    private void registrarUsuario(View view) {
        if (!cb_condicioneslegales.isChecked()) {
            Toast.makeText(getActivity(), "Debes aceptar los términos y condiciones", Toast.LENGTH_LONG).show();
            return;
        }
        String email = et_email.getText().toString().trim();
        String contrasenna1 = et_contrasenna1.getText().toString().trim();
        String contrasenna2 = et_contrasenna2.getText().toString().trim();
        String contrasenna = null;

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "Se debe ingresar el email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(contrasenna1)) {
            Toast.makeText(getActivity(), "Se debe ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(contrasenna2)) {
            Toast.makeText(getActivity(), "Se debe confirmar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        //Verificamos que las contraseñas coinciden
        if (contrasenna1.equals(contrasenna2)) {
            contrasenna = contrasenna1;
        } else {
            Toast.makeText(getActivity(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            return;
        }

        fba.createUserWithEmailAndPassword(email, contrasenna)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "El usuario registrado con exito", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(view).navigate(R.id.nav_weather_Five_Days);
                    } else
                        Toast.makeText(getActivity(), "Ya existe el usuario con el mismo mail", Toast.LENGTH_LONG).show();
                });
    }
}
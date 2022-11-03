package com.example.taras_podolchak_fem_p2.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.taras_podolchak_fem_p2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private EditText et_email;
    private EditText et_contrasenna;
    private Button btn_iniciar_sesion;
    private Button btn_registrarse;

    private FirebaseAuth fba = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        et_email = view.findViewById(R.id.et_email);
        et_contrasenna = view.findViewById(R.id.et_contrasenna);
        btn_iniciar_sesion = view.findViewById(R.id.btn_iniciar_sesion);
        btn_registrarse = view.findViewById(R.id.btn_registrarse);

        btn_iniciar_sesion.setOnClickListener(view1 -> iniciarSesion(view1));
        btn_registrarse.setOnClickListener(view12 -> Navigation.findNavController(view12).navigate(R.id.nav_register));
        return view;
    }

    private void iniciarSesion(View view) {
        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = et_email.getText().toString().trim();
        String contrasenna = et_contrasenna.getText().toString().trim();

        //Verificamos que las cajas de texto no estén vacías
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(contrasenna)) {
            Toast.makeText(getActivity(), "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        fba.signInWithEmailAndPassword(email, contrasenna)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = fba.getCurrentUser();
                        Toast.makeText(getActivity(), "Bien venido" + user.getEmail(), Toast.LENGTH_LONG).show();
                        Navigation.findNavController(view).navigate(R.id.nav_weather_Five_Days);
                    } else {
                        Toast.makeText(getActivity(), "El email o la contraseña es incorrecta", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
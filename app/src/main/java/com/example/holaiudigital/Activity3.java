package com.example.holaiudigital;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * Tercera actividad del sistema de mensajería.
 * Encargada de desplegar el historial de mensajes enviados y sus respuestas utilizando
 * un componente RecyclerView y recuperando los datos desde SharedPreferences.
 */
public class Activity3 extends AppCompatActivity {

    private RecyclerView rvHistorial;
    private TextView tvHistorialVacio;
    private ImageButton btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        // Vinculación de vistas
        rvHistorial = findViewById(R.id.rvHistorial);
        tvHistorialVacio = findViewById(R.id.tvHistorialVacio);
        btnVolver = findViewById(R.id.btnVolver);

        // Configuración del botón de retorno (retorna a MainActivity)
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finaliza la actividad actual para volver a la pantalla anterior
            }
        });

        // Configuración del administrador de diseño lineal para la lista
        rvHistorial.setLayoutManager(new LinearLayoutManager(this));

        // Cargar los datos guardados en el historial
        cargarHistorial();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar la lista en caso de cambios al volver a la pantalla
        cargarHistorial();
    }

    /**
     * Recupera la lista de mensajes persistida en SharedPreferences
     * e inicializa el adaptador del RecyclerView.
     */
    private void cargarHistorial() {
        List<MensajeHistorial> historial = HistorialPreferences.obtenerHistorial(this);

        if (historial.isEmpty()) {
            // Mostrar mensaje informativo si no existen registros
            tvHistorialVacio.setVisibility(View.VISIBLE);
            rvHistorial.setVisibility(View.GONE);
        } else {
            // Configurar el adaptador con la lista cargada
            tvHistorialVacio.setVisibility(View.GONE);
            rvHistorial.setVisibility(View.VISIBLE);
            HistorialAdapter adapter = new HistorialAdapter(this, historial);
            rvHistorial.setAdapter(adapter);
        }
    }
}
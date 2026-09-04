package com.example.holaiudigital;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Segunda actividad del sistema de mensajería.
 * Recibe los datos enviados desde MainActivity, los despliega en pantalla y permite
 * devolver un estado de respuesta ("Recibido" o "Cancelado") finalizando su ciclo de vida.
 */
public class Activity2 extends AppCompatActivity {

    // Componentes de la interfaz de usuario
    private TextView tvMensajeRecibido;
    private Button btnRecibido;
    private Button btnCancelado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        // Vinculación de vistas mediante sus identificadores únicos
        tvMensajeRecibido = findViewById(R.id.tvMensajeRecibido);
        btnRecibido = findViewById(R.id.btnRecibido);
        btnCancelado = findViewById(R.id.btnCancelado);

        // Extracción y validación del Intent que inició la actividad
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("MENSAJE_ENVIADO")) {
            String mensaje = intent.getStringExtra("MENSAJE_ENVIADO");
            tvMensajeRecibido.setText(mensaje);
        }

        // Asignación de acción al botón de confirmación "Recibido"
        btnRecibido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarRespuesta("Recibido");
            }
        });

        // Asignación de acción al botón de cancelación "Cancelado"
        btnCancelado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarRespuesta("Cancelado");
            }
        });
    }

    /**
     * Método modular encargado de construir el Intent de respuesta, establecer el resultado
     * de la actividad y finalizar la misma para retornar el control a MainActivity.
     *
     * @param respuesta Cadena de texto indicando el resultado ("Recibido" o "Cancelado").
     */
    private void enviarRespuesta(String respuesta) {
        Intent intentRespuesta = new Intent();
        // Inserción del resultado en los datos extra del Intent
        intentRespuesta.putExtra("RESPUESTA", respuesta);
        
        // Asignación del código de resultado OK y adjunción de los datos
        setResult(RESULT_OK, intentRespuesta);
        
        // Destrucción de la actividad actual para volver a la pantalla anterior en la pila de actividades
        finish();
    }
}
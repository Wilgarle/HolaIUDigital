package com.example.holaiudigital;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Actividad principal del sistema de mensajería básico.
 * Encargada de capturar la entrada del usuario, iniciar la segunda actividad mediante
 * un Intent explícito, procesar la respuesta retornada y registrar la interacción en el historial.
 */
public class MainActivity extends AppCompatActivity {

    // Componentes de la interfaz de usuario
    private EditText etMensaje;
    private Button btnEnviar;
    private TextView tvEstado;
    private FloatingActionButton btnHistorial;

    // Variable para almacenar temporalmente el último mensaje enviado entre transiciones
    private String ultimoMensajeEnviado;

    /**
     * Registro e inicialización del contrato de resultado de la actividad.
     * Al recibir la respuesta de Activity2 ("Recibido" o "Cancelado"), se obtiene la fecha actual,
     * se instancia un objeto MensajeHistorial y se guarda en SharedPreferences mediante Gson.
     */
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Formateador de fecha/hora para el registro del historial
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                    String fechaHoraActual = sdf.format(new Date());

                    // Verificación de éxito en el código de resultado y existencia de datos en el Intent devuelto
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        // Extracción de la clave "RESPUESTA" adjunta en el Intent de retorno
                        String respuesta = result.getData().getStringExtra("RESPUESTA");

                        // Actualización dinámica de la vista de estado
                        if (respuesta != null) {
                            tvEstado.setText("Estado: " + respuesta);

                            // Registro del mensaje enviado, la fecha/hora y el estado final en el historial persistente
                            MensajeHistorial mensajeObj = new MensajeHistorial(
                                    ultimoMensajeEnviado != null ? ultimoMensajeEnviado : "",
                                    fechaHoraActual,
                                    respuesta
                            );
                            HistorialPreferences.guardarMensaje(MainActivity.this, mensajeObj);
                        }
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                        // Manejo de cancelación si el usuario regresó sin confirmación explícita
                        tvEstado.setText("Estado: Operación cancelada por el usuario.");

                        // Si existía un mensaje en proceso, se guarda con estado "Cancelado"
                        if (ultimoMensajeEnviado != null && !ultimoMensajeEnviado.isEmpty()) {
                            MensajeHistorial mensajeObj = new MensajeHistorial(
                                    ultimoMensajeEnviado,
                                    fechaHoraActual,
                                    "Cancelado"
                            );
                            HistorialPreferences.guardarMensaje(MainActivity.this, mensajeObj);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Limpiar el historial al iniciar una nueva sesión de la aplicación
        if (savedInstanceState == null) {
            HistorialPreferences.limpiarHistorial(this);
        }

        // Vinculación de referencias entre elementos XML y objetos Java
        etMensaje = findViewById(R.id.etMensaje);
        btnEnviar = findViewById(R.id.btnEnviar);
        tvEstado = findViewById(R.id.tvEstado);
        btnHistorial = findViewById(R.id.btnHistorial);

        // Configuración del evento clic para abrir la pantalla de historial (Activity3)
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHistorial = new Intent(MainActivity.this, Activity3.class);
                startActivity(intentHistorial);
            }
        });

        // Configuración del escuchador de eventos para el botón de envío
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = etMensaje.getText().toString().trim();

                // Validación de datos de entrada para evitar transferencias vacías
                if (!mensaje.isEmpty()) {
                    // Almacenar el mensaje en memoria para posterior registro en el historial
                    ultimoMensajeEnviado = mensaje;

                    // Creación de Intent explícito definiendo el origen y destino estricto de la navegación
                    Intent intent = new Intent(MainActivity.this, Activity2.class);

                    // Inserción del dato de mensaje en la estructura de extras del Intent
                    intent.putExtra("MENSAJE_ENVIADO", mensaje);

                    // Lanzamiento de la actividad mediante el launcher registrado
                    activityResultLauncher.launch(intent);
                } else {
                    // Notificación en la interfaz ante error de validación
                    tvEstado.setText("Estado: Error, el mensaje está vacío.");
                }
            }
        });
    }
}
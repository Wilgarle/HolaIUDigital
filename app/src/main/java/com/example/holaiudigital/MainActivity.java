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

/**
 * Actividad principal del sistema de mensajería básico.
 * Encargada de capturar la entrada del usuario, iniciar la segunda actividad mediante
 * un Intent explícito y procesar la respuesta retornada utilizando el patrón moderno ActivityResultLauncher.
 */
public class MainActivity extends AppCompatActivity {

    // Componentes de la interfaz de usuario
    private EditText etMensaje;
    private Button btnEnviar;
    private TextView tvEstado;

    /**
     * Registro e inicialización del contrato de resultado de la actividad.
     * Se reemplaza el método obsoleto startActivityForResult() por ActivityResultLauncher para
     * garantizar la desacoplada gestión del ciclo de vida y evitar fuga de memoria o inconsistencias al rotar la pantalla.
     */
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Verificación de éxito en el código de resultado y existencia de datos en el Intent devuelto
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        // Extracción de la clave "RESPUESTA" adjunta en el Intent de retorno
                        String respuesta = result.getData().getStringExtra("RESPUESTA");
                        
                        // Actualización dinámica de la vista de estado
                        if (respuesta != null) {
                            tvEstado.setText("Estado: " + respuesta);
                        }
                    } else if (result.getResultCode() == RESULT_CANCELED) {
                        // Manejo de cancelación si el usuario regresó sin confirmación
                        tvEstado.setText("Estado: Operación cancelada por el usuario.");
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vinculación de referencias entre elementos XML y objetos Java
        etMensaje = findViewById(R.id.etMensaje);
        btnEnviar = findViewById(R.id.btnEnviar);
        tvEstado = findViewById(R.id.tvEstado);

        // Configuración del escuchador de eventos para el botón de envío
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = etMensaje.getText().toString().trim();

                // Validación de datos de entrada para evitar transferencias vacías
                if (!mensaje.isEmpty()) {
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
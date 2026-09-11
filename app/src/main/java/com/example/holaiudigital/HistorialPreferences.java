package com.example.holaiudigital;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de utilidad encargada del almacenamiento persistente del historial de mensajes.
 * Utiliza SharedPreferences para la persistencia local en disco y la librería Gson
 * para la serialización y deserialización de listas de objetos Java a formato JSON.
 */
public class HistorialPreferences {

    private static final String PREF_NAME = "historial_mensajes_pref";
    private static final String KEY_HISTORIAL_LIST = "key_historial_lista";

    /**
     * Recupera la lista de mensajes almacenados en SharedPreferences deserializando el JSON.
     *
     * @param context Contexto de la aplicación necesario para acceder al almacenamiento de preferencias.
     * @return Lista de objetos MensajeHistorial registrada previamente.
     */
    public static List<MensajeHistorial> obtenerHistorial(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = preferences.getString(KEY_HISTORIAL_LIST, null);

        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }

        // Definición del tipo genérico para deserialización de List<MensajeHistorial> mediante Gson
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MensajeHistorial>>() {}.getType();
        List<MensajeHistorial> lista = gson.fromJson(json, type);

        return (lista != null) ? lista : new ArrayList<>();
    }

    /**
     * Guarda la lista completa de mensajes en SharedPreferences serializándola a JSON.
     *
     * @param context Contexto de la aplicación.
     * @param lista   Lista de mensajes a persistir.
     */
    public static void guardarHistorial(Context context, List<MensajeHistorial> lista) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(lista);

        editor.putString(KEY_HISTORIAL_LIST, json);
        editor.apply(); // Operación asíncrona no bloqueante en el hilo principal
    }

    /**
     * Agrega un nuevo mensaje a la lista persistente de historial.
     *
     * @param context Contexto de la aplicación.
     * @param mensaje Objeto MensajeHistorial a agregar.
     */
    public static void guardarMensaje(Context context, MensajeHistorial mensaje) {
        List<MensajeHistorial> lista = obtenerHistorial(context);
        // Se agrega al inicio de la lista para que aparezca primero el mensaje más reciente
        lista.add(0, mensaje);
        guardarHistorial(context, lista);
    }

    /**
     * Limpia el historial almacenado en SharedPreferences para garantizar
     * que solo se conserven los mensajes generados en la sesión actual.
     *
     * @param context Contexto de la aplicación.
     */
    public static void limpiarHistorial(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().remove(KEY_HISTORIAL_LIST).apply();
    }
}
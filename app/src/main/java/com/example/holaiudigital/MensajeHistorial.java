package com.example.holaiudigital;

/**
 * Clase modelo que representa un elemento en el historial de mensajes.
 * Encapsula la información del mensaje enviado, la fecha/hora de registro y el estado devuelto.
 */
public class MensajeHistorial {

    private String mensaje;
    private String fecha;
    private String estado;

    /**
     * Constructor por defecto requerido para frameworks de serialización como Gson.
     */
    public MensajeHistorial() {
    }

    /**
     * Constructor parametrizado para la instanciación de nuevos mensajes en el historial.
     *
     * @param mensaje Texto del mensaje enviado.
     * @param fecha   Fecha y hora de envío en formato legible.
     * @param estado  Estado final de respuesta ("Recibido" o "Cancelado").
     */
    public MensajeHistorial(String mensaje, String fecha, String estado) {
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.estado = estado;
    }

    // Métodos de acceso (Getters y Setters)

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
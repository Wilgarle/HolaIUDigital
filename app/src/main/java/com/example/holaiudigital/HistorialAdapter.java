package com.example.holaiudigital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * Adaptador personalizado para gestionar la vinculación de datos entre la lista
 * de objetos MensajeHistorial y los componentes visuales definidos en item_historial.xml.
 */
public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    private final List<MensajeHistorial> listaMensajes;
    private final Context context;

    /**
     * Constructor del adaptador.
     *
     * @param context       Contexto de la actividad o aplicación.
     * @param listaMensajes Colección de objetos MensajeHistorial a desplegar.
     */
    public HistorialAdapter(Context context, List<MensajeHistorial> listaMensajes) {
        this.context = context;
        this.listaMensajes = listaMensajes;
    }

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflado del layout personalizado para cada elemento de la lista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historial, parent, false);
        return new HistorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {
        // Obtención del objeto MensajeHistorial en la posición actual
        MensajeHistorial mensajeItem = listaMensajes.get(position);

        // Asignación de datos a los componentes del ViewHolder
        holder.tvMensaje.setText(mensajeItem.getMensaje());
        holder.tvFecha.setText(mensajeItem.getFecha());
        holder.tvEstado.setText(mensajeItem.getEstado());

        // Personalización dinámica del color de texto del estado según la respuesta
        if ("Recibido".equalsIgnoreCase(mensajeItem.getEstado())) {
            holder.tvEstado.setTextColor(ContextCompat.getColor(context, R.color.blue_primary));
        } else if ("Cancelado".equalsIgnoreCase(mensajeItem.getEstado())) {
            holder.tvEstado.setTextColor(ContextCompat.getColor(context, R.color.blue_secondary));
        } else {
            holder.tvEstado.setTextColor(ContextCompat.getColor(context, R.color.dark_blue));
        }
    }

    @Override
    public int getItemCount() {
        return (listaMensajes != null) ? listaMensajes.size() : 0;
    }

    /**
     * ViewHolder que mantiene las referencias en memoria a las vistas de cada elemento
     * evitando llamadas repetitivas e innecesarias a findViewById.
     */
    public static class HistorialViewHolder extends RecyclerView.ViewHolder {

        TextView tvMensaje;
        TextView tvFecha;
        TextView tvEstado;

        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMensaje = itemView.findViewById(R.id.tvItemMensaje);
            tvFecha = itemView.findViewById(R.id.tvItemFecha);
            tvEstado = itemView.findViewById(R.id.tvItemEstado);
        }
    }
}
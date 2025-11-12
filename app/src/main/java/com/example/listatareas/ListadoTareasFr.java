package com.example.listatareas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listatareas.databinding.FragmentListadoTareasBinding;
import com.example.listatareas.databinding.ViewholderElementoBinding;

import java.util.List;

public class ListadoTareasFr extends Fragment {

    private FragmentListadoTareasBinding binding;
    private TareaViewModel tareaViewModel;
    private NavController navController;
    private ElementosAdapter elementosAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentListadoTareasBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // VIEW MODEL COMPARTIDO
        tareaViewModel = new ViewModelProvider(requireActivity()).get(TareaViewModel.class);
        navController = Navigation.findNavController(view);

        // navegar a NuevoElemento cuando se hace click en el FloatingActionButton
        binding.irANuevoElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_listadoTareasFr_to_nuevaTareaFr);
            }
        });

        // crear el Adaptador
        elementosAdapter = new ElementosAdapter(); // Inicializar el adapter

        // asociar el Adaptador con el RecyclerView
        binding.recyclerView.setAdapter(elementosAdapter);

        // obtener el array de Elementos, y pasarselo al Adaptador
        tareaViewModel.obtener().observe(getViewLifecycleOwner(), new Observer<List<Tarea>>() {
            @Override
            public void onChanged(List<Tarea> elementos) {
                elementosAdapter.establecerLista(elementos);
            }
        });

        // FUNCIONALIDAD: ELIMINAR ELEMENTO AL DESLIZAR [cite: 823, 824, 825, 830]
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, // No implementamos 'move' (arrastrar)
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) { // Direcciones de swipe

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // No implementamos 'move'
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Obtenemos la posición del elemento deslizado [cite: 831]
                int posicion = viewHolder.getAdapterPosition();
                // Usamos el método del adapter para obtener la tarea
                Tarea tarea = elementosAdapter.obtenerElemento(posicion);
                // Llamamos al ViewModel para eliminar la tarea [cite: 833]
                tareaViewModel.eliminar(tarea);
            }
        }).attachToRecyclerView(binding.recyclerView); // Adjuntamos el helper al RecyclerView [cite: 825]
    }

    // --- Adapter ---
    public class ElementosAdapter extends RecyclerView.Adapter<TareaViewHolder> {

        List<Tarea> elementos;

        @NonNull
        @Override
        public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TareaViewHolder(ViewholderElementoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
            Tarea elemento = elementos.get(position);
            holder.binding.nombre.setText(elemento.nombre);
            holder.binding.valoracion.setRating(elemento.valoracion);

            holder.binding.valoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser) {
                        Tarea tarea = elementos.get(holder.getAdapterPosition());
                        tareaViewModel.actualizar(tarea, rating);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return elementos != null ? elementos.size() : 0;
        }

        public void establecerLista(List<Tarea> elementos) {
            this.elementos = elementos;
            notifyDataSetChanged();
        }

        public Tarea obtenerElemento(int posicion) {
            return elementos.get(posicion);
        }
    }
    class TareaViewHolder extends RecyclerView.ViewHolder {
        final ViewholderElementoBinding binding;

        public TareaViewHolder(ViewholderElementoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posicion = getAdapterPosition();
                    if (posicion != RecyclerView.NO_POSITION) {
                        Tarea tarea = elementosAdapter.obtenerElemento(posicion);

                        tareaViewModel.seleccionar(tarea);

                        navController.navigate(R.id.action_listadoTareasFr_to_mostrarDetalleTareaFr);
                    }
                }
            });
        }
    }
}
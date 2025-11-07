package com.example.listatareas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.listatareas.databinding.FragmentListadoTareasBinding;
import com.example.listatareas.databinding.ViewholderElementoBinding;

import java.util.List;

public class ListadoTareasFr extends Fragment {

    private FragmentListadoTareasBinding binding;
    private TareaViewModel tareaViewModel;
    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
           return (binding = FragmentListadoTareasBinding.inflate(inflater, container, false)).getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //VIEW MOODEL COMPARTIDO
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
        ElementosAdapter elementosAdapter = new ElementosAdapter();

        // asociar el Adaptador con el RecyclerView
        binding.recyclerView.setAdapter(elementosAdapter);

        // obtener el array de Elementos, y pasarselo al Adaptador
        tareaViewModel.obtener().observe(getViewLifecycleOwner(), new Observer<List<Tarea>>() {
            @Override
            public void onChanged(List<Tarea> elementos) {
                elementosAdapter.establecerLista(elementos);
            }
        });

    }

    public class ElementosAdapter extends RecyclerView.Adapter<TareaViewHolder> {

        List<Tarea> elementos;

        @NonNull
        @Override

        public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ListadoTareasFr.TareaViewHolder(ViewholderElementoBinding.inflate(getLayoutInflater(), parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {

            Tarea elemento = elementos.get(position);

            holder.binding.nombre.setText(elemento.nombre);
            holder.binding.valoracion.setRating(elemento.valoracion);
        }

        @Override
        public int getItemCount() {
            return elementos != null ? elementos.size() : 0;
        }

        public void establecerLista(List<Tarea> elementos) {
            this.elementos = elementos;
            notifyDataSetChanged();
        }
    }

    class TareaViewHolder extends RecyclerView.ViewHolder {
        final ViewholderElementoBinding binding;

        public TareaViewHolder(ViewholderElementoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
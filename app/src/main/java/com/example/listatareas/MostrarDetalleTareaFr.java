package com.example.listatareas;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import com.example.listatareas.databinding.FragmentMostrarDetalleTareaBinding;

public class MostrarDetalleTareaFr extends Fragment {

    private FragmentMostrarDetalleTareaBinding binding;
    private TareaViewModel tareaViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentMostrarDetalleTareaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tareaViewModel = new ViewModelProvider(requireActivity()).get(TareaViewModel.class);

        tareaViewModel.obtenerSeleccionada().observe(getViewLifecycleOwner(), new Observer<Tarea>() {
            @Override
            public void onChanged(Tarea tarea) {
                if (tarea != null) {
                    binding.nombre.setText(tarea.nombre);
                    binding.descripcion.setText(tarea.descripcion);
                    binding.valoracion.setRating(tarea.valoracion);

                    binding.valoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            if (fromUser) {
                                tareaViewModel.actualizar(tarea, rating);
                            }
                        }
                    });
                }
            }
        });
    }
}
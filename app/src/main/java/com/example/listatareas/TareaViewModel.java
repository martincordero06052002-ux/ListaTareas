package com.example.listatareas;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class TareaViewModel extends AndroidViewModel {

    TareasRepositorio listTareas;
    MutableLiveData<List<Tarea>> listTareasMutableLiveData = new MutableLiveData<>();


    public TareaViewModel(@NonNull Application application) {
        super(application);
        //Inicializo la lista de tareas
        listTareas = new TareasRepositorio();
        listTareasMutableLiveData.setValue(listTareas.obtener());
    }

    public MutableLiveData<List<Tarea>> obtener()
    {
        return listTareasMutableLiveData;
    }

    void insertar(Tarea elemento){
        listTareas.insertar(elemento, new TareasRepositorio.Callback() {
            @Override
            public void notificarCambios(List<Tarea> elementos) {
                listTareasMutableLiveData.setValue(elementos);
            }
        });
    }

    void eliminar(Tarea elemento){
        listTareas.eliminar(elemento, new TareasRepositorio.Callback() {
            @Override
            public void notificarCambios(List<Tarea> elementos) {
                listTareasMutableLiveData.setValue(elementos);
            }
        });
    }

    void actualizar(Tarea elemento, float valoracion){
        listTareas.actualizar(elemento, valoracion, new TareasRepositorio.Callback() {
            @Override
            public void notificarCambios(List<Tarea> elementos) {
                listTareasMutableLiveData.setValue(elementos);
            }
        });
    }

    MutableLiveData<Tarea> tareaSeleccionada = new MutableLiveData<>();
    public void seleccionar(Tarea tarea) {
        tareaSeleccionada.setValue(tarea);
    }
    public MutableLiveData<Tarea> obtenerSeleccionada() {
        return tareaSeleccionada;
    }

}

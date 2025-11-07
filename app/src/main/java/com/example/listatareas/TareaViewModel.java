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
        //actualizo la variable muitable de la lista de tareas
        // para que si hay un observador pueda obtener su valor
        listTareasMutableLiveData.setValue(listTareas.obtener());
    }

    public MutableLiveData<List<Tarea>> obtener()
    {
        return listTareasMutableLiveData;
    }

    void insertar(Tarea elemento){
        //llama al método insertar del modelo, este al tener el interfac callback
        //necestita que implementemos los metodos de dicho interfaz en este caso notificarcambios
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

}

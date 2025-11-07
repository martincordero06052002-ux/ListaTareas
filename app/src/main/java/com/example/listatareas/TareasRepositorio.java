package com.example.listatareas;

import java.util.ArrayList;
import java.util.List;

public class TareasRepositorio {

    List<Tarea> tareas = new ArrayList<>();

    /*
    Recordatorio elinterfaz CallBack sirve para que la función que recive el CallBack
    pueda ejecutar una funcion especifica despues de que se complete una tarea o evento

     En este caso se crea el metodo notificarCambios que podrá ser llamada dentro de otras
     funciones de la clase.
     */
    interface Callback {
        void notificarCambios(List<Tarea> elementos);
    }

    public TareasRepositorio()
    {
        tareas.add(new Tarea("Tarea 1", "Descripción tarea 1"));
        tareas.add(new Tarea("Tarea 2", "Descripción tarea 2"));
        tareas.add(new Tarea("Tarea 3", "Descripción tarea 3"));
        tareas.add(new Tarea("Tarea 4", "Descripción tarea 4"));

    }

    List<Tarea> obtener() {
        return tareas;
    }

    void insertar(Tarea elemento, Callback callback){
        tareas.add(elemento);
        callback.notificarCambios(tareas);
    }

    void eliminar(Tarea elemento, Callback callback) {
        tareas.remove(elemento);
        callback.notificarCambios(tareas);
    }

    void actualizar(Tarea elemento, float valoracion, Callback callback) {
        elemento.valoracion = valoracion;
        callback.notificarCambios(tareas);
    }


}

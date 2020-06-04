package app.client.components.inicio;

import app.services.AccionService;
import models.Accion;

public class InicioComponent {

    private InicioTemplate inicioTemplate;
    private AccionService servicioAccion;

    public InicioComponent(){
        servicioAccion = AccionService.getService();
        this.inicioTemplate=  new InicioTemplate(this);
    }

    public InicioTemplate getInicioTemplate() {
        return this.inicioTemplate;
    }

    public Accion obtenerAccion(int numeroAccion){
        return servicioAccion.devolverAccion(numeroAccion);
    }

}
package app.services;

import logic.ControlUsuarios;
import models.Usuario;

public class UsuarioService {
    private static UsuarioService servicio;
    private ControlUsuarios cUsuario;
    private String usuarioLogeado;
    
    private UsuarioService() {
        cUsuario = new ControlUsuarios();
    }

    public boolean verificarDatos(String nombreUsuario, String claveUsuario, String tipoUsuario){
        if(cUsuario.verificarUsuario(nombreUsuario, claveUsuario, tipoUsuario)){
            this.usuarioLogeado = nombreUsuario;
            return true;
        }
        return false;
    }

    public Usuario getUsuario(){
        return cUsuario.devolverUsuario(usuarioLogeado);
    }

    public static UsuarioService getService(){
        if(servicio == null){
            servicio = new UsuarioService();
        }
        return servicio;
    }
}
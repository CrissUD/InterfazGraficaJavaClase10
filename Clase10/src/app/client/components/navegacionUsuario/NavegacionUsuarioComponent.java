package app.client.components.navegacionUsuario;

import app.client.vistaPrincipal.VistaPrincipalComponent;
import app.services.RecursosService;
import app.services.UsuarioService;
import models.Usuario;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NavegacionUsuarioComponent implements ActionListener, MouseListener {

    private NavegacionUsuarioTemplate navegacionUsuarioTemplate;
    private VistaPrincipalComponent vistaPrincipalComponent;
    private UsuarioService servicioUsuario;
    private Usuario usuarioLogeado;

    public NavegacionUsuarioComponent(VistaPrincipalComponent vistaPrincipalComponent) {
        this.vistaPrincipalComponent = vistaPrincipalComponent;
        this.servicioUsuario = UsuarioService.getService();
        this.usuarioLogeado = servicioUsuario.getUsuario();
        this.navegacionUsuarioTemplate = new NavegacionUsuarioTemplate(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.vistaPrincipalComponent.mostrarComponente(e.getActionCommand().trim());
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton boton = ((JButton) e.getSource());
        boton.setContentAreaFilled(true);
        boton.setBackground(RecursosService.getService().getColorAzulOscuro()); 
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton boton = ((JButton) e.getSource());
        boton.setContentAreaFilled(false);
    }

    public void setUsuario (){
        this.usuarioLogeado = servicioUsuario.getUsuario();
        this.navegacionUsuarioTemplate.getPSuperior().removeAll();
        this.navegacionUsuarioTemplate.crearJLabels();
        this.navegacionUsuarioTemplate.repaint();
    }

    public Usuario getUsuario(){
        return this.usuarioLogeado;
    }
    
    public NavegacionUsuarioTemplate getNavegacionUsuarioTemplate() {
        return this.navegacionUsuarioTemplate;
    }
}
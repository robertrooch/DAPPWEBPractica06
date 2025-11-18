package org.uv.vista;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;   

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private boolean loggedIn = false;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Método para procesar el inicio de sesión.
     * @return La página a la que se redirigirá.
     */
    public String login() {
        if ("admin".equals(username) && "1234".equals(password)) {
            this.loggedIn = true;
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.setAttribute("userIsLoggedIn", true);
            
            addMessage(FacesMessage.SEVERITY_INFO, "¡Bienvenido!", "Inicio de sesión exitoso.");
            return "/empleados.xhtml?faces-redirect=true";
        } else {
            this.loggedIn = false;
            addMessage(FacesMessage.SEVERITY_WARN, "Error de inicio de sesión", "Usuario o contraseña incorrectos.");
            return null;
        }
    }

    /**
     * Método para cerrar la sesión.
     * @return La página de login.
     */
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    /**
     * Método de utilidad para mostrar mensajes en la vista.
     */
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
}

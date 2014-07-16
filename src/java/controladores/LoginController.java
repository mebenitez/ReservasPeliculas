/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controladores;
import dtos.UsuariosDTO;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import services.LoginService;

@ManagedBean
@SessionScoped
public class LoginController {

	@EJB
	private LoginService servicioLogin;

	private UsuariosDTO usuario;

	public LoginController() {
		usuario = new UsuariosDTO();
	}

	public UsuariosDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuariosDTO usuario) {
		this.usuario = usuario;
	}

	public String ingresar() {
		UsuariosDTO resultado = null;
		try {
			resultado = servicioLogin.login(usuario);
			if (resultado != null) {
				usuario.setId(resultado.getId());
				return "/reservaciones.xhtml?faces-redirect=true";
			} else {
				msgError("Credenciales incorrectas");
				return "inicio";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "inicio";
	}

	public void salir() {
            try {
                RequestContext context = RequestContext.getCurrentInstance();
		FacesContext fc = FacesContext.getCurrentInstance();
		//ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		//try {
			session.invalidate();
                        context.addCallbackParam("loggetOut", true);
                        fc.getCurrentInstance().getExternalContext().redirect("inicio.xhtml");
			//ec.redirect("/" + ec.getContextName() + "inicio.jsf");
		} catch (IOException e) {
		//	e.printStackTrace();
		//	return;
		}
	}

	private static void msgError(String msg) {
		FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg,
				null);
		FacesContext.getCurrentInstance().addMessage(null, fmsg);
	}

}

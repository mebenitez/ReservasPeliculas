
import controladores.LoginController;
import dtos.HorariosDTO;
import dtos.PeliculaHorarioDTO;
import dtos.PeliculasDTO;
import dtos.ReservacionesDTO;
import dtos.UsuariosDTO;
import java.io.Serializable;
//import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import services.LoginService;
import services.ReservacionService;

/**
 * Esta es la clase que está definida como controlador de tipo Session del
 * patrón MVC
 * 
 * 
 */
@ManagedBean
@SessionScoped
public class ReservacionController implements Serializable {

	private static final long serialVersionUID = -4996341366013817159L;

	/**
	 * Objeto que interactua con la capa de persistencia
	 */
	@EJB
	private ReservacionService servicioRerserva;

	@EJB
	private LoginService servicioLogin;

	private boolean verPanel = true;
	private Integer idPelicula = -1;
	private Integer idHorario = -1;
	private Date fechaMin;
	private ReservacionesDTO reservacion;
	private UsuariosDTO user;
	private List<PeliculasDTO> peliculas;
	private List<HorariosDTO> horarios;
	private List<ReservacionesDTO> reservaciones;

	public ReservacionController() {
		user = new UsuariosDTO();
		reservacion = new ReservacionesDTO();
		peliculas = new ArrayList<>();
		horarios = new ArrayList<>();
		reservaciones = new ArrayList<>();
	}

	public Date getFechaMin() {
		fechaMin = new Date();
		return fechaMin;
	}

	public void setFechaMin(Date fechaMin) {
		this.fechaMin = fechaMin;
	}

	public UsuariosDTO getUser() {
		return user;
	}

	public void setUser(UsuariosDTO user) {
		this.user = user;
	}

	public boolean isVerPanel() {
		return verPanel;
	}

	public void setVerPanel(boolean verPanel) {
		this.verPanel = verPanel;
	}

	public List<PeliculasDTO> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(List<PeliculasDTO> peliculas) {
		this.peliculas = peliculas;
	}

	public List<HorariosDTO> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorariosDTO> horarios) {
		this.horarios = horarios;
	}

	public List<ReservacionesDTO> getReservaciones() {
		return reservaciones;
	}

	public void setReservaciones(List<ReservacionesDTO> reservaciones) {
		this.reservaciones = reservaciones;
	}

	public ReservacionesDTO getReservacion() {
		return reservacion;
	}

	public void setReservacion(ReservacionesDTO reservacion) {
		this.reservacion = reservacion;
	}

	public Integer getIdPelicula() {
		return idPelicula;
	}

	public void setIdPelicula(Integer idPelicula) {
		this.idPelicula = idPelicula;
	}

	public Integer getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(Integer idHorario) {
		this.idHorario = idHorario;
	}

	@PostConstruct
	private void cargarDatosIniciales() {
		peliculas.clear();
		horarios.clear();

		LoginController controller = (LoginController) FacesContext
				.getCurrentInstance().getExternalContext().getSessionMap()
				.get("loginController");

		try {
			user = servicioLogin.obtenerUsuario(controller.getUsuario()
					.getUsername());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			peliculas = servicioRerserva.obtenerPeliculas();
		} catch (Exception e) {			
			e.printStackTrace();
		}

		try {
			horarios = servicioRerserva.obtenerHorarios();
		} catch (Exception e) {			
			e.printStackTrace();
		}

		cargarReservacionesExistentes();
	}

	/**
	 * Reservaciones existentes del usuario
	 */
	private void cargarReservacionesExistentes() {
		reservaciones.clear();

		try {
			reservaciones = servicioRerserva
					.obtenerReservacionesPorUsuario(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (reservaciones.size() > 0) {
			verPanel = false;
		} else {
			verPanel = true;
		}
	}

	private boolean validarCampos() {
		if (reservacion.getFecha() == null) {
			msgAdv("Fecha es requerida");
			return false;
		}

		if (idPelicula == -1) {
			msgAdv("Pelicula es requerida");
			return false;
		}

		if (idHorario == -1) {
			msgAdv("Horario es requerido");
			return false;
		}

		return true;
	}

	/**
	 * La verificación es deacuerdo a la fecha que el usuario ha seleccionado en
	 * la reserva
	 * 
	 * @return
	 */
	private Integer verificarReserva() {
		PeliculaHorarioDTO ch = null;

		try {
			ch = servicioRerserva.obtenerPeliculaHorario(idPelicula, idHorario);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (ch != null) {
			reservacion.setPeliculaHorario(ch);
		}

		reservaciones.clear();
		try {
			reservaciones = servicioRerserva.obtenerReservaciones(reservacion
					.getFecha());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ch.getId();
	}

	/**
	 * Se registra la reservación de la pelicula en base a #verificarReserva()
	 */
	public void reservar() {
		if (validarCampos() == false) {
			return;
		}

		Integer idCH = verificarReserva();
		System.out.println("ID CH: " + idCH);

		boolean salir = false;
		for (ReservacionesDTO res : reservaciones) {
			if (idPelicula.equals(res.getPeliculaHorario().getPelicula().getId())
					&& idHorario.equals(res.getPeliculaHorario().getHorario().getId())){
				salir = true;
				break;
			}
		}

		if (salir) {
			msgError("La pelicula ya está reservada ese día y a esa hora");
			return;
		}

		PeliculaHorarioDTO chDTO = new PeliculaHorarioDTO();
		chDTO.setId(idCH);

		reservacion.setPeliculaHorario(chDTO);
		reservacion.setUser(user);

		double valor = 00.00;

		switch (idHorario) {
		case 1:
			valor = 40.00;
			break;
		case 2:
			valor = 45.00;
			break;
		case 3:
			valor = 50.00;
			break;
		}

		reservacion.setValor(valor);

		ReservacionesDTO resultado = null;

		try {
			resultado = servicioRerserva.registrarReserva(reservacion);
		} catch (Exception e) {
			e.printStackTrace();
			msgError("No se pudo registrar la reservación");
			return;
		}

		if (resultado != null) {
			msgInfo("Reservación exitosa");
			verPanel = false;
		} else {
			verPanel = true;
		}

		reservacion = new ReservacionesDTO();
		idPelicula = -1;
		idHorario = -1;

		cargarReservacionesExistentes();
	}

	/**
	 * El usuario pude eliminar una reserva registrada
	 * 
	 * @param reser
	 */
	public void eliminar(ReservacionesDTO reser) {
		try {
			servicioRerserva.eliminar(reser);
			msgInfo("Reservación eliminada correctamente");
		} catch (Exception e) {
			e.printStackTrace();
			msgError("No se pudo eliminar la reservación");
			return;
		}
		cargarReservacionesExistentes();
	}

	/**
	 * mensaje de advertencia
	 * 
	 * @param msg
	 */
	private static void msgAdv(String msg) {
		FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg,
				null);
		FacesContext.getCurrentInstance().addMessage(null, fmsg);
	}

	/**
	 * mensaje de error
	 * 
	 * @param msg
	 */
	private static void msgError(String msg) {
		FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg,
				null);
		FacesContext.getCurrentInstance().addMessage(null, fmsg);
	}

	/**
	 * mensaje de información
	 * 
	 * @param msg
	 */
	private static void msgInfo(String msg) {
		FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg,
				null);
		FacesContext.getCurrentInstance().addMessage(null, fmsg);
	}
}

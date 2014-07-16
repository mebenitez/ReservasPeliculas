/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.PeliculasDTO;
import dtos.PeliculaHorarioDTO;
import dtos.HorariosDTO;
import dtos.ReservacionesDTO;
import dtos.UsuariosDTO;
import entidades.Peliculas;
import entidades.PeliculaHorario;
import entidades.Horarios;
import entidades.Reservaciones;
import entidades.Usuarios;
import services.ReservacionService;

@Stateless(name = "servicioReserva")
public class ReservacionServiceImpl implements ReservacionService {

	private static final long serialVersionUID = -3928001491063637493L;

	@PersistenceContext
	private EntityManager em;

	public ReservacionServiceImpl() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PeliculasDTO> obtenerPeliculas() throws Exception {
		List<PeliculasDTO> peliculasDTO = new ArrayList<>();
		List<Peliculas> peliculas = em.createQuery("select p from Peliculas p").getResultList();

		for (Peliculas c : peliculas) {
			peliculasDTO.add(c.convert());
		}
                System.out.println("size:"+peliculas.size());

		return peliculasDTO;
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public List<HorariosDTO> obtenerHorarios() throws Exception {
		List<HorariosDTO> horariosDTO = new ArrayList<>();
		List<Horarios> horarios = em.createQuery("select h from Horarios h").getResultList();

		for (Horarios h : horarios) {
			horariosDTO.add(h.convert());
		}

		return horariosDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReservacionesDTO> obtenerReservaciones(Date fecha)
			throws Exception {
		List<ReservacionesDTO> reservacionesDTO = new ArrayList<>();
		Query query = em
				.createQuery("select r from Reservaciones r where r.fecha = :fechaConsulta");
		List<Reservaciones> reservaciones = query.setParameter("fechaConsulta",
				fecha).getResultList();

		for (Reservaciones res : reservaciones) {
			reservacionesDTO.add(res.convert());
		}

		return reservacionesDTO;
	}

	@Override
	public PeliculaHorarioDTO obtenerPeliculaHorario(Integer idPelicula,
			Integer idHorario) throws Exception {
		Query query = em
				.createQuery("Select ch from PeliculaHorario ch where ch.pelicula.id = :idPelicula and ch.horario.id = :idHorario");
		PeliculaHorario peliculaHorario = (PeliculaHorario) query
				.setParameter("idPelicula", idPelicula)
				.setParameter("idHorario", idHorario).getSingleResult();
		return peliculaHorario.convert();
	}

	@Override
	public ReservacionesDTO registrarReserva(ReservacionesDTO reservacion)
			throws Exception {

		Reservaciones res = new Reservaciones();
		res.setFecha(reservacion.getFecha());
		res.setValor(reservacion.getValor());

		Usuarios usu = new Usuarios();
		usu.setId(reservacion.getUser().getId());

		res.setUser(usu);

		PeliculaHorario ch = new PeliculaHorario();
		ch.setId(reservacion.getPeliculaHorario().getId());

		res.setPeliculaHorario(ch);

		em.persist(res);

		reservacion.setId(res.getId());

		return reservacion;
	}

	@Override
	public Boolean eliminar(ReservacionesDTO reservacion) throws Exception {
		Reservaciones reser = new Reservaciones();
                reser.setId(reservacion.getId());
		em.remove(em.merge(reser));
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReservacionesDTO> obtenerReservacionesPorUsuario(
			UsuariosDTO usuario) throws Exception {

		System.out.println("ID USUARIO: " + usuario.getId());

		List<ReservacionesDTO> reservacionesDTO = new ArrayList<>();
		Query query = em
				.createQuery("select r from Reservaciones r where r.user.id = :user");
		List<Reservaciones> reservaciones = query.setParameter("user",
				usuario.getId()).getResultList();

		for (Reservaciones res : reservaciones) {
			reservacionesDTO.add(res.convert());
		}

		return reservacionesDTO;
	}

}

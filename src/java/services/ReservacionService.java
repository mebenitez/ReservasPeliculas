/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import dtos.PeliculasDTO;
import dtos.PeliculaHorarioDTO;
import dtos.HorariosDTO;
import dtos.ReservacionesDTO;
import dtos.UsuariosDTO;

public interface ReservacionService extends Serializable {

	public List<PeliculasDTO> obtenerPeliculas() throws Exception;

	public List<HorariosDTO> obtenerHorarios() throws Exception;

	public List<ReservacionesDTO> obtenerReservaciones(Date fecha)
			throws Exception;

	public ReservacionesDTO registrarReserva(ReservacionesDTO reservacion)
			throws Exception;

	public PeliculaHorarioDTO obtenerPeliculaHorario(Integer idPelicula,
			Integer idHorario) throws Exception;

	public Boolean eliminar(ReservacionesDTO reservacion) throws Exception;

	public List<ReservacionesDTO> obtenerReservacionesPorUsuario(UsuariosDTO usuario)
			throws Exception;
}

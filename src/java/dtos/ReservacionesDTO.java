/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dtos;

import java.util.Date;

public class ReservacionesDTO {

	private Integer id;
	private Date fecha;
	private Double valor;
	private UsuariosDTO user;
	private PeliculaHorarioDTO peliculaHorario;

	public ReservacionesDTO() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public UsuariosDTO getUser() {
		return user;
	}

	public void setUser(UsuariosDTO user) {
		this.user = user;
	}

	public PeliculaHorarioDTO getPeliculaHorario() {
		return peliculaHorario;
	}

	public void setPeliculaHorario(PeliculaHorarioDTO peliculaHorario) {
		this.peliculaHorario = peliculaHorario;
	}
}

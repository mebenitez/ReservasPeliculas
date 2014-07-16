/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import dtos.ReservacionesDTO;

@Entity
@Table(name = "reservaciones")
public class Reservaciones {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(value = TemporalType.DATE)
	private Date fecha;

	private Double valor;

	@OneToOne
	@JoinColumn(name = "usuarios_id")
	private Usuarios user;

	@OneToOne
	@JoinColumn(name = "pelicula_horario_id")
	private PeliculaHorario peliculaHorario;

	public Reservaciones() {

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

	public Usuarios getUser() {
		return user;
	}

	public void setUser(Usuarios user) {
		this.user = user;
	}

	public PeliculaHorario getPeliculaHorario() {
		return peliculaHorario;
	}

	public void setPeliculaHorario(PeliculaHorario peliculaHorario) {
		this.peliculaHorario = peliculaHorario;
	}

	public ReservacionesDTO convert() {
		ReservacionesDTO dto = new ReservacionesDTO();
		dto.setId(this.id);
		dto.setFecha(this.fecha);
		dto.setValor(this.valor);
		
		if(this.peliculaHorario != null){
			dto.setPeliculaHorario(this.peliculaHorario.convert());
		}
		
		if(this.user != null){
			dto.setUser(this.user.convert());
		}
		
		return dto;
	}
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entidades;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.persistence.Entity;

import dtos.PeliculaHorarioDTO;

@Entity
@Table(name = "pelicula_horario")
public class PeliculaHorario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "peliculas_id")
	private Peliculas pelicula;

	@OneToOne
	@JoinColumn(name = "horarios_id")
	private Horarios horario;

	public PeliculaHorario() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Peliculas getPelicula() {
		return pelicula;
	}

	public void setPelicula(Peliculas pelicula) {
		this.pelicula = pelicula;
	}

	public Horarios getHorario() {
		return horario;
	}

	public void setHorario(Horarios horario) {
		this.horario = horario;
	}

	public PeliculaHorarioDTO convert() {
		PeliculaHorarioDTO dto = new PeliculaHorarioDTO();
		dto.setId(this.id);

		if (this.pelicula != null) {
			dto.setPelicula(this.pelicula.convert());
		}

		if (this.horario != null) {
			dto.setHorario(this.horario.convert());
		}

		return dto;
	}
}

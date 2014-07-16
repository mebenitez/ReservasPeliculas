/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dtos;

/**
 *
 * @author USER
 */
public class PeliculaHorarioDTO {
    private Integer id;
	private PeliculasDTO pelicula;
	private HorariosDTO horario;

	public PeliculaHorarioDTO() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PeliculasDTO getPelicula() {
		return pelicula;
	}

	public void setPelicula(PeliculasDTO pelicula) {
		this.pelicula = pelicula;
	}

	public HorariosDTO getHorario() {
		return horario;
	}

	public void setHorario(HorariosDTO horario) {
		this.horario = horario;
	}
}

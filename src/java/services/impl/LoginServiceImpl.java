/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dtos.UsuariosDTO;
import entidades.Usuarios;
import services.LoginService;

@Stateless(name = "servicioLogin")
public class LoginServiceImpl implements LoginService {

	private static final long serialVersionUID = -7175181286876049251L;

	@PersistenceContext
	private EntityManager em;

	@Override
	public UsuariosDTO login(UsuariosDTO usuario) throws Exception {
		Query query = em
				.createQuery("from Usuarios u where u.username = :username and u.clave = :password");
		query.setParameter("username", usuario.getUsername());
		query.setParameter("password", usuario.getClave());

		Usuarios resultado = (Usuarios) query.getSingleResult();

		if (resultado == null) {
			throw new Exception("No se encontraron resultados");
		}

		return resultado.convert();
	}

	@Override
	public UsuariosDTO obtenerUsuario(String username) throws Exception {
		Query query = em
				.createQuery("from Usuarios u where u.username = :username");
		query.setParameter("username", username);

		return ((Usuarios) query.getResultList().get(0)).convert();
	}
}

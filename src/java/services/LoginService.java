/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.io.Serializable;

import dtos.UsuariosDTO;

public interface LoginService extends Serializable {

	public UsuariosDTO login(UsuariosDTO usuario) throws Exception;

	public UsuariosDTO obtenerUsuario(String username) throws Exception;
}

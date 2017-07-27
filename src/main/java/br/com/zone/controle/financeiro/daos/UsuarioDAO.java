package br.com.zone.controle.financeiro.daos;

import br.com.zone.controle.financeiro.entidades.Usuario;

/**
 *
 * @author daniel
 */
public class UsuarioDAO extends DAOGenerico<Usuario>{

    public UsuarioDAO() {
        super(Usuario.class);
    }
    
    public Usuario buscarUsuarioPorLoginOuEmailESenha(String loginOuEmail, String senha){
        return buscarUmResultado(Usuario.BUSCAR_USUARIO_POR_LOGIN_OU_EMAIL_E_SENHA, loginOuEmail, loginOuEmail, senha);
    }
    
    public Usuario buscarUsuarioPorEmail(String email){
        return buscarUmResultado(Usuario.BUSCAR_USUARIO_POR_EMAIL, email);
    }
    
}

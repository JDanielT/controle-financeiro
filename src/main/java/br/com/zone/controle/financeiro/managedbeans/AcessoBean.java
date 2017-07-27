package br.com.zone.controle.financeiro.managedbeans;

import br.com.zone.controle.financeiro.daos.UsuarioDAO;
import br.com.zone.controle.financeiro.entidades.Usuario;
import br.com.zone.controle.financeiro.util.Mensagens;
import br.com.zone.controle.financeiro.util.PasswordUtil;
import br.com.zone.controle.financeiro.util.SessionUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author daniel
 */
@Named(value = "acessoBean")
@SessionScoped
public class AcessoBean implements Serializable {
    
    private String loginOuEmail;
    private String senha;
    
    @Inject
    private UsuarioDAO usuarioDAO;
    
    public String getLoginOuEmail() {
        return loginOuEmail;
    }

    public void setLoginOuEmail(String loginOuEmail) {
        this.loginOuEmail = loginOuEmail;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String logar(){
        Usuario usuario = null;
        try {
            if((usuario = usuarioDAO.buscarUsuarioPorLoginOuEmailESenha(loginOuEmail, PasswordUtil.encriptar(senha))) != null){
                SessionUtil.setSessionAttribute("usuario", usuario);
                return "inicio";
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(AcessoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        Mensagens.mensagemDeErro("Login/Email ou senha incorreta", "Verifique os dados informados");
        return "login";
    }
    
    public String sair(){
        SessionUtil.setSessionAttribute("usuario", null);
        return "login.xhtml?faces-redirect=true";
    }
    
}

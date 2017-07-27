package br.com.zone.controle.financeiro.managedbeans;

import br.com.zone.controle.financeiro.daos.UsuarioDAO;
import br.com.zone.controle.financeiro.entidades.Usuario;
import br.com.zone.controle.financeiro.util.Mensagens;
import br.com.zone.controle.financeiro.util.PasswordUtil;
import br.com.zone.controle.financeiro.util.SenderEmail;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author daniel
 */
@Named(value = "usuarioBean")
@ViewScoped
public class UsuarioBean extends AbstractBean<Usuario> {

    private final static String PAGINA_DE_CADASTRO_EDICAO = "usuario.xhtml?faces-redirect=true";
    private final static String PAGINA_DE_LISTAGEM = "usuarios.xhtml?faces-redirect=true";
    
    @Inject
    private UsuarioDAO usuarioDAO;
    
    private String confirmacaoEmail;
    private String confirmacaoSenha;
    
    private String emailParaRedefinicao;
    
    public UsuarioBean() {
        super(Usuario.class);
    }
    
    public String getConfirmacaoEmail() {
        return confirmacaoEmail;
    }

    public void setConfirmacaoEmail(String confirmacaoEmail) {
        this.confirmacaoEmail = confirmacaoEmail;
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }

    public String getEmailParaRedefinicao() {
        return emailParaRedefinicao;
    }

    public void setEmailParaRedefinicao(String emailParaRedefinicao) {
        this.emailParaRedefinicao = emailParaRedefinicao;
    }
    
    @Override
    public String salvar() {
        if(!getEntity().getEmail().equals(confirmacaoEmail)){
            Mensagens.mensagemDeErro("Os campos email e confirmação de email não conferem", null);
            return null;
        }
        if(!getEntity().getSenha().equals(confirmacaoSenha)){
            Mensagens.mensagemDeErro("Os campos senha e confirmação de senha não conferem", null);
            return null;
        }
        try {
            getEntity().setSenha(PasswordUtil.encriptar(confirmacaoSenha));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            Mensagens.mensagemDeErro("Um problema ocorreu ao salvar", null);
            return null;
        }
        return super.salvar();
    }
    
    @Override
    protected UsuarioDAO getDAO() {
        return usuarioDAO;
    }

    @Override
    public String irParaPaginaListagem() {
        return UsuarioBean.PAGINA_DE_LISTAGEM;
    }

    @Override
    public String irParaPaginaCadastroEdicao() {
        return UsuarioBean.PAGINA_DE_CADASTRO_EDICAO;
    }
 
    public void redefinirSenha(){
        
        Usuario usuario = usuarioDAO.buscarUsuarioPorEmail(emailParaRedefinicao);
        
        if(usuario == null){
            Mensagens.mensagemDeAlerta("Email não encontrado!", null);
            return;
        }
        
        String novaSenha = UUID.randomUUID().toString().substring(0,8);
        
        String titulo = "Controle financeiro - Redefinição de senha";
        String email = "Olá, sua senha foi alterada para: "+novaSenha;
        
        try {
            usuario.setSenha(PasswordUtil.encriptar(novaSenha));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        usuarioDAO.atualizar(usuario);
        
        SenderEmail senderEmail = new SenderEmail();
        senderEmail.send(titulo, email, usuario.getEmail());
        
        Mensagens.mensagemDeAlerta("A nova senha foi enviada para o seu email", null);
        
    }
    
}

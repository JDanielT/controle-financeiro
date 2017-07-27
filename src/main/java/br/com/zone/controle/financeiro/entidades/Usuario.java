package br.com.zone.controle.financeiro.entidades;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author daniel
 */
@Entity
@Table
@NamedQueries({
    @NamedQuery(name=Usuario.BUSCAR_USUARIO_POR_LOGIN_OU_EMAIL_E_SENHA,
                query="SELECT u FROM Usuario u WHERE (u.login = ?1 OR u.email = ?2) AND u.senha = ?3 ORDER BY id"),
    @NamedQuery(name=Usuario.BUSCAR_USUARIO_POR_EMAIL,
                query="SELECT u FROM Usuario u WHERE u.email = ?1 ORDER BY id")
}) 
public class Usuario implements BaseEntity {
    
    public static final String BUSCAR_USUARIO_POR_LOGIN_OU_EMAIL_E_SENHA = "Usuario.buscarUsuarioPorLoginOuEmailESenha";
    public static final String BUSCAR_USUARIO_POR_EMAIL = "Usuario.buscarUsuarioPorEmail";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotEmpty(message = "{usuario.nome.vazio}")
    @Size(min = 3, max = 60, message = "{usuario.nome.tamanho}")
    @Column(length = 60)
    private String nome;
    
    @NotEmpty(message = "{usuario.login.vazio}")
    @Size(min = 5, max = 20, message = "{usuario.login.tamanho}")
    @Column(length = 20)
    private String login;
    
    @NotEmpty(message = "{usuario.senha.vazia}")
    @Size(min = 5, max = 100, message = "{usuario.senha.tamanho}")
    @Column(length = 100)
    private String senha;
    
    @Email(message = "{usuario.email.valido}")
    @NotEmpty(message = "{usuario.email.vazio}")
    @Column(length = 60)
    private String email;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}

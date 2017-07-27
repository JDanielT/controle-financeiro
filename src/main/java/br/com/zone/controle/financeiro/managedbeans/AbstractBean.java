package br.com.zone.controle.financeiro.managedbeans;

import br.com.zone.controle.financeiro.daos.DAOGenerico;
import br.com.zone.controle.financeiro.entidades.BaseEntity;
import br.com.zone.controle.financeiro.util.Mensagens;
import br.com.zone.controle.financeiro.util.ParameterUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractBean<T extends BaseEntity> implements Serializable {

    private static final long serialVersionUID = 255521629992773976L;

    private Class<T> itemClass;
    private T entity;
    protected Collection<T> itens;
    
    public AbstractBean() {
    }

    public AbstractBean(Class<T> itemClass) {
        this.itemClass = itemClass;
    }

    public Class<T> getItemClass() {
        return itemClass;
    }

    public void setItemClass(Class<T> itemClass) {
        this.itemClass = itemClass;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Collection<T> getItens() {
        if (itens == null) {
            itens = getDAO().listarTodos();
        }
        return itens;
    }

    public void setItens(Collection<T> itens) {
        this.itens = itens;
    }
    
    public void preCadastro() {
        String id = ParameterUtil.getRequestParameter("id");
        if (id == null) {
            try {
                entity = itemClass.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(PagamentoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                setEntity(getDAO().buscarPorId(Integer.parseInt(id)));
            } catch (Exception ex) {
                Mensagens.mensagemDeAlerta("Identificador inválido", "Identificador inválido");
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String salvar() {
        if (getEntity().getId() == null) {
            getDAO().salvar(entity);
        } else {
            getDAO().atualizar(entity);
        }
        limparDados();
        return irParaPaginaListagem();
    }

    public void excluir() {
        try {
            getDAO().excluir(entity);
        } catch (Exception ex) {
            Mensagens.mensagemDeErro("Um problema ocorreu ao excluir", "Esse registro está sendo utilizado em outra tabela");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        limparDados();
    }

    public void limparDados() {
        itens = null;
        entity = null;
    }

    protected abstract DAOGenerico<T> getDAO();

    public abstract String irParaPaginaListagem();

    public abstract String irParaPaginaCadastroEdicao();

}

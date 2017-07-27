package br.com.zone.controle.financeiro.managedbeans;

import br.com.zone.controle.financeiro.daos.TipoPagamentoDAO;
import br.com.zone.controle.financeiro.entidades.TipoPagamento;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author daniel
 */
@Named(value = "tipoPagamentoBean")
@ViewScoped
public class TipoPagamentoBean extends AbstractBean<TipoPagamento> {

    private final static String PAGINA_DE_CADASTRO_EDICAO = "tipo-pagamento.xhtml?faces-redirect=true";
    private final static String PAGINA_DE_LISTAGEM = "tipos-pagamentos.xhtml?faces-redirect=true";
    
    @Inject
    private TipoPagamentoDAO tipoPagamentoDAO;
    
    public TipoPagamentoBean() {
        super(TipoPagamento.class);
    }
    
    @Override
    protected TipoPagamentoDAO getDAO() {
        return tipoPagamentoDAO;
    }

    @Override
    public String irParaPaginaListagem() {
        return TipoPagamentoBean.PAGINA_DE_LISTAGEM;
    }

    @Override
    public String irParaPaginaCadastroEdicao() {
        return TipoPagamentoBean.PAGINA_DE_CADASTRO_EDICAO;
    }
    
}

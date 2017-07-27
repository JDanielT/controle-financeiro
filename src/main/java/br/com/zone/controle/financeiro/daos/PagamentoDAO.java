package br.com.zone.controle.financeiro.daos;

import br.com.zone.controle.financeiro.entidades.Pagamento;
import br.com.zone.controle.financeiro.entidades.TipoPagamento;
import br.com.zone.controle.financeiro.entidades.Usuario;
import java.util.Date;
import java.util.List;

/**
 *
 * @author daniel
 */
public class PagamentoDAO extends DAOGenerico<Pagamento>{

    public PagamentoDAO() {
        super(Pagamento.class);
    }
    
    public List<Pagamento> listarPagamentosPorUsuario(Usuario usuario){
        return listar(Pagamento.BUSCAR_PAGAMENTOS_POR_USUARIO, usuario.getId());
    }
    
    public List<Pagamento> listarPagamentosPorTipoPagamento(TipoPagamento tipoPagamento){
        return listar(Pagamento.BUSCAR_PAGAMENTOS_POR_TIPO_PAGAMENTO, tipoPagamento.getId());
    }
    
    public List<Pagamento> listarPagamentosPorPeriodo(Date dataInicial, Date dataFinal){
        return listar(Pagamento.BUSCAR_PAGAMENTOS_POR_PERIODO, dataInicial, dataFinal);
    }
    
    public List<Pagamento> listarPagamentosPagosPorPeriodo(Date dataInicial, Date dataFinal){
        return listar(Pagamento.BUSCAR_PAGAMENTOS_PAGOS_POR_PERIODO, dataInicial, dataFinal);
    }
    
    public List<Pagamento> listarPagamentosNaoPagosPorPeriodo(Date dataInicial, Date dataFinal){
        return listar(Pagamento.BUSCAR_PAGAMENTOS_NAO_PAGOS_POR_PERIODO, dataInicial, dataFinal);
    }
    
}

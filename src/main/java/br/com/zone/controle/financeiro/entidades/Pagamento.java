package br.com.zone.controle.financeiro.entidades;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author daniel
 */
@Entity
@Table
@NamedQueries({
    @NamedQuery(name = Pagamento.BUSCAR_PAGAMENTOS_POR_USUARIO,
            query = "SELECT p FROM Pagamento p WHERE p.usuario.id = ?1 ORDER BY id"),
    @NamedQuery(name = Pagamento.BUSCAR_PAGAMENTOS_POR_TIPO_PAGAMENTO,
            query = "SELECT p FROM Pagamento p WHERE p.tipoPagamento.id = ?1 ORDER BY id"),
    @NamedQuery(name = Pagamento.BUSCAR_PAGAMENTOS_POR_PERIODO,
            query = "SELECT p FROM Pagamento p WHERE p.dataVencimento BETWEEN ?1 AND ?2 OR p.dataPagamento BETWEEN ?1 AND ?2 ORDER BY id"),
    @NamedQuery(name = Pagamento.BUSCAR_PAGAMENTOS_PAGOS_POR_PERIODO,
            query = "SELECT p FROM Pagamento p WHERE p.dataPagamento BETWEEN ?1 AND ?2 ORDER BY id"),
    @NamedQuery(name = Pagamento.BUSCAR_PAGAMENTOS_NAO_PAGOS_POR_PERIODO,
            query = "SELECT p FROM Pagamento p WHERE p.dataPagamento IS NULL AND p.dataVencimento BETWEEN ?1 AND ?2 ORDER BY id")
})
public class Pagamento implements BaseEntity {

    public static final String BUSCAR_PAGAMENTOS_POR_USUARIO = "Pagamento.buscarPagamentosPorUsuario";
    public static final String BUSCAR_PAGAMENTOS_POR_TIPO_PAGAMENTO = "Pagamento.buscarPagamentosPorTipoPagamento";
    public static final String BUSCAR_PAGAMENTOS_POR_PERIODO = "Pagamento.buscarPagamentosPorPeriodo";
    public static final String BUSCAR_PAGAMENTOS_PAGOS_POR_PERIODO = "Pagamento.buscarPagamentosPagosPorPeriodo";
    public static final String BUSCAR_PAGAMENTOS_NAO_PAGOS_POR_PERIODO = "Pagamento.buscarPagamentosNaoPagosPorPeriodo";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "{pagamento.descricao.vazia}")
    @Size(min = 2, max = 100, message = "{pagamento.descricao.tamanho}")
    @Column(length = 100)
    private String descricao;

    @NotNull(message = "{pagamento.data.vencimento.null}")
    @Column(name = "data_vencimento")
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;

    @Column(name = "data_pagamento")
    @Temporal(TemporalType.DATE)
    private Date dataPagamento;
    
    private double valor;

    @Column(name = "valor_pago")
    private double valorPago;

    @Size(max = 255, message = "{pagamento.observacao.tamanho}")
    @Column(length = 255)
    private String obeservacao;

    @NotNull(message = "{pagamento.tipo.pagamento.null}")
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_pagamento_id", foreignKey = @ForeignKey(name = "fk_tipo_pagamento"), nullable = false)
    private TipoPagamento tipoPagamento;

    @ManyToOne
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_usuario"))
    private Usuario usuario;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public String getObeservacao() {
        return obeservacao;
    }

    public void setObeservacao(String obeservacao) {
        this.obeservacao = obeservacao;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final Pagamento other = (Pagamento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}

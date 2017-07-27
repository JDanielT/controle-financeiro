package br.com.zone.controle.financeiro.managedbeans;

import br.com.zone.controle.financeiro.daos.PagamentoDAO;
import br.com.zone.controle.financeiro.daos.TipoPagamentoDAO;
import br.com.zone.controle.financeiro.entidades.Pagamento;
import br.com.zone.controle.financeiro.entidades.TipoPagamento;
import br.com.zone.controle.financeiro.entidades.Usuario;
import br.com.zone.controle.financeiro.util.DateUtil;
import br.com.zone.controle.financeiro.util.Mensagens;
import br.com.zone.controle.financeiro.util.ReportUtil;
import br.com.zone.controle.financeiro.util.SessionUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

/**
 *
 * @author daniel
 */
@Named(value = "pagamentoBean")
@ViewScoped
public class PagamentoBean extends AbstractBean<Pagamento> {

    private final static String PAGINA_DE_CADASTRO_EDICAO = "pagamento.xhtml?faces-redirect=true";
    private final static String PAGINA_DE_LISTAGEM = "pagamentos.xhtml?faces-redirect=true";

    @Inject
    private PagamentoDAO pagamentoDAO;

    @Inject
    private TipoPagamentoDAO tipoPagamentoDAO;

    private Date dataInicial = DateUtil.getPrimeiroDiaDoMes();
    private Date dataFinal = DateUtil.getUltimoDiaDoMes();
    private String status = "TODOS";

    public PagamentoBean() {
        super(Pagamento.class);
    }
    
    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getTiposPagamentos() {
        List<SelectItem> tiposPagamentos = new ArrayList<>();
        for (TipoPagamento tp : tipoPagamentoDAO.listarTodos()) {
            tiposPagamentos.add(new SelectItem(tp, tp.getDescricao()));
        }
        return tiposPagamentos;
    }

    public void imprimirRelatorio() {

        if (getItens() != null && !getItens().isEmpty()) {

            try {

                ReportUtil reportUtil = new ReportUtil();

                FacesContext faces = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
                HttpServletRequest request = (HttpServletRequest) faces.getExternalContext().getRequest();

                HashMap parametros = new HashMap();
                parametros.put("DATA_INICIAL", dataInicial);
                parametros.put("DATA_FINAL", dataFinal);
                parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

                Connection conexao = reportUtil.getConnection();

                String relatorio = reportUtil.getRelatoriosPath() + "/pagamentos-por-periodo.jasper";

                PrintWriter printWriter = response.getWriter();
                ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
                JasperPrint jasperPrint = JasperFillManager.fillReport(relatorio, parametros, conexao);

                JRHtmlExporter jrHtmlExporter = new JRHtmlExporter();

                response.setContentType("text/html");
                response.setCharacterEncoding("ISO-8859-1");
                request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);

                jrHtmlExporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);
                jrHtmlExporter.setParameter(JRExporterParameter.OUTPUT_WRITER, printWriter);
                jrHtmlExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "ISO-8859-1");
                jrHtmlExporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath() + "/servlets/image?image=");
                jrHtmlExporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                jrHtmlExporter.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
                jrHtmlExporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM, byteOutStream);

                jrHtmlExporter.exportReport();

                FacesContext.getCurrentInstance().responseComplete();
            } catch (JRException | IOException ex) {
                Logger.getLogger(PagamentoBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else{
            Mensagens.mensagemDeAlerta("Não há pagamentos no período informado", null);
        }

    }

    @Override
    public List<Pagamento> getItens() {
        List<Pagamento> pagamentos;
        switch (status) {
            case "NAO_PAGOS":
                pagamentos = pagamentoDAO.listarPagamentosNaoPagosPorPeriodo(dataInicial, dataFinal);
                break;
            case "PAGOS":
                pagamentos = pagamentoDAO.listarPagamentosPagosPorPeriodo(dataInicial, dataFinal);
                break;
            default:
                pagamentos = pagamentoDAO.listarPagamentosPorPeriodo(dataInicial, dataFinal);
                break;
        }
        return pagamentos;
    }

    @Override
    public String salvar() {
        getEntity().setUsuario((Usuario)SessionUtil.getSessionAttribute("usuario"));
        return super.salvar(); 
    }
    
    @Override
    protected PagamentoDAO getDAO() {
        return pagamentoDAO;
    }

    @Override
    public String irParaPaginaListagem() {
        return PagamentoBean.PAGINA_DE_LISTAGEM;
    }

    @Override
    public String irParaPaginaCadastroEdicao() {
        return PagamentoBean.PAGINA_DE_CADASTRO_EDICAO;
    }

}

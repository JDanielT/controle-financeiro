package br.com.zone.controle.financeiro.util;

import br.com.zone.controle.financeiro.daos.EMF;
import java.sql.Connection;
import javax.faces.context.FacesContext;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author Daniel
 * 
 */
public class ReportUtil {

    public FacesContext context;
    public String relatoriosPath;

    public ReportUtil() {
        context = FacesContext.getCurrentInstance();
        relatoriosPath = context.getExternalContext().getRealPath("/WEB-INF/relatorios");
    }

    public String getRelatoriosPath() {
        return relatoriosPath;
    }
    
    public Connection getConnection(){
        SessionImpl sessionImpl = (SessionImpl)EMF.getInstance().createEntityManager().getDelegate();
        return sessionImpl.connection();
    }
    
}

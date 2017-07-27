package br.com.zone.controle.financeiro.util;

import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Daniel
 *
 */
public class SessionUtil {

    public static void destruirSessao() {
        ExternalContext ec = getExternalContext();
        ec.invalidateSession();
    }

    public static Object getSessionAttribute(String attributeName) {
        try {
            ExternalContext ec = getExternalContext();
            if (ec != null) {
                Map attrMap = ec.getSessionMap();
                if (attrMap != null) {
                    return attrMap.get(attributeName);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static ExternalContext getExternalContext() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            if (facesContext == null) {
                return null;
            } else {
                return facesContext.getExternalContext();
            }
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static void setSessionAttribute(String attributeName, Object attributeValue) {
        try {
            ExternalContext ec = getExternalContext();
            if (ec != null) {
                Map attrMap = ec.getSessionMap();
                if (attrMap != null) {
                    attrMap.put(attributeName, attributeValue);
                }
            }
        } catch (Exception e) {
        }
    }
    
}

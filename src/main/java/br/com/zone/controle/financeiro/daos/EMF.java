package br.com.zone.controle.financeiro.daos;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author daniel
 */
public class EMF {

    private static EntityManagerFactory emf;
    private static final String UNIDADE_DE_PERSISTENCIA = "controle-financeiro-pu";
    
    private EMF(){
    }
    
    public static EntityManagerFactory getInstance(){
        if(emf == null){
            emf = Persistence.createEntityManagerFactory(UNIDADE_DE_PERSISTENCIA);
        }
        return emf;
    }
            
}

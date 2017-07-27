package br.com.zone.controle.financeiro.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class DateUtil {

    public static Date getPrimeiroDiaDoMes() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        
        int mes = (cal.get(Calendar.MONDAY) + 1);
        int ano = cal.get(Calendar.YEAR);
            
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse("01/" + mes + "/" + ano);
        } catch (ParseException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Date getUltimoDiaDoMes() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());

        int dia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int mes = (cal.get(Calendar.MONDAY) + 1);
        int ano = cal.get(Calendar.YEAR);
        
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(dia+"/"+mes+"/"+ano);
        } catch (ParseException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String formatar(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Abdessamad
 */
public class FieldsController implements Serializable {

    public static FacesMessage.Severity FATAL_MSG = FacesMessage.SEVERITY_FATAL;
    public static FacesMessage.Severity ERROR_MSG = FacesMessage.SEVERITY_ERROR;
    public static FacesMessage.Severity WARN_MSG = FacesMessage.SEVERITY_WARN;
    public static FacesMessage.Severity INFO_MSG = FacesMessage.SEVERITY_INFO;

    /**
     * Creates a new instance of FieldsController
     */
    public FieldsController() {
    }

    public void msgInfo() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(INFO_MSG, "Information", "Merci de remplir les champs"));
    }

    public void msgWarn() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(WARN_MSG, "Avertissement", "Merci de vérifier les données saisi"));
    }

    public void msgError() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ERROR_MSG, "Erreur", "Vous avez saisi mal inforamtion"));
    }

    public void msgFatal() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FATAL_MSG, "Erreur danger", "Erreur non détecté"));
    }

    public void msgPersonnal(String title, String content, FacesMessage.Severity s) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(s, title, content));
    }

    
    public boolean verifyPattern(int rule, String value) {
        String pattern = null;
        switch (rule) {
            case 1:
                pattern = "[^0-9][\\w àáâæçèéêëìíîïñòóôœùúûüýÿÀÁÂÆÇÈEÊËÌÍÎÏÑÒÓÔŒÙÚÛÜÝŸ()'\"_.:@&=/!?*$µ<>¨£€;,-]+"; //String
                break;
            case 2:
                pattern = "^\\\\d+$"; //numeric
                break;
            case 3:
                pattern = "^[a-zA-Z]([a-zA-Z0-9._-]+)@[a-zA-Z0-9._-]{2,}\\.[a-zA-Z]{2,4}"; //email
                break;
            case 4:
                pattern = "^0(5|6)([-. ]?[0-9]{2}){4}$"; //Phone
                break;
        }
        if (value.matches(pattern)) {
            return true;
        }
        return false;
    }

    
    public boolean verifySize(int min, int max, String value) {
        if (value.length() >= min && value.length() <= max) {
            return true;
        }
        this.msgWarn();
        return false;
    }

    /**
     * Méthode qui vérifier la map des données entrés Writed By Mohsine
     *
     * @param attributes
     * @return
     */
    public Map<? extends String, ? extends String> checkData(HashMap<String, Rule> attributes) {
        Map<String, String> results = new HashMap<String, String>();
        for (Map.Entry<String, Rule> entry : attributes.entrySet()) {
            String name_attr = entry.getKey();
            Rule rule = entry.getValue();
            String value = (String) rule.getAttributeValue();
            if (!this.verifyPattern(rule.getRuleId(), value) || value.equals("")) {
                results.put(name_attr, "has-error");
            }
        }
        return results;
    }

    public Date dateLastWeek(String value) {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i - 7);
        Date startWeek = c.getTime();
        c.add(Calendar.DATE, 6);
        Date endWeek = c.getTime();
        if (value.equals("end")) {
            return endWeek;
        }
        return startWeek;
    }

    public Date dateLastMonth(String value) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        aCalendar.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = aCalendar.getTime();
        if (value.equals("end")) {
            return lastDateOfPreviousMonth;
        }
        return firstDateOfPreviousMonth;
    }
}

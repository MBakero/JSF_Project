/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Abdessamad
 */
public class Rule {

    private Object attributeValue;
    private int ruleId;

    public Rule() {
    }

    public Rule(Object attributeValue, int ruleId) {
        this.attributeValue = attributeValue;
        this.ruleId = ruleId;
    }

    public Object getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(Object attributeValue) {
        this.attributeValue = attributeValue;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }
}

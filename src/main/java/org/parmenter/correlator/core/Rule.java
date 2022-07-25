package org.parmenter.correlator.core;

import com.espertech.esper.common.client.EPCompiled;

import java.util.Objects;
import java.util.UUID;

import static org.parmenter.correlator.core.Util.compileEPL;
import static org.parmenter.correlator.core.Util.getConfiguration;

/**
 * A rule is a wrapper around EPStatement to model the query being run in the CEP engine
 */
public class Rule {

    private String id;
    private String eplString;
    private EPCompiled epCompiled;
    private String ruleName;

    public Rule(String eplString, String ruleName){
        this.id = UUID.randomUUID().toString();
        this.ruleName = ruleName;
        this.eplString = eplString;
        String compileString = String.format("@name('%s') %s", this.ruleName, eplString);
        this.epCompiled = compileEPL(compileString, getConfiguration());

    }

    public String getId(){
        return id;
    }

    public String getRuleName(){return ruleName;}

    public EPCompiled getCompiledEP(){
        return epCompiled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return id.equals(rule.id) && eplString.equals(rule.eplString) && ruleName.equals(rule.ruleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eplString, ruleName);
    }
}

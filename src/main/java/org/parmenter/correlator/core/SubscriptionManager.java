package org.parmenter.correlator.core;

import com.espertech.esper.runtime.client.EPDeployException;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SubscriptionManager {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionManager.class);

    //Must be kept in sync
    private final Map<User,List<Subscription>> subscriptionUserMap;
    private Map<Rule,List<Subscription>>  subscriptionRulesMap;

    public SubscriptionManager(){
        this.subscriptionUserMap = new ConcurrentHashMap<User, List<Subscription>>();
        this.subscriptionRulesMap = new ConcurrentHashMap<Rule, List<Subscription>>();
    }

    public synchronized void addSubscription(Subscription subscription){
        log.info("Adding subscription");
        if(subscriptionUserMap.containsKey(subscription.getUser())){
            List currentSubs = subscriptionUserMap.get(subscription.getUser());
            currentSubs.add(subscription);
        }
        else {
            List newSubList = new ArrayList<Subscription>();
            newSubList.add(subscription);
            subscriptionUserMap.put(subscription.getUser(), newSubList);
        }

        if(subscriptionRulesMap.containsKey(subscription.getRule())){
            List currentSubs = subscriptionRulesMap.get(subscription.getRule());
            currentSubs.add(subscription);
        }
        else {
            List newSubList = new ArrayList<Subscription>();
            newSubList.add(subscription);
            subscriptionRulesMap.put(subscription.getRule(), newSubList);
        }
    }

    public List<Subscription> getSubscriptionsForRule(EPStatement statement){

        List<Subscription> subscriptions = subscriptionRulesMap.get(getRuleForEPStatement(statement));
        return subscriptions;
    }

    private Rule getRuleForEPStatement(EPStatement epstatement){
        for( Rule rule : this.subscriptionRulesMap.keySet()){
//            if( epstatement.equals(rule.getEPStatement())){
//                return rule;
//            }
        }
        return null;
    }

    public void initialiseSubscriptions(Engine engine) {
        log.info("Setting up subscriptions in the manager at startup");
        for (User user : this.subscriptionUserMap.keySet()){
            List<Subscription> subs = this.subscriptionUserMap.get(user);
            for(Subscription sub : subs) {
                EPDeployment deployment;
                try {
                    deployment = engine.getRuntime().getDeploymentService().deploy(sub.getRule().getCompiledEP());
                    EPStatement statement = engine.getRuntime().getDeploymentService().getStatement(
                            deployment.getDeploymentId(),sub.getRule().getRuleName());
                    statement.addListener(sub.getUpdateListener());
                    log.info("Deployed {} for {}", deployment.getDeploymentId(), sub.getUser());
                } catch (EPDeployException ex) {
                    throw new RuntimeException("Deployment failed: " + ex.getMessage(), ex);
                }
            }
        }
    }
}

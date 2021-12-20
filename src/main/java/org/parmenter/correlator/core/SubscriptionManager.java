package org.parmenter.correlator.core;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SubscriptionManager {
    //Must be kept in sync
    private Map<User,List<Subscription>> subscriptionUserMap = new ConcurrentHashMap<User, List<Subscription>>();
    private Map<Rule,List<Subscription>>  subscriptionRulesMap = new ConcurrentHashMap<Rule, List<Subscription>>();

    public SubscriptionManager(){

    }

    public synchronized void addSubscription(Subscription subscription){
        if(subscriptionUserMap.containsKey(subscription.getUser())){
            List currentSubs = subscriptionUserMap.get(subscription.getUser());
            currentSubs.add(subscription);
        }
        else {
            subscriptionUserMap.put(subscription.getUser(), Collections.singletonList(subscription));
        }

        if(subscriptionUserMap.containsKey(subscription.getRule())){
            List currentSubs = subscriptionUserMap.get(subscription.getRule());
            currentSubs.add(subscription);
        }
        else {
            subscriptionUserMap.put(subscription.getUser(), Collections.singletonList(subscription));
        }
    }

    public List<Subscription> getSubscriptionsForRule(Rule rule){
        List<Subscription> subscriptions = subscriptionRulesMap.get(rule);
        return subscriptions;
    }

}

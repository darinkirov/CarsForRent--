package com.carhiring.carhiring.common;

import com.carhiring.carhiring.data.entities.Operator;

public class Context {
    private Operator operator;

    private static Context instance;

    private Context() {}

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Operator getOperator() {
        return this.operator;
    }
}

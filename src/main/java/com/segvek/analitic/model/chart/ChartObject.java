package com.segvek.analitic.model.chart;

public abstract class ChartObject {

    protected String id;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChartObject) {
            return this.id.equals(((ChartObject) obj).id);
        }
        return false;
    }
}

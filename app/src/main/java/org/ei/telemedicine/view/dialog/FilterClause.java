package org.ei.telemedicine.view.dialog;

public interface FilterClause<T> {
    public boolean filter(T object);
}

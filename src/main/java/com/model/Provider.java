package com.model;

import com.vo.Vacancy;
import java.util.List;


public class Provider
{
    private Strategy strategy;

    public Provider(Strategy strategy)
    {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy)
    {
        this.strategy = strategy;
    }

    public List<Vacancy> getJavaVacancies(String searchString)
    {
        return strategy.getVacancies(searchString);
    }
}

package com.model;


import com.view.View;
import com.vo.Vacancy;
import java.util.ArrayList;
import java.util.List;


public class Model
{
    private Provider[] providers;
    private View view;

    public Model(View view, Provider[] providers)
    {
        if (view == null || providers.length == 0) throw new IllegalArgumentException();
        this.providers = providers;
        this.view = view;

    }

    public void selectCity(String city)
    {
        List<Vacancy> vacancies = new ArrayList<>();
        for (Provider p : providers)
        {
            for (Vacancy v : p.getJavaVacancies(city))
            {
                vacancies.add(v);
            }
        }
        view.update(vacancies);
    }

}

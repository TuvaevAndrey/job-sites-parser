package com.view;

import com.vo.Vacancy;
import com.Controller;

import java.util.List;


public interface View {
    void update(List<Vacancy> vacancies);

    void setController(Controller controller);
}

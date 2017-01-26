package com.model;

import com.vo.Vacancy;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;


public interface Strategy {
    List<Vacancy> getVacancies(String searchString);

    default String getVacancyName() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
            return properties.getProperty("vacancy");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

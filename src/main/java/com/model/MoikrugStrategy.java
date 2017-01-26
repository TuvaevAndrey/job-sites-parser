package com.model;

import com.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MoikrugStrategy implements Strategy {
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?page=%d&q=%s+%s";

    protected Document getDocument(String searchString, int page) throws IOException {
        String vacancy = getVacancyName();
        String url = String.format(URL_FORMAT, page,vacancy, searchString);
        Document document = Jsoup.connect(url).userAgent("Mozilla/5.0 (jsoup)").referrer("some text").get();
        return document;
    }

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancies = new ArrayList<>();
        int pageNumber = 1;

        Document doc;
        try {
            while (true) {
                doc = getDocument(searchString, pageNumber++);
                if (doc == null) break;
                Elements elements = doc.getElementsByClass("job");
                if (elements.size() == 0) break;

                for (Element e : elements) {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(e.getElementsByAttributeValue("class", "title").text());
                    vacancy.setSiteName("https://moikrug.ru");
                    vacancy.setCompanyName(e.getElementsByAttributeValue("class", "company_name").text());
                    vacancy.setCity(e.getElementsByAttributeValue("class", "location").text());
                    vacancy.setUrl(e.getElementsByClass("title").select("a").attr("abs:href"));
                    Element salaryElement = e.getElementsByAttributeValue("class", "salary").first();
                    if (salaryElement == null) {
                        vacancy.setSalary("");
                    } else vacancy.setSalary(salaryElement.text());
                    vacancies.add(vacancy);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vacancies;
    }
}

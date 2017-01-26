package com.model;

import com.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=%s+%s&page=%d";
    private Properties properties = new Properties();

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancies = new ArrayList<>();
        int pageNumber = 0;
        Document doc;
        try {
            while (true) {
                doc = getDocument(searchString, pageNumber++);
                if (doc == null) break;
                Elements elements = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                if (elements.size() == 0) break;

                for (Element e : elements) {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(e.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text());
                    vacancy.setUrl(e.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href"));
                    vacancy.setCity(e.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text());
                    vacancy.setCompanyName(e.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                    vacancy.setSiteName("http://hh.ru");
                    Element salaryElement = e.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").first();
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

    protected Document getDocument(String searchString, int page) throws IOException {
        String vacancy = getVacancyName();
        String url = String.format(URL_FORMAT, vacancy, searchString, page);
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
        String referer = "https://hh.ua/search/vacancy?text=java+%D0%BA%D0%B8%D0%B5%D0%B2";
        Document document = Jsoup.connect(url).userAgent("Mozilla/5.0 (jsoup)").referrer("some text").get();
        return document;
    }
}

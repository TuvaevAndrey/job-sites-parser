package com.view;

import com.vo.Vacancy;
import com.Controller;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


public class HtmlView implements View {
    private final String filePath = "./src/main/java/" + this.getClass().getPackage().getName().replaceAll("\\.", "/")
            + "/vacancies.html";
    ;
    private Controller controller;

    @Override
    public void update(List<Vacancy> vacancies) {
        try {
            updateFile(getUpdatedFileContent(vacancies));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Some exception occurred");
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            controller.onCitySelect(properties.getProperty("city"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUpdatedFileContent(List<Vacancy> vacacies) {
        Document doc = null;
        try {
            doc = getDocument();
            Element template = doc.getElementsByClass("template").first();
            Element cloneTemp = template.clone();
            cloneTemp.removeAttr("style");
            cloneTemp.removeClass("template");
            doc.select("tr[class=vacancy]").remove();
            for (Vacancy vacancy : vacacies) {
                Element vac = cloneTemp.clone();
                vac.getElementsByClass("city").get(0).text(vacancy.getCity());
                vac.getElementsByClass("companyName").get(0).text(vacancy.getCompanyName());
                vac.getElementsByClass("salary").get(0).text(vacancy.getSalary());
                Element link = vac.getElementsByTag("a").first();
                link.text(vacancy.getTitle());
                link.attr("href", vacancy.getUrl());
                template.before(vac.outerHtml());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return doc.html();
    }

    private void updateFile(String fileBody) {
        try (FileWriter writer = new FileWriter(new File(filePath))) {
            writer.write(fileBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File(filePath), "UTF-8");
    }
}

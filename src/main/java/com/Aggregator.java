package com;


import com.model.*;
import com.view.HtmlView;


public class Aggregator {
    public static void main(String[] args) {
        HtmlView view = new HtmlView();
        HHStrategy hh = new HHStrategy();
        Model model = new Model(view, new Provider[]{new Provider(hh)});
        Controller controller = new Controller(model);
        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}

package com.misys.stockmarket;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
/**
 * @author Gurudath Reddy
 * @version 1.0
 */
@Controller
public class StockMarketController {
     
    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }
    
    @RequestMapping("/game")
    public String app(Model model) {
        return "app";
    }
}


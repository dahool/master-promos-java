package com.ar.sgt.masterpromos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ar.sgt.masterpromos.model.Promo;

@Component
public class PromoParser {

	private static final String NO_HAY_STOCK_DISPONIBLE = "No hay stock disponible";

	private Pattern PERC_FIND = Pattern.compile("([\\d]+\\%){1}");

	private Pattern DATES_FIND = Pattern.compile("para pagos entre el (\\d{2}\\/\\d{2}\\/\\d{4}){1} al (\\d{2}\\/\\d{2}\\/\\d{4}){1}");
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public List<Promo> parse(String url) throws Exception {
		//return parse(Jsoup.parse(loadFile("page.html")));
		return parse(Jsoup.parse(new URL(url), 30000));
	}
	
	public List<Promo> parse(Document doc) throws Exception {
		List<Promo> promos = new ArrayList<Promo>();
		Elements ePromos = doc.select("div.promotion-content");

		for (Element ePromo : ePromos) {
			Promo promo = new Promo();
			promo.setTitle("PARTE1");
			promo.setText(ePromo.select("div.promotion-body-caption").text());
			promo.setUrl(ePromo.select("a").attr("href"));
			promo.setImage(ePromo.select("div.promotion-header").select("img").attr("src"));
			promo.setPoints(ePromo.select("span.promotion-points").text());
			Matcher m = PERC_FIND.matcher(promo.getText());
			if (m.find()) {
				promo.setPercentage(m.group(0));
			}
			promos.add(promo);
			logger.info("Parsed: {}", promo);
		}
		
		return promos;
	}
	
	public Promo parseDetails(Promo promo) throws Exception {
		Document doc = Jsoup.parse(new URL(promo.getUrl()), 30000);
		//Document doc = Jsoup.parse(loadFile("promodetail.html"));
	
		String title = doc.select("article.promotion header h1 strong").text();
		boolean hasStock = !doc.select("article.promotion section div.alert").text().equalsIgnoreCase(NO_HAY_STOCK_DISPONIBLE);
		String detailText = doc.select("#detalleBeneficio").text();
		String dateFrom = null;
		String dateTo = null; 
		Matcher m = DATES_FIND.matcher(detailText);
		if (m.find()) {
			dateFrom = m.group(1);
			dateTo = m.group(2);
		}
		
		promo.setTitle(title);
		promo.setHasStock(hasStock);
		promo.setDateFrom(dateFrom);
		promo.setDateTo(dateTo);
		
		logger.info("Detailed Promo: {}", promo);
		return promo;
	}

	
	private String loadFile(String name) throws Exception {
		InputStreamReader ior = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(name));
		BufferedReader reader = new BufferedReader(ior);
		String line = null;
		StringBuilder b = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			b.append(line);
		}
		reader.close();
		return b.toString();
	}
	
	public static void main(String[] args) throws Exception {
		/*File f = new File("c:\\tmp\\master\\page.html");
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line = null;
		StringBuilder b = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			b.append(line);
		}
		reader.close();*/
		PromoParser parser = new PromoParser();
		List<Promo> promos = parser.parse(Jsoup.parse(parser.loadFile("page.html")));
		System.out.println(promos);
		for (Promo promo : promos) {
			parser.parseDetails(promo);
			System.out.println(promo);
		}
	}
}

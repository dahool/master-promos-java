package com.ar.sgt.masterpromos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import com.ar.sgt.masterpromos.utils.DateUtils;

@Component
public class PromoParser {

	private static final String NO_HAY_STOCK_DISPONIBLE = "No hay stock disponible";

	private static final String[] USER_AGENTS = {"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:51.0) Gecko/20100101 Firefox/51.0",
												"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36",
												"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36",
												"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:50.0) Gecko/20100101 Firefox/51.0",
												"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:50.0) Gecko/20100101 Firefox/50.0",
												"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:51.0) Gecko/20100101 Firefox/51.0",
												"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.76 Safari/537.36"};
	
	private final static String SIN_DATOS = "Ninguna oferta.";
	
	private static final int TIMEOUT = 60000;
	
	private final Pattern PERC_FIND = Pattern.compile("([\\d]+\\%){1}");

	private final Pattern DATES_FIND = Pattern.compile("para pagos entre el (\\d{2}\\/\\d{2}\\/\\d{4}){1} al (\\d{2}\\/\\d{2}\\/\\d{4}){1}");
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Random random = new Random();
	
	private String getUserAgent() {
		String ua = USER_AGENTS[random.nextInt(USER_AGENTS.length)];
		logger.info("Request using {}", ua);
		return ua;
	}
	
	public List<Promo> parse(String url) throws Exception {
		return parse(Jsoup.connect(url).timeout(TIMEOUT).userAgent(getUserAgent()).get());
	}
	
	public List<Promo> parse(Document doc) throws Exception {
		List<Promo> promos = new ArrayList<Promo>();
		Elements ePromos = doc.select("div.promotion-content");

		if (ePromos.size() == 0) {
			logger.info("Promo list is empty");
			if (doc.select("section.socios").text().contains(SIN_DATOS)) {
				logger.info(SIN_DATOS);
			} else {
				logger.error(doc.html());
				throw new Exception("Page cannot be analized.");
			}
		} else {

			for (Element ePromo : ePromos) {
				Promo promo = new Promo();
				promo.setText(ePromo.select("div.promotion-body-caption").text());
				promo.setUrl(ePromo.select("a").attr("href"));
				promo.setImage(ePromo.select("div.promotion-header").select("img").attr("src"));
				promo.setPoints(ePromo.select("span.promotion-points").text());
				promo.setCreated(DateUtils.getCurrent());
				Matcher m = PERC_FIND.matcher(promo.getText());
				if (m.find()) {
					promo.setPercentage(m.group(0));
				}
				if (promo.getPercentage() != null && promo.getPoints() != null) {
					promos.add(promo);
				}
				logger.info("Parsed: {}", promo);
			}

		}
		
		return promos;
	}
	
	public Promo parseDetails(Promo promo) throws Exception {
		Document doc = Jsoup.connect(promo.getUrl()).timeout(TIMEOUT).userAgent(getUserAgent()).get();
	
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
		//System.setProperty("javax.net.debug", "all");
		//List<Promo> promos = parser.parse("https://sorpresas.mastercard.com/ar");
		List<Promo> promos = parser.parse(Jsoup.parse(parser.loadFile("main.html")));
		System.out.println(promos);
		for (Promo promo : promos) {
			//parser.parseDetails(promo);
			System.out.println(promo);
		}
	}
}

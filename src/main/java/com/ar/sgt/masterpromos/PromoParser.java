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

	private Pattern PERC_FIND = Pattern.compile("([\\d]+\\%){1}");

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public List<Promo> parse(String url) throws Exception {
		return parse(Jsoup.parse(new URL(url), 60000));
	}
	
	public List<Promo> parse(Document doc) throws Exception {
		List<Promo> promos = new ArrayList<Promo>();
		
		// BORRAR PROXY
		/*System.setProperty("https.proxyHost", "pxyinternet");
		System.setProperty("https.proxyPort", "80");
		System.setProperty("https.proxyUser", "12072245");
		System.setProperty("https.proxyPassword", "nativa40+");
		System.setProperty("javax.net.debug", "all");*/
		
		//Document doc = Jsoup.parse(loadFile());
		
		Elements ePromos = doc.select("div.promotion-content");

		for (Element ePromo : ePromos) {
			Promo promo = new Promo();
			promo.setText(ePromo.select("div.promotion-body-caption").text());
			promo.setUrl(ePromo.select("a").attr("href"));
			promo.setImage(ePromo.select("div.promotion-header").select("img").attr("src"));
			promo.setPoints(ePromo.select("span.promotion-points").text());
			Matcher m = PERC_FIND.matcher(promo.getText());
			if (m.find()) {
				promo.setPercentage(m.group(0));
			}
			promos.add(promo);
			System.out.println(promo);
			logger.debug("Parsed {}", promo);
		}
		
		return promos;
	}
	
	private String loadFile() throws Exception {
		InputStreamReader ior = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("page.html"));
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
		/*PromoParser parser = new PromoParser();
		parser.parse("https://sorpresas.mastercard.com/ar/beneficios/main/index/1/10/op/key/descuentos_acumulables");*/
		
	}
}

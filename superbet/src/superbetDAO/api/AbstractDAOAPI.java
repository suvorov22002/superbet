package superbetDAO.api;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.stream.JsonGenerationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.util.NotImplementedException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import modele.AdminTicketDto;
import modele.Airtime;
import modele.BetTicketK;
import modele.BonusSet;
import modele.CagnotteDto;
import modele.Caissier;
import modele.CaissierDto;
import modele.GameCycle;
import modele.GameCycleDto;
import modele.Keno;
import modele.KenoRes;
import modele.Misek;
import modele.MisekDto;
import modele.Partner;
import modele.PartnerDto;
import modele.ResPartner;
import modele.ShiftDay;
import modele.Versement;
import modele.VersementDto;
import superbetDAO.api.exeception.DAOAPIException;

public abstract class AbstractDAOAPI<T> {
	
	final Class<T> typeParameterClass;
	ObjectMapper mapper;
	JsonStringEncoder jsonEncoder;
	public static final int TIMEOUT = 120;//seconds
	
	public AbstractDAOAPI(Class<T> type) {
		this.typeParameterClass = type;
	//	this.target = target;
		this.mapper = new ObjectMapper();
		this.mapper.setSerializationInclusion(Include.NON_NULL);
		this.jsonEncoder = JsonStringEncoder.getInstance();
	}
	
	public T get(int id) {
		throw new NotImplementedException();
	}
	
	public String get(Map<String , String> filtre,String host, String protocole, String port, String url, boolean secure) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		URIBuilder builder = new URIBuilder(url);

		if(secure) {
		//	builder.setParameter("password", "scoring");
			//builder.setParameter("login", "passwordafb");
		}
		
		if(filtre != null) {
			for(String key : filtre.keySet()) {
				builder.addParameter(key, filtre.get(key));
			}
		}
		
		builder.build();
		
		//////System.out.println("request url " +builder.build());

		HttpGet getRequest = new HttpGet(builder.build());
		getRequest.setHeader("Content-type", "application/json");
	//	getRequest.addHeader("Authorization", Parameters.authentication());
		
		HttpResponse httpResponse = this.getHttpClient().execute(getTarget(host, protocole, port), getRequest);
		this.checkStatusResponse(httpResponse);
		String results =null;
		
		
		HttpEntity entity = httpResponse.getEntity();
		
		if (entity != null) {
			String retSrc = EntityUtils.toString(entity);		
			
				//retSrc = new JSONArray().toString();
				JSONObject jsonObject = new JSONObject(retSrc);
				
				//////System.out.println("resultString: "+retSrc);
				results = jsonObject.getString("response");
		}
		
		return results;
	}
	
	public HttpHost getTarget(String host, String protocole, String port) {
		// specify the host, protocol, and port
		if(StringUtils.isNotBlank(host) && StringUtils.isNotBlank(port) && StringUtils.isNotBlank(protocole))
			return new HttpHost(host, Integer.parseInt(port), protocole);
		else
			return null;
	}

	public List<T> filter(Map<String , String> filtre ,String host, String protocole, String port, String url, boolean secure) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
		
		URIBuilder builder = new URIBuilder(url);

		if(secure) {
		//	builder.setParameter("password", "scoring");
			//builder.setParameter("login", "passwordafb");
		}
		
		if(filtre != null) {
			for(String key : filtre.keySet()) {
				builder.addParameter(key, filtre.get(key));
			}
		}
		
		builder.build();
		
		//////System.out.println("request url " +builder.build());

		HttpGet getRequest = new HttpGet(builder.build());
		getRequest.setHeader("Content-type", "application/json");
	//	getRequest.addHeader("Authorization", Parameters.authentication());
		
		HttpResponse httpResponse = this.getHttpClient().execute(getTarget(host, protocole, port), getRequest);
		this.checkStatusResponse(httpResponse);
		List<T> results = new ArrayList<>();
		
		
		HttpEntity entity = httpResponse.getEntity();
		
		if (entity != null) {
			String retSrc = EntityUtils.toString(entity);				
			if(retSrc.length() == 0) {
				retSrc = new JSONArray().toString();
			}
			//////System.out.println("resultString: "+retSrc);
			if(retSrc.substring(0, 1).equalsIgnoreCase("{")){
				retSrc = "["+retSrc+"]";
			}
			
			// CONVERT RESPONSE STRING TO JSON ARRAY
			JSONArray ja = new JSONArray(retSrc);
			int n = ja.length();
			for(int i=0 ; i< n ; i++) {
				JSONObject jo = ja.getJSONObject(i);
				results.add(this.mapToObject(jo));
			}
		}
		
		return results;
	}

	public HttpClient getHttpClient() {
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(TIMEOUT*1000)
				.setConnectionRequestTimeout(TIMEOUT*1000)
				.setSocketTimeout(TIMEOUT*1000)
				.build();
		//////System.out.println("Connection Timeout , " + requestConfig.getConnectionRequestTimeout());
		HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		return client;
	}
	
	public CloseableHttpClient getClosableHttpClient() {
		
		RequestConfig config = RequestConfig.custom()
		  .setConnectTimeout(TIMEOUT * 20000)
		  .setConnectionRequestTimeout(TIMEOUT * 20000)
		  .setSocketTimeout(TIMEOUT * 20000).build();
		CloseableHttpClient client = 
		  HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		
		return client;
	}
	
//	public HttpHost getTarget() {
//		return this.target;
//	}
	
	public T mapToObject(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		StringReader reader = new StringReader(obj.toString());
		T o = this.mapper.readValue(reader, this.typeParameterClass);
		//////System.out.println("returned object " +  o);
		return o;
	}
	
	public CaissierDto mapToCaissierDto_(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		StringReader reader = new StringReader(obj.toString());
		CaissierDto o = this.mapper.readValue(reader, CaissierDto.class);
		//////System.out.println("returned object " +  o);
		return o;
	}
	
	public VersementDto mapToVersementDto(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		StringReader reader = new StringReader(obj.toString());
		VersementDto o = this.mapper.readValue(reader, VersementDto.class);
		return o;
	}
	
	public GameCycleDto mapToGameCycleDto(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		StringReader reader = new StringReader(obj.toString());
		GameCycleDto o = this.mapper.readValue(reader, GameCycleDto.class);
		return o;
	}
	
	public MisekDto mapToMisekDto_(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		StringReader reader = new StringReader(obj.toString());
		MisekDto o = this.mapper.readValue(reader, MisekDto.class);
		//////System.out.println("returned object " +  o);
		return o;
	}
	
	public AdminTicketDto mapToAdminTicketDto(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		StringReader reader = new StringReader(obj.toString());
		AdminTicketDto o = this.mapper.readValue(reader, AdminTicketDto.class);
		return o;
	}
	
	public Airtime mapToAdminAirtime(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		StringReader reader = new StringReader(obj.toString());
		Airtime o = this.mapper.readValue(reader, Airtime.class);
		return o;
	}
	
	public String mapToJsonStrings(T obj) throws JsonGenerationException, JsonMappingException, IOException, JSONException {
		String map = this.mapper.writeValueAsString(obj);
		String encoded = this.encodeJson(map);
		//////System.out.println("encoded "+ encoded);
		return map;
	}
	
	private String encodeJson(String jsonString) {
		return new String(jsonEncoder.quoteAsString(jsonString));
	}
	
	protected void checkStatusResponse(HttpResponse response) throws DAOAPIException {
		int status = response.getStatusLine().getStatusCode();
		
		if(status != 200 && status != 201 && status != 202) {
			throw new DAOAPIException(" HTTP : "+status + " result "+response.getStatusLine().getReasonPhrase());
		}
	}
	
	public String getListAllUrl() {
		return this.getUrl()+"/list-all";
	}
	
	public abstract String getUrl();

	public BetTicketK sendPostSlip(String url, BetTicketK slip, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
		
		String playload = null;
		String resp_code;
		
		playload = mapToJsonString(slip);
		if(!isJSONValid(playload)) {
			return null;
		}
		
        HttpPost post = new HttpPost(url+"/placeslip-keno/"+coderace);

        // add request parameter, form parameters
        post.setHeader("content-type", "application/json");
		post.setEntity(new StringEntity(playload));

        try (CloseableHttpResponse response = this.getClosableHttpClient().execute(post)) {
        	BetTicketK bet = new BetTicketK();
        	try{
        		HttpEntity entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	//	////System.out.println("content: "+content);
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	         //       ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                JSONObject btkObject = j.getJSONObject("btick");
	                bet = this.mapToBetTicket(btkObject);
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		return null;
        	}
            
            return bet;
        }

    }
	
	public BetTicketK verifyTicket(String url, String coderace,  String barcode) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/findticket/"+coderace+"/"+barcode)
				;
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			BetTicketK betk = new BetTicketK();
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
//	        	    ////System.out.println("user-content: "+content);
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	                //System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	               
	                //System.out.println("CODE: "+code);
//	                if("503".equalsIgnoreCase(code) || "030".equalsIgnoreCase(code)  
//	                		|| "032".equalsIgnoreCase(code) || "035".equalsIgnoreCase(code)) {
	                if("503".equalsIgnoreCase(code) || "031".equalsIgnoreCase(code)) {
	                	betk.setMessage(j.getString("message") );
	                	return betk;
                    }
	                
	                
	                JSONObject betkObject = j.getJSONObject("btick");
	                //System.out.println("RESULTAT: "+betkObject);
	                betk = this.mapToBetTicket(betkObject);
	                betk.setMessage(j.getString("message"));
	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		return null;
        	}
        	

			return betk;
        	
		}
	}
	
	public Versement registerVersement(String url, String barcode,  Long caissier, double montant) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/versement/"+barcode+"/"+montant+"/"+caissier);
				
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			Versement vers = new Versement();
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	//    System.out.println("user-content: "+content);
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	           //     ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	           //     ////System.out.println("CODE: "+code);
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                JSONObject betkObject = j.getJSONObject("vers");
	                vers = this.mapToVersement(betkObject);
	               // vers.setMessage(j.getString("message"));
	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		return null;
        	}
        	

			return vers;
        	
		}
	}
	
	public KenoRes retrieveDrawCombi(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		String resp_code;
		//System.out.println("url: "+url+"/drawcombi/"+coderace);
		HttpGet getRequest = new HttpGet(url+"/drawcombi/"+coderace);
				
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			KenoRes kers = new KenoRes();
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	   // //System.out.println("user-content: "+content);
	                JSONObject json = new JSONObject(content);
	                
	                if (!json.has("entity")) {
	                	return null;
	                }
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	             //   ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	         //       ////System.out.println("CODE: "+code);
	                if(!code.equalsIgnoreCase("200")) {
	                	return kers;
	                }
	                JSONObject betkObject = j.getJSONObject("kres");
	           //     ////System.out.println("betkObject "+betkObject);
	                kers = this.mapToKenoRes(betkObject);
	               // vers.setMessage(j.getString("message"));
	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		return null;
        	}
        	
			return kers;
		}
	}
	
	public ShiftDay getShift(String url, Long id, String debut, String fin) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException{
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/shift/"+id+"/"+debut+"/"+fin);
				
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			ShiftDay shiftday = new ShiftDay();
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        //	    ////System.out.println("user-content: "+content);
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	                ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	           //     ////System.out.println("CODE: "+code);
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                JSONObject betkObject = j.getJSONObject("shift");
	                shiftday = this.mapToShiftDay(betkObject);
	               // vers.setMessage(j.getString("message"));
	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		return null;
        	}
        	

			return shiftday;
		}
	}
	
	public int archive(String url, GameCycleDto gmt, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
		
		    String playload = null;
			String resp_code;
			HttpPost post;
			
			playload = mapToJsonString(gmt);
			if(!isJSONValid(playload)) {
				return 0;
			}

	       post = new HttpPost(url+"/ugamecycle/"+coderace);

	      // add request parameter, form parameters
	        post.setHeader("content-type", "application/json");
			post.setEntity(new StringEntity(playload));
		
	      try (CloseableHttpResponse response = this.getClosableHttpClient().execute(post)) {
	    	int c = 0;
	      	try{
	      		HttpEntity entity = response.getEntity();
	      		
	      		if (entity != null) {
	      			
		        		String content = EntityUtils.toString(entity);
		        		////System.out.println("content: "+content);
		                JSONObject json = new JSONObject(content);
		                resp_code = json.getString("entity");
		           //     ////System.out.println("resp_code "+resp_code);
		                JSONObject j = new JSONObject(resp_code);
		                
		                String code = j.getString("code");
		                if(!code.equalsIgnoreCase("200")) {
		                	return 0;
		                }
		                
		                String retSrc = j.getString("data");
		        	   
	                   JSONObject jsonObj = new JSONObject(retSrc.toString());
		                if (jsonObj.has("nbre")) {
		                	c = jsonObj.getInt("nbre");
		                }
		                else {
		                	return 0;
		                }
		                
	  			}
	      	}
	      	catch(Exception e) {
	      		e.printStackTrace();
	      		return 0;
	      	}
	          
	          return c;
	      }
	}
	
	public boolean gamecycles(String url, GameCycleDto gmt, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
		String playload = null;
		String resp_code;
		HttpPost post;
		boolean resp = false;
		
		playload = mapToJsonString(gmt);
		if(!isJSONValid(playload)) {
			return resp;
		}
		
       post = new HttpPost(url+"/gamecycle/"+coderace);

      // add request parameter, form parameters
        post.setHeader("content-type", "application/json");
		post.setEntity(new StringEntity(playload));
	
      try (CloseableHttpResponse response = this.getClosableHttpClient().execute(post)) {
    	int c = 0;
      	try{
      		HttpEntity entity = response.getEntity();
      		
      		if (entity != null) {
      			
	        		String content = EntityUtils.toString(entity);
	        		////System.out.println("content post cycle: "+content);
	                JSONObject json = new JSONObject(content);
	                resp_code = json.getString("entity");
	           //     ////System.out.println("resp_code "+resp_code);
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return resp;
	                }
	                String retSrc = j.getString("data");
		        	   
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                if (jsonObj.has("resp")) {
	                	resp = jsonObj.getBoolean("resp");
	                }
	                else {
	                	return resp;
	                }
	                
	                
	                
  			}
      	}
      	catch(Exception e) {
      		e.printStackTrace();
      		return resp;
      	}
          
          return resp;
      }
	}
	
	public boolean sendPostEOD(String url, List<String> bkeve) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
		
		String _json = new Gson().toJson(bkeve);
		
		String playload = mapToJsonString(bkeve);
	//	////System.out.println("\n"+_json);
		if(!isValidJson(playload)) {
			return false;
		}
        HttpPost post = new HttpPost(url+"/EODevents");

        // add request parameter, form parameters
        post.setHeader("content-type", "application/json");
		post.setEntity(new StringEntity(_json));
	
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(post)) {
        	
        	try{
        		HttpEntity entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	//	//System.out.println(content); 
	        			
	                JSONObject json = new JSONObject(content);
	                playload = json.getString("code");
	    	    	//////System.out.println("CODE REPONSE: "+playload);
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
        	
        	return playload.equalsIgnoreCase("200");
      }
    }
	
	public BetTicketK _sendPostReverseEvent(String url, String bkeve) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
		
		String resp_code;
		//////System.out.println("EXECUTE REVERSE: "+url+"/reverseevent/"+bkeve);
        HttpPost post = new HttpPost(url+"/reverseevent/"+bkeve);
      
        post.setHeader("content-type", "application/json");

//		////System.out.println("URI: "+post.getURI());
			 try (CloseableHttpResponse response = this.getClosableHttpClient().execute(post)) {
				 BetTicketK eve = new BetTicketK();
		        	try{
		        		HttpEntity entity = response.getEntity();
		        		
		        		if (entity != null) {
		        			
			        		String content = EntityUtils.toString(entity);
			        //		//System.out.println(content); 
			                JSONObject json = new JSONObject(content);
			                
			                //Verification du code reponse
			                resp_code = json.getString("code");
			         //       //System.out.println(resp_code); 
			                if(!resp_code.equalsIgnoreCase("200")) {
			                	return null;
			                }
			        		
			                JSONObject eveObject = json.getJSONObject("eve");
			                JSONArray eveArray = new JSONArray();
			                eveArray.put(eveObject);
			  
			    	    	eve = new BetTicketK();
		    				// CONVERT  JSON ARRAY to object bkeve
		    			
		    				int n = eveArray.length();
		    				for(int i=0 ; i< n ; i++) {
		    					JSONObject jo = eveArray.getJSONObject(i);
		    					eve = this.mapToBetTicket(jo);
		    				}
		    				
		    		//		////System.out.println("EVE LIST: "+eve);
		    			}
		        	}
		        	catch(Exception e) {
		        		e.printStackTrace();
		        		return null;
		        	}
		        	
		        	return eve;
        }
    }
	
	public Boolean getPartner(String url, String coderace) throws ClientProtocolException, IOException {
		
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/partner/"+coderace);
				
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			
			Partner partnerDto = new Partner();
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	                JSONObject json = new JSONObject(content);
	                //System.out.println(content); 
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	                //System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	          
	                if(!code.equalsIgnoreCase("200")) {
	                	return Boolean.FALSE;
	                }
	          		        	   
	                if (j.has("part")) {
	                	
	 	                partnerDto = this.mapToPartner(j.getJSONObject("part"));
	 	               if(coderace.equals(partnerDto.getCoderace())) {
		                	return Boolean.TRUE;
		                }
	                } 
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		return Boolean.FALSE;
        	}
        	
			return Boolean.FALSE;
		}
	}
	
	public CagnotteDto creerCagnot(String url, CagnotteDto cagnotte, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {

		CagnotteDto p = null;
		String playload = null;
		String resp_code;
		HttpPost post;
		playload = mapToJsonString(cagnotte);
		if(!isJSONValid(playload)) {
			return null;
		}
		
        post = new HttpPost(url+"/cagnotte/"+coderace);

      // add request parameter, form parameters
        post.setHeader("content-type", "application/json");
		post.setEntity(new StringEntity(playload));
	
       try (CloseableHttpResponse response = this.getClosableHttpClient().execute(post)) {
    	
    	  try{
      		HttpEntity entity = response.getEntity();
      		
      		if (entity != null) {
      			
	        		String content = EntityUtils.toString(entity);
	        		////System.out.println("cagnotte: "+content);
	                JSONObject json = new JSONObject(content);
	                resp_code = json.getString("entity");
	         
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                String retSrc = j.getString("cagnot");
		        	   
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if (jsonObj.has("cagnot")) {
	                    this.mapToCagnotte(jsonObj.getJSONObject("cagnot"));
	                }
	                else {
	                	return null;
	                }
	               
  			}
      	}
      	catch(Exception e) {
      		e.printStackTrace();
      		return null;
      	}
          
          return p;
      }
	}
	
	public ResPartner partner(String url, PartnerDto part) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
		
		ResPartner resp = null;
		Partner pdto = null;
		String playload = null;
		String resp_code;
		HttpPost post;
		playload = mapToJsonString(part);
		if(!isJSONValid(playload)) {
			return null;
		}

       post = new HttpPost(url+"/partners");

      // add request parameter, form parameters
        post.setHeader("content-type", "application/json");
		post.setEntity(new StringEntity(playload));
	
      try (CloseableHttpResponse response = this.getClosableHttpClient().execute(post)) {
    	
    	  try{
      		HttpEntity entity = response.getEntity();
      		
      		if (entity != null) {
      			
	        		String content = EntityUtils.toString(entity);
	        		//System.out.println("content: "+content);
	                JSONObject json = new JSONObject(content);
	                resp_code = json.getString("entity");
	         
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                
	                resp = new ResPartner();
	                String err = j.getString("error");
	                if("EXISTS".equalsIgnoreCase(err)) {
	                	resp.setMessage("EXISTS");
	                }
	                else {
	                	resp.setMessage("NEW");
	                }
	                
	                if (j.has("part")) {
	                	////System.out.println("j: "+j.getJSONObject("part"));
	                	pdto = this.mapToPartner(j.getJSONObject("part"));
	                	resp.setPartner(pdto);
	                }
	                else {
	                	return null;
	                }
	                
	                
	                
  			}
      	}
      	catch(Exception e) {
      		e.printStackTrace();
      		return null;
      	}
    	  
          return resp;
      }
	}
	public Caissier getUser(Caissier caissier, String url, String partner) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
		
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/finduser/"+partner+"/"+caissier.getLoginc()+"/"+caissier.getMdpc());
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			Caissier cais = new Caissier();
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");

	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                JSONObject caisObject = j.getJSONObject("cais");
	                cais = this.mapToCaissier(caisObject);
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		return null;
        	}
        	

			return cais;
        	//return eve;
		}
    }
	
	public Caissier getUserAdmin(Caissier caissier, String url) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
		
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/finduser-admin/"+caissier.getLoginc()+"/"+caissier.getMdpc());
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			
			Caissier cais = new Caissier();
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	                JSONObject j = new JSONObject(resp_code);
	                String code = j.getString("code");

	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                JSONObject caisObject = j.getJSONObject("cais");
	                cais = this.mapToCaissier(caisObject);
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		return null;
        	}
        	

			return cais;
        	//return eve;
		}
    }
	
	public Double pushAirtime(String url, String coderace, String login, double credit) throws ClientProtocolException, IOException, 
		JSONException, URISyntaxException, DAOAPIException {
		
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/airtime/"+coderace+"/"+login+"/"+credit);
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	Double solde = 0d;
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        		////System.out.println(content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	                //System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return 0d;
	                }
	                
	                
	                String retSrc = j.getString("data");
	               // ////System.out.println("Push Airtime: "+retSrc);
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("credit")) {
	                	solde = jsonObj.getDouble("credit");
	                }
	                else {
	                	solde = 0d;
	                }
	               
	         //       ////System.out.println("Solde: "+solde);
	                	           
    			}
        	}
        	catch(Exception e) {
       // 	catch(NumberFormatException | NullPointerException | JSONException e) {
        		//System.err.println(e.getMessage());
        		e.printStackTrace();
        		solde = 0d;
        		return solde;
        	}
        	
			return solde;
	   }
	}
	
	public List<Misek> statsMisek(String url, long t1, long t2, String coderace) throws ClientProtocolException, IOException, 
	JSONException, URISyntaxException, DAOAPIException {
		List<Misek> misek = null;
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/stat-misek/"+coderace+"/"+t1+"/"+t2);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        		//////System.out.println("result "+content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	               // ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                
	                String retSrc = j.getString("data");
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("statK")) {
	                	String list_json = jsonObj.getString("statK");
	                	//////System.out.println("statK "+list_json);
	                	JSONArray jObj = new JSONArray(list_json.toString());
	                	List<Misek> object = new ArrayList<>();
	                	
	        			int n = jObj.length();
	        			for(int i=0 ; i< n ; i++) {
	        				JSONObject jo = jObj.getJSONObject(i);
	        				//////System.out.println("MISEK "+jo);
	        				object.add(this.mapToMisekDto_(jo));
	        			}
	                	
		                misek = new ArrayList<>();
		                misek.addAll(object);
	                }
	                else {
	                	return null;
	                }    	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		
        		return null;
        	}
        	
			return misek;
		}
	}
	
	public List<Versement> versement(String url, long t1, long t2, String coderace) throws ClientProtocolException, IOException, 
		JSONException, URISyntaxException, DAOAPIException {

		List<Versement> lvers = null;
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/versements/"+coderace+"/"+t1+"/"+t2);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	//	////System.out.println("result "+content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	      //          ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                 
	                String retSrc = j.getString("data");
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("vers")) {
	                	String list_json = jsonObj.getString("vers");
	                	////System.out.println("vers "+list_json);
	                	JSONArray jObj = new JSONArray(list_json.toString());
	                	List<VersementDto> object = new ArrayList<>();
	                	
	                	// CONVERT RESPONSE STRING TO JSON ARRAY
	                	lvers = new ArrayList<>();
	        			int n = jObj.length();
	        			for(int i=0 ; i< n ; i++) {
	        				JSONObject jo = jObj.getJSONObject(i);
	        				lvers.add(this.mapToVersementDto(jo));
	        			}
	                }
	                else {
	                	return null;
	                }    	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		return null;
        	}
        	
			return lvers;
		}
		
	}
	
	public List<Caissier> turnover(String url, String coderace) throws ClientProtocolException, IOException, 
	JSONException, URISyntaxException, DAOAPIException {
		List<Caissier> lcais = null;
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/turnover/"+coderace);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	//	////System.out.println("result "+content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	      //          ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                
	                String retSrc = j.getString("data");
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("turnover")) {
	                	String list_json = jsonObj.getString("turnover");
	                	////System.out.println("turnover "+list_json);
	                	JSONArray jObj = new JSONArray(list_json.toString());
	                	List<CaissierDto> object = new ArrayList<>();
	                	
	                	// CONVERT RESPONSE STRING TO JSON ARRAY
	                	
	        			int n = jObj.length();
	        			for(int i=0 ; i< n ; i++) {
	        				JSONObject jo = jObj.getJSONObject(i);
	        				object.add(this.mapToCaissierDto_(jo));
	        			}
	                	
	        			lcais = new ArrayList<>();
		                Caissier cais = new Caissier();
		                for (CaissierDto m : object) {
		                	cais.setAirtime(m.getAirtime());
		                	cais.setGrpe(m.getGrpe());
		                	cais.setIdCaissier(m.getIdCaissier());
		                	cais.setLoginc(m.getLoginc());
		                	cais.setNomc(m.getNomc());
		                	cais.setPartner(m.getPartner());
		                	cais.setProfil(m.getProfil());
		                	cais.setStatut(m.getStatut());
		                	lcais.add(cais);
		                }
	                }
	                else {
	                	return null;
	                }    	           
    			}
        	}
        	catch(Exception e) {
       // 	catch(NumberFormatException | NullPointerException | JSONException e) {
        		//System.err.println(e.getMessage());
        		e.printStackTrace();
        		
        		return null;
        	}
        	
			return lcais;
		}
	}
	
	public List<GameCycle> gamecyle(String url, String coderace) throws ClientProtocolException, IOException, 
				JSONException, URISyntaxException, DAOAPIException {
		List<GameCycle> lgame = null;
		String resp_code;
		//System.out.println("result gamecycle "+url+"/gamecycle/"+coderace);
		HttpGet getRequest = new HttpGet(url+"/gamecycle/"+coderace);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	  //System.out.println("result gamecycle "+content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	      //          ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                
	                String retSrc = j.getString("data");
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("cycle")) {
	                	String list_json = jsonObj.getString("cycle");
	                	////System.out.println("cycle "+list_json);
	                	JSONArray jObj = new JSONArray(list_json.toString());
	                	List<GameCycle> object = new ArrayList<>();
	                	
	                	// CONVERT RESPONSE STRING TO JSON ARRAY
	                	
	        			int n = jObj.length();
	        			for(int i=0 ; i< n ; i++) {
	        				JSONObject jo = jObj.getJSONObject(i);
	        				object.add(this.mapToGameCycleDto(jo));
	        			}
	                	
	        			lgame = new ArrayList<>();
	        			lgame.addAll(object);
//	        			GameCycle cycle = new GameCycle();
//		                for (GameCycleDto m : object) {
//		                	
//		                	lgame.add(cycle);
//		                }
	                }
	                else {
	                	return null;
	                }    	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		
        		return null;
        	}
        	
			return lgame;
		}
	}
	
	public double miseRK(String url, Long dat1, Long dat2, String coderace, String caissier) throws ClientProtocolException, IOException, 
		JSONException, URISyntaxException, DAOAPIException {
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/miserk/"+coderace+"/"+caissier+"/"+dat1+"/"+dat2);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	double max = 0l;
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	//	//System.out.println(content);
	        		if(!isValidJson(content)) {
	        			return 0;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");       
	                JSONObject j = new JSONObject(resp_code);
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return 0l;
	                }
	                
	                String retSrc = j.getString("data");
	                ////System.out.println(retSrc);
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("miserk")) {
	                	max = jsonObj.getDouble("miserk");
	                }
	                else {
	                	max = 0;
	                }        	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		return 0;
        	}
        	
			return max;
		}
		
	}
	
	public double versements(String url, Long dat1, Long dat2, String coderace, String caissier) throws ClientProtocolException, IOException, 
	JSONException, URISyntaxException, DAOAPIException {
	String resp_code;
	HttpGet getRequest = new HttpGet(url+"/versemens/"+coderace+"/"+caissier+"/"+dat1+"/"+dat2);
	// add request parameter, form parameters
	getRequest.setHeader("content-type", "application/json");
	
	try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
    	double max = 0l;
    	HttpEntity entity = null;
    	
    	try{
    		entity = response.getEntity();
    		
    		if (entity != null) {
    			
        		String content = EntityUtils.toString(entity);
        	//	//System.out.println(content);
        		if(!isValidJson(content)) {
        			return 0;
        		}
                JSONObject json = new JSONObject(content);
                
                //Verification du code reponse
                resp_code = json.getString("entity");       
                JSONObject j = new JSONObject(resp_code);
                String code = j.getString("code");
                if(!code.equalsIgnoreCase("200")) {
                	return 0l;
                }
                
                String retSrc = j.getString("data");
                ////System.out.println(retSrc);
                JSONObject jsonObj = new JSONObject(retSrc.toString());
                
                if(jsonObj.has("vers")) {
                	max = jsonObj.getDouble("vers");
                }
                else {
                	max = 0;
                }        	           
			}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return 0;
    	}
    	
		return max;
	}
	
}

	
	public double cumulCredit(String url, String dat1, String dat2, String coderace, String caissier) throws ClientProtocolException, IOException, 
			JSONException, URISyntaxException, DAOAPIException {

		
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/airtimes/"+coderace+"/"+caissier+"/"+dat1+"/"+dat2);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	double max = 0l;
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        		////System.out.println(content);
	        		if(!isValidJson(content)) {
	        			return 0;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");       
	                JSONObject j = new JSONObject(resp_code);
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return 0l;
	                }
	                
	                String retSrc = j.getString("data");
	                //System.out.println(retSrc);
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("cumul")) {
	                	max = jsonObj.getDouble("cumul");
	                }
	                else {
	                	max = 0;
	                }        	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		return 0;
        	}
        	
			return max;
		}
		
	}
	
	
	public Long getMaxMisek(String url, String coderace) throws ClientProtocolException, IOException, 
		JSONException, URISyntaxException, DAOAPIException {
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/findmaxMisek/"+coderace);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	Long max = 0l;
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	//	//System.out.println(content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	      //          ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return 0l;
	                }
	                
	                String retSrc = j.getString("data");
	    //            //System.out.println(retSrc);
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("max")) {
	                	max = jsonObj.getLong("max");
	                }
	                else {
	                	max = 0l;
	                }        	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		max = 0l;
        		return max;
        	}
        	
			return max;
		}
	}
	
	public Map<String, Double> miseKCycle(String url, String coderace, long mise, long l) throws ClientProtocolException, IOException, 
		JSONException, URISyntaxException, DAOAPIException {
		
		String resp_code;
		Map<String, Double> respMap = null;
		
		HttpGet getRequest = new HttpGet(url+"/totalMisek/"+coderace+"/"+mise+"/"+l);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	double max = 0l;
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	//	//System.out.println(content);
	        		if(!isValidJson(content)) {
	        			return respMap;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");       
	                JSONObject j = new JSONObject(resp_code);
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return respMap;
	                }
	                
	                String retSrc = j.getString("data");
	    //            //System.out.println(retSrc);
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("sumMise") && jsonObj.has("sumWin")) {
	                	respMap = new HashMap<>();
	                	respMap.put("sumMise", jsonObj.getDouble("sumMise"));
	                	respMap.put("sumWin", jsonObj.getDouble("sumWin"));
	                }
	                      	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
        	
			return respMap;
		}
	}
	
	public double miseKCycleWin(String url, String coderace, long mise, long l) throws ClientProtocolException, IOException, 
		JSONException, URISyntaxException, DAOAPIException {
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/totalWin/"+coderace+"/"+mise+"/"+l);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	double max = 0;
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	//	//System.out.println(content);
	        		if(!isValidJson(content)) {
	        			return 0;
	        		}
	                JSONObject json = new JSONObject(content);
	                resp_code = json.getString("entity");
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return 0l;
	                }
	                
	                String retSrc = j.getString("data");
	    //            //System.out.println(retSrc);
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("sumWin")) {
	                	max = jsonObj.getLong("sumWin");
	                }
	                else {
	                	max = 0;
	                }        	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		return 0;
        	}
        	
			return max;
		}
	}
	
	public double jackpot(String url, int k1, int k2, String coderace) throws ClientProtocolException, IOException, 
		JSONException, URISyntaxException, DAOAPIException {
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/jackpot/"+coderace+"/"+k1+"/"+k2);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
	 try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
    	double max = 0;
    	HttpEntity entity = null;
    	
    	try{
    		entity = response.getEntity();
    		
    		if (entity != null) {
    			
        		String content = EntityUtils.toString(entity);
        	//	//System.out.println(content);
        		if(!isValidJson(content)) {
        			return 0;
        		}
                JSONObject json = new JSONObject(content);
                resp_code = json.getString("entity");
                JSONObject j = new JSONObject(resp_code);
                
                String code = j.getString("code");
                if(!code.equalsIgnoreCase("200")) {
                	return 0l;
                }
                
                String retSrc = j.getString("data");
    //            //System.out.println(retSrc);
                JSONObject jsonObj = new JSONObject(retSrc.toString());
                
                if(jsonObj.has("jackpot")) {
                	max = jsonObj.getDouble("jackpot");
                }
                else {
                	max = 0;
                }        	           
			}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return 0;
    	}
    	
		return max;
	  }
	}
		
	public Double getBalance(String url, Long ncp) throws ClientProtocolException, IOException, 
		JSONException, URISyntaxException, DAOAPIException {
		
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/findbalance/"+ncp);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	Double solde = 0d;
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	//	//System.out.println(content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	      //          ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return 0d;
	                }
	                
	                String retSrc = j.getString("data");
	    //            //System.out.println(retSrc);
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("balance")) {
	                	solde = jsonObj.getDouble("balance");
	                }
	                else {
	                	solde = 0d;
	                }
	               
	         //       ////System.out.println("Solde: "+solde);
	                	           
    			}
        	}
        	catch(Exception e) {
       // 	catch(NumberFormatException | NullPointerException | JSONException e) {
        		//System.err.println(e.getMessage());
        		e.printStackTrace();
        		solde = 0d;
        		return solde;
        	}
        	
			return solde;
		}
	}
	
	public Map<String, String> sumOddKeno(String url, String coderace) throws ClientProtocolException, IOException, 
	JSONException, URISyntaxException, DAOAPIException {
		
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/sumOdd/"+coderace);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
	
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			Map<String, String> sOdd = new HashMap<String, String>();
	    	HttpEntity entity = null;
	    	
	    	try{
	    		entity = response.getEntity();
	    		
	    		if (entity != null) {
	    			
	        		String content = EntityUtils.toString(entity);
	        		String contents = content;
	        	//	//System.out.println(content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	        		JSONObject json = new JSONObject(content);
//	                if(content.substring(0, 1).equalsIgnoreCase("{")){
//	                	content = "["+content+"]";
//	    			}
	                
	                
	                ObjectMapper mapper = new ObjectMapper();
	            //    //System.out.println(content);
	                
//	                JSONArray ja = new JSONArray(content.toString());
//	                //System.out.println(ja);
	             
	                sOdd = mapper.readValue(contents.toString(), Map.class);
	                
	         //       ////System.out.println("ODDS------- "+sOdd);
	                
//	                ////System.out.println("ODDS value------- "+sOdd.get("44"));
//	                String code = j.getString("code");
//	                if(!code.equalsIgnoreCase("200")) {
//	                	return 0d;
//	                }
//	                
//	                String retSrc = j.getString("data");
//	    //            //System.out.println(retSrc);
//	                JSONObject jsonObj = new JSONObject(retSrc.toString());
//	                
//	                if(jsonObj.has("balance")) {
//	                	solde = jsonObj.getDouble("balance");
//	                }
//	                else {
//	                	solde = 0d;
//	                }
//	               
	         //       ////System.out.println("Solde: "+solde);
	                	           
				}
	    	}
	    	catch(Exception e) {
	   // 	catch(NumberFormatException | NullPointerException | JSONException e) {
	    		//System.err.println(e.getMessage());
	    		e.printStackTrace();
	    	}
	    	
			return sOdd;
		}
}
	
	public Keno getMaxDraw(String url, String coderace) throws ClientProtocolException, IOException, 
		JSONException, URISyntaxException, DAOAPIException {
	
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/max-draw/"+coderace);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			Long max = 0L;
			Keno keno = new Keno();
	    	HttpEntity entity = null;
	    	
	    	try{
	    		entity = response.getEntity();
	    		
	    		if (entity != null) {
	    			
	        		String content = EntityUtils.toString(entity);
	 //       		//System.out.println(content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	       //         ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                JSONObject kenoObject = j.getJSONObject("ken");
	                keno = this.mapToKeno(kenoObject);
	                
	                	           
				}
	    	}
	    	catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    	
		return keno;
	}
  }
	
	public BonusSet getbonusk(String url, String coderace) throws ClientProtocolException, IOException, 
		JSONException, URISyntaxException, DAOAPIException {
	
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/search-bonus/"+coderace);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			
			BonusSet bns = new BonusSet();
	    	HttpEntity entity = null;
	    	
	    	try{
	    		entity = response.getEntity();
	    		
	    		if (entity != null) {
	    			
	        		String content = EntityUtils.toString(entity);
	        //		//System.out.println(content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	                ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                JSONObject bonusObject = j.getJSONObject("bnset");
	                bns = this.mapToBonus(bonusObject);
	                
	                	           
				}
	    	}
	    	catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    	
		return bns;
	}
  }

  public BonusSet getcagnot(String url, String coderace) throws ClientProtocolException, IOException, 
		JSONException, URISyntaxException, DAOAPIException {
	
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/search-cagnotte/"+coderace);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			
			BonusSet bns = new BonusSet();
	    	HttpEntity entity = null;
	    	
	    	try{
	    		entity = response.getEntity();
	    		
	    		if (entity != null) {
	    			
	        		String content = EntityUtils.toString(entity);
	        //		//System.out.println(content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	         //       ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                JSONObject bonusObject = j.getJSONObject("bnset");
	                bns = this.mapToBonus(bonusObject);
	                
	                	           
				}
	    	}
	    	catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    	
		return bns;
	}
  }
	
	public Long getBarcode(String url, int jeu) throws ClientProtocolException, IOException, 
			JSONException, URISyntaxException, DAOAPIException {
	
		HttpGet getRequest = new HttpGet(url+"/barcode/"+jeu);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
	    	Long barcode = null;
	    	HttpEntity entity = null;
	    	
	    	try{
	    		entity = response.getEntity();
	    		
	    		if (entity != null) {
	        		String content = EntityUtils.toString(entity);
	  //      		////System.out.println("barcode: "+content);  
	        		JSONObject jsonObj = new JSONObject(content);
	        		barcode = jsonObj.getLong(content);
	        		//barcode = Long.parseLong(content);
				}
	    	}
	    	catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    	
			return barcode;
		  }
    }
	
	public int getState(String url, int state, String coderace) throws ClientProtocolException, IOException, 
			JSONException, URISyntaxException, DAOAPIException {
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/getstate/"+coderace+"/"+state);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			int gamestate = 0;
			HttpEntity entity = null;
			
			try{
				entity = response.getEntity();
				
				if (entity != null) {
		    		String content = EntityUtils.toString(entity);
		      		////System.out.println("state: "+content); 
		      		JSONObject json = new JSONObject(content);
		      	    resp_code = json.getString("entity");
		 	     
 	                JSONObject j = new JSONObject(resp_code);
 	                
 	                String code = j.getString("code");
 	                if(!code.equalsIgnoreCase("200")) {
 	                	return 0;
 	                }
 	               String retSrc = j.getString("data");
  	          //      //System.out.println(retSrc);
  	                JSONObject jsonObj = new JSONObject(retSrc.toString());
  	                
  	                if(jsonObj.has("state")) {
  	                	gamestate = jsonObj.getInt("state");
  	                }
  	                else {
  	                	gamestate = 0;
  	                }
		    	
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			return gamestate;
		  }
	}
	
	public CagnotteDto getCagnotte(String url, String coderace) throws ClientProtocolException, IOException, 
					JSONException, URISyntaxException, DAOAPIException {
	String resp_code;
	HttpGet getRequest = new HttpGet(url+"/cagnotte/"+coderace);
	// add request parameter, form parameters
	getRequest.setHeader("content-type", "application/json");
	
	try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
		CagnotteDto cgt = new CagnotteDto();
		HttpEntity entity = null;
		
		try{
			entity = response.getEntity();
			
			if (entity != null) {
    			
        		String content = EntityUtils.toString(entity);
        //	    ////System.out.println("user-content: "+content);
                JSONObject json = new JSONObject(content);
                
                //Verification du code reponse
                resp_code = json.getString("entity");
           //     ////System.out.println("resp_code "+resp_code);
                
                JSONObject j = new JSONObject(resp_code);
                
                String code = j.getString("code");
           //     ////System.out.println("CODE: "+code);
                if(!code.equalsIgnoreCase("200")) {
                	return null;
                }
                JSONObject betkObject = j.getJSONObject("cagnot");
                //System.out.println("betkObject: "+betkObject);
                cgt = this.mapToCagnotte(betkObject);
               // vers.setMessage(j.getString("message"));
           
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return cgt;
	  }
	}
	
	public String retrievecombi(String url, int num, String coderace) throws ClientProtocolException, IOException, 
		JSONException, URISyntaxException, DAOAPIException {
	String resp_code;
	HttpGet getRequest = new HttpGet(url+"/combinaison/"+coderace+"/"+num);
	// add request parameter, form parameters
	getRequest.setHeader("content-type", "application/json");
	
	try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
		String combinaison = "";
		HttpEntity entity = null;
		
		try{
			entity = response.getEntity();
			
			if (entity != null) {
	    		String content = EntityUtils.toString(entity);
	      		//System.out.println("combinaison: "+content); 
	      		JSONObject json = new JSONObject(content);
	      	    resp_code = json.getString("entity");
	 	     
	             JSONObject j = new JSONObject(resp_code);
	             
	             String code = j.getString("code");
	             if(!code.equalsIgnoreCase("200")) {
	             	return "";
	             }
	               String retSrc = j.getString("data");
	        //      //System.out.println(retSrc);
	              JSONObject jsonObj = new JSONObject(retSrc.toString());
	              
	              if(jsonObj.has("combinaison")) {
	            	  combinaison = jsonObj.getString("combinaison");
	              }
	             
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//System.out.println("retSrc combinaison "+combinaison);
		return combinaison;
	  }
	}
		
	public int getTimeKeno(String url, String coderace) throws ClientProtocolException, IOException, 
	JSONException, URISyntaxException, DAOAPIException {
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/timekeno/"+coderace);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			int time = 0;
			HttpEntity entity = null;
			
			try{
				entity = response.getEntity();
				
				if (entity != null) {
		    		String content = EntityUtils.toString(entity);
		      	//	////System.out.println("time: "+content); 
		      		JSONObject json = new JSONObject(content);
		      	    resp_code = json.getString("entity");
		 	     
 	                JSONObject j = new JSONObject(resp_code);
 	                
 	                String code = j.getString("code");
 	                if(!code.equalsIgnoreCase("200")) {
 	                	return 0;
 	                }
 	               String retSrc = j.getString("data");
  	          //      //System.out.println(retSrc);
  	                JSONObject jsonObj = new JSONObject(retSrc.toString());
  	                
  	                if(jsonObj.has("time")) {
  	                	time = jsonObj.getInt("time");
  	                }
  	                else {
  	                	time = 0;
  	                }
		    	
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			return time;
		  }
	}
	
	
	
    public boolean sendFinishDraw(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
		
		String playload = null;
		String resp_code;
		
		
        HttpPost post = new HttpPost(url+"/finish-draw/"+coderace);

        // add request parameter, form parameters
        post.setHeader("content-type", "application/json");
		post.setEntity(new StringEntity(playload));

        try (CloseableHttpResponse response = this.getClosableHttpClient().execute(post)) {
        	BetTicketK bet = new BetTicketK();
        	try{
        		HttpEntity entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        		////System.out.println("content: "+content);
//	                JSONObject json = new JSONObject(content);
//	                
//	                //Verification du code reponse
//	                resp_code = json.getString("entity");
//	                ////System.out.println("resp_code "+resp_code);
//	                
//	                JSONObject j = new JSONObject(resp_code);
//	                
//	                String code = j.getString("code");
//	                if(!code.equalsIgnoreCase("200")) {
//	                	return Boolean.FALSE;
//	                }
//	                JSONObject btkObject = j.getJSONObject("btick");
//	                bet = this.mapToBetTicket(btkObject);
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		return Boolean.FALSE;
        	}
            
            return Boolean.TRUE;
        }

    }
    
    public Misek misek(String url, long idmax) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
    	String resp_code;
    	HttpGet getRequest = new HttpGet(url+"/misek/"+idmax);
    	getRequest.setHeader("content-type", "application/json");
    	
    	List<Misek> misek = null;
    	
    	try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
    		Misek cgt = new Misek();
    		HttpEntity entity = null;
    		
    		try{
    			entity = response.getEntity();
    			
    			if (entity != null) {
        			
    				String content = EntityUtils.toString(entity);
	        		//////System.out.println("result "+content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	               // ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                
	                String retSrc = j.getString("data");
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                if(jsonObj.has("misek")) {
	                	String list_json = jsonObj.getString("misek");
	                	//////System.out.println("statK "+list_json);
	                	JSONArray jObj = new JSONArray(list_json.toString());
	                	List<Misek> object = new ArrayList<>();
	                	
	                	// CONVERT RESPONSE STRING TO JSON ARRAY
	        			
	        			int n = jObj.length();
	        			for(int i=0 ; i< n ; i++) {
	        				JSONObject jo = jObj.getJSONObject(i);
	        				//////System.out.println("misek "+jo);
	        				object.add(this.mapToMisekDto_(jo));
	        			}
	                	
	                	
	                	
		                misek = new ArrayList<>();
		                misek.addAll(object);
		                if(misek.isEmpty()) {
		                	return null;
		                }
		                cgt = misek.get(0);
//		                }
	                }
	                else {
	                	return null;
	                }
        	                
    			}
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    			return null;
    		}
    		
    		return cgt;
    	  }
	}
	
    public Caissier addUser(String url, Caissier cais, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
	 
	  String playload = null;
		String resp_code;
		HttpPost post;
		
		playload = mapToJsonString(cais);
		if(!isJSONValid(playload)) {
			return null;
		}
	 //////System.out.println("URL: "+url);
       post = new HttpPost(url+"/save-user/"+coderace);

      // add request parameter, form parameters
        post.setHeader("content-type", "application/json");
		post.setEntity(new StringEntity(playload));
	
      try (CloseableHttpResponse response = this.getClosableHttpClient().execute(post)) {
    	Caissier c = new Caissier();
      	try{
      		HttpEntity entity = response.getEntity();
      		
      		if (entity != null) {
      			
	        		String content = EntityUtils.toString(entity);
	        	//	////System.out.println("content: "+content);
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                  resp_code = json.getString("entity");
	           //     ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                JSONObject btkObject = j.getJSONObject("cais");
	                c = this.mapToCaissier(btkObject);
  			}
      	}
      	catch(Exception e) {
      		e.printStackTrace();
      		return null;
      	}
          
          return c;
      }
    }
    
    public int setbonusk(String url, double k_rate, double mbonusk0, double mbonusk1, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
    	String resp_code;
		HttpGet getRequest = new HttpGet(url+"/bonusk-cf/"+coderace+"/"+mbonusk0+"/"+mbonusk1+"/"+k_rate);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			int cfgBonus = 0;
			HttpEntity entity = null;
			
			try{
				entity = response.getEntity();
				
				if (entity != null) {
		    		String content = EntityUtils.toString(entity);
		      	 //   System.out.println("time: "+content); 
		      		JSONObject json = new JSONObject(content);
		      	    resp_code = json.getString("entity");
		 	     
 	                JSONObject j = new JSONObject(resp_code);
 	                
 	                String code = j.getString("code");
 	                if(!code.equalsIgnoreCase("200")) {
 	                	return 0;
 	                }
 	               String retSrc = j.getString("data");
  	          //      //System.out.println(retSrc);
  	                JSONObject jsonObj = new JSONObject(retSrc.toString());
  	                
  	                if(jsonObj.has("cfgBonus")) {
  	                	cfgBonus = jsonObj.getInt("cfgBonus");
  	                }
  	                else {
  	                	cfgBonus = 0;
  	                }
		    	
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			return cfgBonus;
		  }
	}
    
    public List<KenoRes> lDraw(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
    	
    	List<KenoRes> kenres = null;
    	
    	String resp_code;
		HttpGet getRequest = new HttpGet(url+"/last-draw/"+coderace);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	//	//System.out.println("result "+content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	              
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                
	                String retSrc = j.getString("data");
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("bonus")) {
	                	String list_json = jsonObj.getString("bonus");
	                	JSONArray jObj = new JSONArray(list_json.toString());
	                	
	        			int n = jObj.length();
	        			kenres = new ArrayList<>(n);
	        			
	        			for(int i=0 ; i< n ; i++) {
	        				JSONObject jo = jObj.getJSONObject(i);
	        				////System.out.println("tickets "+jo);
	        				kenres.add(this.mapToKenoRes(jo));
	        			}
	                	
	                }   	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
			return kenres;
		}
	}
    
    public List<KenoRes> lastTwelveDraw(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
    	
    	List<KenoRes> kenr = null;
    	
    	String resp_code;
		HttpGet getRequest = new HttpGet(url+"/last-twelve/"+coderace);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	//    System.out.println("result12: "+content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	              
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                
	                String retSrc = j.getString("data");
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("bonus")) {
	                	String list_json = jsonObj.getString("bonus");
	                	JSONArray jObj = new JSONArray(list_json.toString());
	                	
	        			int n = jObj.length();
	        			kenr = new ArrayList<>(n);
	        			
	        			for(int i=0 ; i< n ; i++) {
	        				JSONObject jo = jObj.getJSONObject(i);
	        				////System.out.println("tickets "+jo);
	        				kenr.add(this.mapToKenoRes(jo));
	        			}
	                	
	                }   	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
			return kenr;
		}
    }
    
    public List<KenoRes> bonus(String url, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
    	
    	List<KenoRes> kenres = null;
    	
    	String resp_code;
		HttpGet getRequest = new HttpGet(url+"/bonus/"+coderace);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        	//	//System.out.println("result "+content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	              
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                
	                String retSrc = j.getString("data");
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("bonus")) {
	                	String list_json = jsonObj.getString("bonus");
	                	JSONArray jObj = new JSONArray(list_json.toString());
	                	
	        			int n = jObj.length();
	        			kenres = new ArrayList<>(n);
	        			
	        			for(int i=0 ; i< n ; i++) {
	        				JSONObject jo = jObj.getJSONObject(i);
	        				////System.out.println("tickets "+jo);
	        				kenres.add(this.mapToKenoRes(jo));
	        			}
	                	
	                }   	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
			return kenres;
		}
	}
    
    public List<Airtime> airtimes(String url, String dat1, String dat2, String coderace, String login) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
    	List<Airtime> miset = null;
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/credit/"+coderace+"/"+login+"/"+dat1+"/"+dat2);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        		////System.out.println("result "+content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	               // ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                
	                String retSrc = j.getString("data");
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("voucher")) {
	                	String list_json = jsonObj.getString("voucher");
	                	JSONArray jObj = new JSONArray(list_json.toString());
	                	
	        			int n = jObj.length();
	        			miset = new ArrayList<>(n);
	        			
	        			for(int i=0 ; i< n ; i++) {
	        				JSONObject jo = jObj.getJSONObject(i);
	        				////System.out.println("tickets "+jo);
	        				miset.add(this.mapToAdminAirtime(jo));
	        			}
	                	
	                }
	                else {
	                	return null;
	                }    	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		
        		return null;
        	}
        	
			return miset;
		}
	}
    
    public List<AdminTicketDto> misets(String url, long t1, long t2, String coderace) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
    	List<AdminTicketDto> miset = null;
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/miset/"+coderace+"/"+t1+"/"+t2);
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        		//////System.out.println("result "+content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	               // ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                
	                String retSrc = j.getString("data");
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("tickets")) {
	                	String list_json = jsonObj.getString("tickets");
	                	JSONArray jObj = new JSONArray(list_json.toString());
	                	
	        			int n = jObj.length();
	        			miset = new ArrayList<>(n);
	        			
	        			for(int i=0 ; i< n ; i++) {
	        				JSONObject jo = jObj.getJSONObject(i);
	        				////System.out.println("tickets "+jo);
	        				miset.add(this.mapToAdminTicketDto(jo));
	        			}
	                	
	                }
	                else {
	                	return null;
	                }    	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		
        		return null;
        	}
        	
			return miset;
		}
	}
    
    public List<Caissier> superAdmin(String url) throws ClientProtocolException, IOException, JSONException, URISyntaxException, DAOAPIException {
		
    	List<Caissier> listCaissier = null;
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/superadmin");
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
			
			HttpEntity entity = null;
			
			try{
				
				entity = response.getEntity();
				
				if (entity != null) {
					
					String content = EntityUtils.toString(entity);
	        		System.out.println("result "+content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	                System.out.println("resp_code "+resp_code);
					
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return listCaissier;
	}
    
    public List<PartnerDto> retrieveAllPartner(String url) throws ClientProtocolException, IOException {
		
    	List<PartnerDto> listPartner = new ArrayList<>();
		String resp_code;
		HttpGet getRequest = new HttpGet(url+"/list-partners");
		// add request parameter, form parameters
		getRequest.setHeader("content-type", "application/json");
		
		try (CloseableHttpResponse response = this.getClosableHttpClient().execute(getRequest)) {
        	
        	HttpEntity entity = null;
        	
        	try{
        		entity = response.getEntity();
        		
        		if (entity != null) {
        			
	        		String content = EntityUtils.toString(entity);
	        		//////System.out.println("result "+content);
	        		if(!isValidJson(content)) {
	        			return null;
	        		}
	                JSONObject json = new JSONObject(content);
	                
	                //Verification du code reponse
	                resp_code = json.getString("entity");
	               // ////System.out.println("resp_code "+resp_code);
	                
	                JSONObject j = new JSONObject(resp_code);
	                
	                String code = j.getString("code");
	                if(!code.equalsIgnoreCase("200")) {
	                	return null;
	                }
	                
	                String retSrc = j.getString("data");
	                JSONObject jsonObj = new JSONObject(retSrc.toString());
	                
	                if(jsonObj.has("partners")) {
	                	
	                	String list_json = jsonObj.getString("partners");
	                	JSONArray jObj = new JSONArray(list_json.toString());
	                	
	        			int n = jObj.length();
	        			listPartner = new ArrayList<>(n);
	        			PartnerDto pdto;
	        			JSONObject jo;
	        			
	        			for(int i=0 ; i< n ; i++) {
	        				
	        				jo = jObj.getJSONObject(i);
	        				pdto = mapToPartnerDto(jo);
	        				listPartner.add(pdto);
	        				
	        			}
	                }  	           
    			}
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
		}
		
		return listPartner;
		
	}

	
	public String mapToJsonString(Object obj) throws JsonGenerationException, JsonMappingException, IOException, JSONException{
	 //	ObjectMapper mapper = new ObjectMapper();
	 	//mapper.setSerializationInclusion(Include.NON_NULL);
		String map = null;
		try {
		     map = this.mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String encoded = this.encodeJson(map);
		return map;
	}
	
	public BetTicketK mapToBetTicket(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		return this.mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(obj.toString(), BetTicketK.class);
		
	}
	
	public Caissier mapToCaissier(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		return this.mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(obj.toString(), Caissier.class);
		
	}
	
	public Keno mapToKeno(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		return this.mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(obj.toString(), Keno.class);
		
	}
	
	public PartnerDto mapToPartnerDto(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		return this.mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(obj.toString(), PartnerDto.class);
		
	}
	
	public Partner mapToPartner(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		return this.mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(obj.toString(), Partner.class);
		
	}
	
	public BonusSet mapToBonus(JSONObject obj)throws JsonParseException, JsonMappingException, IOException, JSONException {
		return this.mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(obj.toString(), BonusSet.class);
		
	}
	
	public Versement mapToVersement(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		return this.mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(obj.toString(), Versement.class);
		
	}
	
	public KenoRes mapToKenoRes(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		return this.mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(obj.toString(), KenoRes.class);
	
	}
	
	public ShiftDay mapToShiftDay(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		return this.mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(obj.toString(), ShiftDay.class);
		
	}
	
	public CagnotteDto mapToCagnotte(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		return this.mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(obj.toString(), CagnotteDto.class);
	}
	
	
	private boolean isJSONValid(String jsonInString ) {
	    try {
	      // final ObjectMapper mapper = new ObjectMapper();
	       this.mapper.readTree(jsonInString);
	       return true;
	    } catch (IOException e) {
	       return false;
	    }
	}
	
	private boolean isValidJson(String maybeJson){
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(maybeJson);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
	
	public String sendSimpleSMS(String baseUrl, String message, String phone) throws ClientProtocolException, IOException, URISyntaxException{
		
		try (CloseableHttpClient client = HttpClientBuilder.create().useSystemProperties().build()) {
			java.net.URI uri = null;
			try {
				uri = new URIBuilder(baseUrl)
						.addParameter("message", message)
						.addParameter("phone", phone)
						.build();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpGet getRequest = new HttpGet(uri);
			
			CloseableHttpResponse resp = client.execute(getRequest);
			
			return resp.getEntity().toString();
		}
	}
	
	public String sendSimpleMAIL(String baseUrl, String message, String phone) throws ClientProtocolException, IOException, URISyntaxException{
		return null;
	}
}

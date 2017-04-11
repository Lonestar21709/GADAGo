package com.lyledenman.gadago;

import com.codename1.io.*;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by Lyle on 4/5/2017.
 */
public class YelpRequest {

    private static final String API_HOST = "api.yelp.com";
    private static final String DEFAULT_TERM = "dinner";
    private static final String DEFAULT_LOCATION = "San Francisco, CA";
    private static final int SEARCH_LIMIT = 3;
    private static final String SEARCH_PATH = "/v2/search";
    private static final String BUSINESS_PATH = "/v2/business";

    /*
     * Update OAuth credentials below from the Yelp Developers API site:
     * http://www.yelp.com/developers/getting_started/api_access
     */

    private static final String CONSUMER_KEY = ConstantValues.YELP_CONSUMER_KEY;
    private static final String CONSUMER_SECRET = ConstantValues.YELP_CONSUMER_SECRET;
    private static final String TOKEN = ConstantValues.YELP_TOKEN;
    private static final String TOKEN_SECRET = ConstantValues.YELP_TOKEN_SECRET;
//    private final String accessToken = ConstantValues.ACCESS_TOKEN;

    private Map<String, Object> yelpResult = null;
    private int nPlacesReturned = 0;

    public YelpRequest(String foodPreference) {
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setPost(true);

            r.setUrl("https://api.yelp.com/oauth2/token");

            String grantType = Util.encodeBody("grant_type");
            String clientCred = Util.encodeBody("client_credentials");
            String clientId = Util.encodeBody("client_id");
            String clientId_ = Util.encodeBody("7kIp4gSQ3QZkxHbu9rEeDQ");
            String clientSecret = Util.encodeBody("client_secret");
            String clientSecret_ = Util.encodeBody("Gq5ljFxb4yGsq4VPBGJzpR2GUg3QgLUdXZZc6jFTpxYxrWXyoa0SVuPfAueOeVJa");

            r.addArgument(grantType, clientCred);
            r.addArgument(clientId, clientId_);
            r.addArgument(clientSecret, clientSecret_);

            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));

            // Get the access_token and expires_in from the result
            Object accessToken = result.get("access_token");
            Object expiresIn = result.get("expires_in");

            ConnectionRequest yelpReq = new ConnectionRequest();
            yelpReq.setPost(false);
            yelpReq.addRequestHeader("Authorization", ("Bearer " + accessToken.toString()));
            //cn1AccessToken = new AccessToken(accessToken.toString(), expiresIn.toString());
            yelpReq.setUrl("https://api.yelp.com/v3/businesses/search");

            yelpReq.addArgument("term", foodPreference);
            yelpReq.addArgument("categories", "restaurants,food");
            yelpReq.addArgument("latitude", "30.555007");
            yelpReq.addArgument("longitude", "-97.6662851");
            yelpReq.addArgument("radius", "10000"); // 20,000 m ~ 12 miles
            yelpReq.addArgument("open_now", "true");

            NetworkManager.getInstance().addToQueueAndWait(yelpReq);
            yelpResult = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(yelpReq.getResponseData()), "UTF-8"));

            } catch(Exception err) {
            Log.e(err);
        }
    }

    public Map<String,Object> getYelpResult() {
        if (yelpResult != null) {
            return yelpResult;
        }

        return null;
    }

    public void printAccessToken() {
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setPost(true);

            r.setUrl("https://api.yelp.com/oauth2/token");

            String grantType = Util.encodeBody("grant_type");
            String clientCred = Util.encodeBody("client_credentials");
            String clientId = Util.encodeBody("client_id");
            String clientId_ = Util.encodeBody("7kIp4gSQ3QZkxHbu9rEeDQ");
            String clientSecret = Util.encodeBody("client_secret");
            String clientSecret_ = Util.encodeBody("Gq5ljFxb4yGsq4VPBGJzpR2GUg3QgLUdXZZc6jFTpxYxrWXyoa0SVuPfAueOeVJa");

            r.addArgument(grantType, clientCred);
            r.addArgument(clientId, clientId_);
            r.addArgument(clientSecret, clientSecret_);

            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            Map<String, Object> response = (Map<String, Object>)result.get("response");

        } catch(Exception err) {
            Log.e(err);
        }
    }

    public void getBusinessSearch(String loc, Map<String, String> params) {

    }

    private int pageNumber = 1;

    java.util.List<Map<String, Object>> fetchPropertyData(String text) {
        try {
            ConnectionRequest r = new ConnectionRequest();


            Oauth2 oauth = new Oauth2("yelp_oauth_url", CONSUMER_KEY, "REDIRECT_URI", null, "token_request_url", CONSUMER_SECRET);


            r.setPost(false);
            r.setUrl("http://api.nestoria.co.uk/api");
//            r.setUrl("https://api.yelp.com/v2/search?term=german+food&location=Hayes&cll=37.77493,-122.419415\n");
            r.addArgument("pretty", "0");
            r.addArgument("action", "search_listings");
            r.addArgument("encoding", "json");
            r.addArgument("listing_type", "buy");
            r.addArgument("page", "" + pageNumber);
            pageNumber++;
            r.addArgument("country", "uk");
            r.addArgument("place_name", text);
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            Map<String, Object> response = (Map<String, Object>)result.get("response");
            return (java.util.List<Map<String, Object>>)response.get("listings");
        } catch(Exception err) {
            Log.e(err);
            return null;
        }
    }
}
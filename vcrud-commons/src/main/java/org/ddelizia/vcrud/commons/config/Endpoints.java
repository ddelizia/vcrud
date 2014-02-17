package org.ddelizia.vcrud.commons.config;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 06/02/14
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class Endpoints {

    public static final String REPLACE_REGEX = "\\{\\w*\\}";

    public static final String AUTHENTICATION_RESOURCE_BASE = "/auth/";

    public static class AuthenticationResource{
        public static class Labels{
            public static final String AUTH_TOKEN = "{authToken}";
        }
        public static final String LOGIN = AUTHENTICATION_RESOURCE_BASE + "login/"+Labels.AUTH_TOKEN+"/";
    }

    public static Set<String> getLabelsFromUrls (String url){
        Set<String> matchList = new HashSet<String>();
        Pattern regex = Pattern.compile(REPLACE_REGEX, Pattern.MULTILINE);
        Matcher regexMatcher = regex.matcher(url);
        while (regexMatcher.find()) {
            matchList.add(regexMatcher.group());
        }
        return matchList;
    }

    public static String buildUrl(String url, Map<String, String> map){
        String result = url;
        for(String key: map.keySet()){
            result.replace(key, map.get(key));
        }

        return result;
    }

    public static String getSimpleLabel(String label){
        return label.replaceAll("\\{", "").replaceAll("\\}", "");
    }
}

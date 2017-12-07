package com.nlecloud.api;

import com.google.gson.Gson;
import com.nlecloud.http.NleHttpGet;
import com.nlecloud.response.actuatorInfo.ActuatorInfoResponse;
import com.nlecloud.utils.Config;
import com.nlecloud.utils.UrlFormater;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActuatorInfoApi {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ActuatorInfoResponse executeApi(String gatewayTag, String apiTag, String accessToken) {
        NleHttpGet baseHttp = new NleHttpGet();
        String conversionUri = UrlFormater.format(Config.getString("GateWayActuatorInfo"), gatewayTag, apiTag);
        baseHttp.setUri(conversionUri);
        baseHttp.setHeader("AccessToken", accessToken);
        HttpResponse httpResponse = baseHttp.execute();
        try {
            Gson gson = new Gson();
            return gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), ActuatorInfoResponse.class);

        } catch (Exception e) {
            logger.error("json error {}", e.getMessage());
        }
        try {
            baseHttp.close();
        } catch (Exception e) {
            logger.error("http close error: {}", e.getMessage());
        }
        return null;
    }
}


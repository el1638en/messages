package com.syscom.rest.api.serversides;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Base64Utils;

import com.syscom.rest.api.AbstractTest;
import com.syscom.rest.api.LoginController;


@AutoConfigureMockMvc
public class AbstractIntTest extends AbstractTest {

    
    @Autowired
    protected MockMvc mockMvc;
    
    
    protected String getAccessToken(String username, String password) throws Exception {
         
        ResultActions result 
          = mockMvc.perform(MockMvcRequestBuilders.get(LoginController.PATH).header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString(StringUtils.join(username, ":",password).getBytes())))
            .andExpect(status().isOk());

     
        String resultString = result.andReturn().getResponse().getContentAsString();
     
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("value").toString();
    }
    
}

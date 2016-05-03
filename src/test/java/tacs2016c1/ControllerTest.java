package tacs2016c1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import com.g1.config.AppConfig;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class ControllerTest {
    
	@Autowired
    private WebApplicationContext ctx;
	
    private MockMvc mockMvc;
 
    @Before
    public void setUp() {
    	DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.ctx);
    	this.mockMvc = builder.build();
    }

    @Test
    public void testUserController () throws Exception {
    	MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/usuarios")
                .accept(MediaType.APPLICATION_JSON);

    	this.mockMvc.perform(builder)
    			.andExpect(MockMvcResultMatchers.status().isOk());	    	
    }

}

package springdemo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.example.DemoApplication;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class AccountControllerTest {
	private MediaType contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void loginTest() throws Exception {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("username", "test");
        hashmap.put("password", "test");

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(hashmap);
		System.out.println("JSON : "+json);

		ResultActions result = mockMvc.perform(post("/login").content(json).contentType(contentType));
		result.andDo(print());

		result.andExpect(status().isOk());
	}
}

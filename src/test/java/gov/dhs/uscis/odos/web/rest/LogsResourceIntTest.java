package gov.dhs.uscis.odos.web.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import gov.dhs.uscis.odos.base.test.BaseIntegrationTest;
import gov.dhs.uscis.odos.web.rest.vm.LoggerVM;

/**
 * Test class for the LogsResource REST controller.
 *
 * @see LogsResource
 */
public class LogsResourceIntTest extends BaseIntegrationTest {

    private MockMvc restLogsMockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        LogsResource logsResource = new LogsResource();
        this.restLogsMockMvc = MockMvcBuilders
            .standaloneSetup(logsResource)
            .build();
    }

    @Test
    public void getAllLogs()throws Exception {
        restLogsMockMvc.perform(get("/management/logs"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void changeLogs()throws Exception {
        LoggerVM logger = new LoggerVM();
        logger.setLevel("INFO");
        logger.setName("ROOT");
        
        assertEquals("LoggerVM{name='" + logger.getName() + "', level='" + logger.getLevel() + "'}", logger.toString());

        restLogsMockMvc.perform(put("/management/logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logger)))
            .andExpect(status().isNoContent());
    }

}

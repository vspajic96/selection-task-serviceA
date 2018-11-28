package com.vedran.servicea.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedran.servicea.domain.Transaction;
import com.vedran.servicea.event.TransactionPerformedEvent;
import com.vedran.servicea.service.TransactionServiceInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @MockBean
    private TransactionServiceInterface transactionServiceInterface;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<Transaction> json;

    @Before
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void makeValidTransactionTest() throws Exception {
        Transaction transactionValid = new Transaction(50, "EUR");

        MockHttpServletResponse responseValid = mvc.perform(post("").contentType(MediaType.APPLICATION_JSON)
                .content(json.write(transactionValid).getJson()))
                .andReturn().getResponse();

        assertThat(responseValid.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseValid.getContentAsString()).isEqualTo(json.write(transactionValid).getJson());
    }

    @Test
    public void makeInvalidTransactionTest() throws Exception {
        Transaction transactionInvalid = new Transaction(50, "BAM");

        MockHttpServletResponse responseInvalid = mvc.perform(post("").contentType(MediaType.APPLICATION_JSON)
                .content(json.write(transactionInvalid).getJson()))
                .andReturn().getResponse();

        assertThat(responseInvalid.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

}

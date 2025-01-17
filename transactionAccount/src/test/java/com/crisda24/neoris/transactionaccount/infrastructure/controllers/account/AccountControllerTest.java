package com.crisda24.neoris.transactionaccount.infrastructure.controllers.account;

import com.crisda24.neoris.transactionaccount.domain.common.dtos.account.AccountRequestDto;
import com.crisda24.neoris.transactionaccount.domain.common.dtos.account.AccountRequestPatchDto;
import com.crisda24.neoris.transactionaccount.application.input.port.AccountService;
import com.crisda24.neoris.transactionaccount.domain.models.Account;
import com.crisda24.neoris.transactionaccount.infrastructure.input.adapter.exceptions.rest.impl.AccountController;
import com.crisda24.neoris.transactionaccount.mockData.AccountMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountControllerTest {

    @MockBean
    private AccountService accountService;

    @Autowired
    private AccountController accountController;

    private AccountRequestDto accountRequestDto;

    @Autowired
    private ObjectMapper objectMapper;

    private AccountRequestPatchDto accountRequestPatchDto;

    @BeforeEach
    void setUp() {
        accountRequestDto  = AccountMock.createAccountRequestDto();
        accountRequestPatchDto= AccountMock.createAccountRequestPatchDto();
    }

    @Test
    void testCreateAccountInvalidAccountType() throws Exception {

        String content = (new ObjectMapper()).writeValueAsString(accountRequestDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder);


        actualPerformResult.andExpect(status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"accountType\":\"invalid account type: CORRIENTE o AHORRO\"}"));
    }


    @Test
    void testUpdateAccountPatchNotFound() throws Exception {

        when(accountService.accountPatch(any(String.class), any(AccountRequestPatchDto.class))).thenReturn(new Account());

        ResultActions result =MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(put("/api/v1/clients/{accountNumber}", "123456789")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequestPatchDto)));

        result.andExpect(status().isNotFound());
    }
}
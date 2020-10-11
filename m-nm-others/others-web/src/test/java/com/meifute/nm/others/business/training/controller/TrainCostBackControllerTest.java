package com.meifute.nm.others.business.training.controller;

import com.meifute.nm.others.business.training.entity.TrainCostBack;
import com.meifute.nm.others.business.training.service.TrainCostBackService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @Classname TrainCostBackControllerTest
 * @Description
 * @Date 2020-07-17 09:53
 * @Created by MR. Xb.Wu
 */
@Slf4j
class TrainCostBackControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TrainCostBackController controller;
    @Mock
    private TrainCostBackService trainCostBackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void backCostOne() throws Exception {
        String enrollId = "1";
        String remark = "退款";
        doNothing().when(trainCostBackService).backCostOne(enrollId, remark);

        mockMvc.perform(MockMvcRequestBuilders.request(
                HttpMethod.GET, "/bgw/training/cost/back")
                .param("enrollId", enrollId).param("remark",remark)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true))
                .andReturn();

        verify(trainCostBackService, times(1)).backCostOne(enrollId, remark);
    }

    @Test
    void queryBackCostRecordByEnrollId() throws Exception {
        String enrollId = "1";
        TrainCostBack costBack = new TrainCostBack();
        costBack.setCost(BigDecimal.valueOf(100));
        costBack.setEnrollId("1");

        when(trainCostBackService.queryBackCostRecordByEnrollId(enrollId)).thenReturn(costBack);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(
                HttpMethod.GET, "/bgw/training/cost/query/back/record")
                .param("enrollId", enrollId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        verify(trainCostBackService, times(1)).queryBackCostRecordByEnrollId(enrollId);
        Assert.notNull(content, "结果为空");
    }
}
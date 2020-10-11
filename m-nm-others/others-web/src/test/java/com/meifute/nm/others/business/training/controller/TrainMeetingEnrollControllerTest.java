package com.meifute.nm.others.business.training.controller;

import com.alibaba.fastjson.JSON;
import com.meifute.nm.others.business.training.entity.TrainMeetingEnroll;
import com.meifute.nm.others.business.training.service.TrainMeetingAffairsService;
import com.meifute.nm.others.business.training.service.TrainMeetingEnrollService;
import com.meifute.nm.others.business.training.enums.CostBackStatusEnum;
import com.meifute.nm.others.utils.CurrentUserService;
import com.meifute.nm.othersserver.domain.dto.MallUser;
import com.meifute.nm.othersserver.domain.dto.TrainMeetingEnrollDto;
import com.meifute.nm.othersserver.domain.vo.QueryMeetingEnrollParam;
import org.junit.jupiter.api.Assertions;
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

import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * @Classname TrainMeetingEnrollControllerTest
 * @Description
 * @Date 2020-07-17 10:46
 * @Created by MR. Xb.Wu
 */
class TrainMeetingEnrollControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private TrainMeetingEnrollController controller;
    @Mock
    private TrainMeetingEnrollService trainMeetingEnrollService;
    @Mock
    private TrainMeetingAffairsService trainMeetingAffairsService;
    @Mock
    private CurrentUserService currentUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void enrollMeeting() throws Exception {
        String meetingId = "11";
        MallUser user = new MallUser();
        user.setPhone("11111111");
        TrainMeetingEnroll enroll = new TrainMeetingEnroll();
        enroll.setMeetingName("会务活动");
        when(currentUserService.getCurrentUser()).thenReturn(user);
        when(trainMeetingEnrollService.createMeetingEnroll(user, meetingId)).thenReturn(enroll);
        mockMvc.perform(MockMvcRequestBuilders.request(
                HttpMethod.GET, "/bgw/training/enroll/enter")
                .param("meetingId", meetingId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(enroll))
                .andReturn();
        verify(currentUserService, times(1)).getCurrentUser();
    }

    @Test
    void queryMeetingEnrollPage() throws Exception {
        QueryMeetingEnrollParam queryMeetingEnrollParam = new QueryMeetingEnrollParam();
        queryMeetingEnrollParam.setMeetingId("111");
        when(trainMeetingEnrollService.queryMeetingEnrollPage(queryMeetingEnrollParam)).thenReturn(any());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(
                HttpMethod.POST, "/bgw/training/enroll/query/page")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(queryMeetingEnrollParam)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
        verify(trainMeetingEnrollService, times(1)).queryMeetingEnrollPage(queryMeetingEnrollParam);
    }

    @Test
    void queryMyMeetingEnrollPage() throws Exception {
        QueryMeetingEnrollParam queryMeetingEnrollParam = new QueryMeetingEnrollParam();
        queryMeetingEnrollParam.setMeetingId("111");
        when(trainMeetingEnrollService.queryMyMeetingEnrollPage(queryMeetingEnrollParam)).thenReturn(any());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(
                HttpMethod.POST, "/bgw/training/enroll/query/my/page")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(queryMeetingEnrollParam)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
        verify(trainMeetingEnrollService, times(1)).queryMyMeetingEnrollPage(queryMeetingEnrollParam);
    }

    @Test
    void signUp() throws Exception {
        String meetingId = "1";
        TrainMeetingEnrollDto enrollDto = new TrainMeetingEnrollDto();
        enrollDto.setId("11");
        enrollDto.setCostBacked(CostBackStatusEnum.COST_BACKING.getCode());
        when(trainMeetingEnrollService.signUp(meetingId)).thenReturn(enrollDto);

        doNothing().when(trainMeetingAffairsService).sendCostEnrollDelayMQ(Collections.singletonList(enrollDto.getId()));

        mockMvc.perform(MockMvcRequestBuilders.request(
                HttpMethod.GET, "/bgw/training/enroll/sign/up")
                .param("meetingId", meetingId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true))
                .andReturn();
        verify(trainMeetingAffairsService, times(1)).sendCostEnrollDelayMQ(Collections.singletonList(enrollDto.getId()));
    }

}
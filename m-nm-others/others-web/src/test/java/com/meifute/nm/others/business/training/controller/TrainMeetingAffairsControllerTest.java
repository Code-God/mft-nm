package com.meifute.nm.others.business.training.controller;

import com.alibaba.fastjson.JSON;
import com.meifute.nm.others.business.training.entity.TrainMeetingAffairs;
import com.meifute.nm.others.business.training.entity.TrainMeetingEnroll;
import com.meifute.nm.others.business.training.service.TrainMeetingAffairsService;
import com.meifute.nm.others.business.training.service.TrainMeetingEnrollService;
import com.meifute.nm.others.business.training.enums.MeetingStatusEnum;
import com.meifute.nm.others.business.training.enums.SignUpStatusEnum;
import com.meifute.nm.others.business.training.enums.TrainBackFlagEnum;
import com.meifute.nm.othersserver.domain.dto.MyMeetingAffairsDto;
import com.meifute.nm.othersserver.domain.dto.TrainMeetingAffairsDto;
import com.meifute.nm.othersserver.domain.vo.QueryMeetingParam;
import lombok.extern.slf4j.Slf4j;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @Classname TrainMeetingAffairsControllerTest
 * @Description
 * @Date 2020-07-16 13:32
 * @Created by MR. Xb.Wu
 */
@Slf4j
class TrainMeetingAffairsControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TrainMeetingAffairsController controller;
    @Mock
    private TrainMeetingAffairsService trainMeetingAffairsService;
    @Mock
    private TrainMeetingEnrollService trainMeetingEnrollService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createMeeting() throws Exception {
        TrainMeetingAffairsDto dto = new TrainMeetingAffairsDto();
        dto.setMeetingImg("11111");
        dto.setCreateTime(LocalDateTime.now());
        dto.setCost(BigDecimal.valueOf(100));
        dto.setEnrollEndTime(LocalDateTime.now());
        doNothing().when(trainMeetingAffairsService).createMeeting(dto);
        mockMvc.perform(MockMvcRequestBuilders.request(
                HttpMethod.POST, "/bgw/training/meeting/create/meeting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true))
                .andReturn();
    }

    @Test
    void updateMeeting() throws Exception {
        TrainMeetingAffairsDto dto = new TrainMeetingAffairsDto();
        dto.setId("111111111");
        dto.setCost(BigDecimal.valueOf(99));
        when(trainMeetingAffairsService.updateMeeting(dto)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.request(
                HttpMethod.POST, "/bgw/training/meeting/update/meeting")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true))
                .andReturn();
    }

    @Test
    void queryMeetingPage() throws Exception {
        QueryMeetingParam queryMeetingParam = new QueryMeetingParam();
        queryMeetingParam.setStatus(SignUpStatusEnum.NOT_SIGN_UP.getCode());
        when(trainMeetingAffairsService.queryMeetingPage(queryMeetingParam)).thenReturn(any());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(
                HttpMethod.POST, "/bgw/training/meeting/query/meeting/page")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(queryMeetingParam)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(result.getResponse().getContentAsString(), "结果为空");
    }

    @Test
    void queryMeetingPageToApp() throws Exception {
        QueryMeetingParam queryMeetingParam = new QueryMeetingParam();
        queryMeetingParam.setStatus(SignUpStatusEnum.NOT_SIGN_UP.getCode());
        when(trainMeetingAffairsService.queryMeetingPageToApp(queryMeetingParam)).thenReturn(any());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.request(
                HttpMethod.POST, "/bgw/training/meeting/query/my/meeting/page")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(queryMeetingParam)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Assertions.assertNotNull(result.getResponse().getContentAsString(), "结果为空");
    }

    @Test
    void releaseMeeting() throws Exception {
        String id = "111";
        String status = MeetingStatusEnum.AC_RELEASED.getCode();
        when(trainMeetingAffairsService.releaseMeeting(id, status)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.request(
                HttpMethod.GET, "/bgw/training/meeting/release/or/close/meeting")
                .param("id", id).param("status", status)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true))
                .andReturn();

        verify(trainMeetingAffairsService, times(1)).releaseMeeting(id, status);
    }

    @Test
    void closeMeeting() throws Exception {
        String id = "111";
        String status = MeetingStatusEnum.AC_CLOSED.getCode();

        TrainMeetingAffairs affairs = new TrainMeetingAffairs();
        affairs.setCostBack(TrainBackFlagEnum.CAN_BACK.getCode());

        when(trainMeetingAffairsService.queryById(id)).thenReturn(affairs);

        List<TrainMeetingEnroll> te = new ArrayList<>();
        when(trainMeetingEnrollService.queryNoBackList(any())).thenReturn(te);

        when(trainMeetingAffairsService.closeMeeting(affairs, status, te)).thenReturn(true);

        doNothing().when(trainMeetingAffairsService).sendCostEnrollDelayMQ(any());

        mockMvc.perform(MockMvcRequestBuilders.request(
                HttpMethod.GET, "/bgw/training/meeting/release/or/close/meeting")
                .param("id", id).param("status", status)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true))
                .andReturn();

        verify(trainMeetingAffairsService, times(1)).sendCostEnrollDelayMQ(any());
    }

    @Test
    void queryMeetingDetail() throws Exception {
        String id = "111";
        MyMeetingAffairsDto dto = new MyMeetingAffairsDto();
        dto.setMeetingName("会务活动");

        when(trainMeetingAffairsService.queryMeetingDetail(id)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.request(
                HttpMethod.GET, "/bgw/training/meeting/query/meeting/detail")
                .param("id", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(dto))
                .andReturn();
    }
}
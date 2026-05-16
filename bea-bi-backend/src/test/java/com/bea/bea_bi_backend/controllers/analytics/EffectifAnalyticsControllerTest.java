package com.bea.bea_bi_backend.controllers.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.services.analytics.EffectifAnalyticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EffectifAnalyticsControllerTest {

    @Mock
    private EffectifAnalyticsService service;

    @InjectMocks
    private EffectifAnalyticsController controller;

    @Test
    void getEffectifParService_shouldReturnOk() {
        when(service.getEffectifParService(any(), any(), any()))
                .thenReturn(List.of(new VwEffectifParService()));

        ResponseEntity<List<VwEffectifParService>> response = controller.getEffectifParService("DSI", null, 2025);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void exportEffectifParServiceExcel_shouldReturnFile() {
        byte[] fileContent = new byte[]{1, 2, 3};
        when(service.exportEffectifParServiceExcel(any(), any(), any())).thenReturn(fileContent);

        ResponseEntity<byte[]> response = controller.exportEffectifParServiceExcel("DSI", null, 2025);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(fileContent);
    }
}
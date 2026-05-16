package com.bea.bea_bi_backend.controllers.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.services.analytics.CreditAnalyticsService;
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
class CreditAnalyticsControllerTest {

    @Mock
    private CreditAnalyticsService service;

    @InjectMocks
    private CreditAnalyticsController controller;

    @Test
    void getEncoursParTypePret_shouldReturnData() {
        when(service.getEncoursParTypePret(any(), any(), any()))
                .thenReturn(List.of(new VwEncoursParTypePret()));

        ResponseEntity<List<VwEncoursParTypePret>> response =
                controller.getEncoursParTypePret(null, null, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getTopEmprunteurs_shouldReturnData() {
        when(service.getTopEmprunteurs(any(), any()))
                .thenReturn(List.of(new VwTopEmprunteurs()));

        ResponseEntity<List<VwTopEmprunteurs>> response =
                controller.getTopEmprunteurs("DSI", 2025);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void exportEncoursParTypePretExcel_shouldReturnFile() {
        byte[] file = new byte[]{10, 20, 30};
        when(service.exportEncoursParTypePretExcel(any(), any(), any())).thenReturn(file);

        ResponseEntity<byte[]> response = controller.exportEncoursParTypePretExcel(null, null, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(file);
    }
}
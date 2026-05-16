package com.bea.bea_bi_backend.controllers.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.services.analytics.ChargesAnalyticsService;
import org.junit.jupiter.api.BeforeEach;
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
class ChargesAnalyticsControllerTest {

    @Mock
    private ChargesAnalyticsService service;

    @InjectMocks
    private ChargesAnalyticsController controller;

    private List<VwMasseSalarialeMois> mockMasseData;

    @BeforeEach
    void setUp() {
        mockMasseData = List.of(new VwMasseSalarialeMois());
    }

    @Test
    void getMasseSalarialeMois_shouldReturnOk() {
        when(service.getMasseSalarialeMois(any(), any(), any())).thenReturn(mockMasseData);

        ResponseEntity<List<VwMasseSalarialeMois>> response = controller.getMasseSalarialeMois("DSI", 2025, 5);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockMasseData);
        verify(service).getMasseSalarialeMois("DSI", 2025, 5);
    }

    @Test
    void exportMasseSalarialeMoisExcel_shouldReturnFile() {
        byte[] fileContent = new byte[]{1, 2, 3, 4};
        when(service.exportMasseSalarialeMoisExcel(any(), any(), any())).thenReturn(fileContent);

        ResponseEntity<byte[]> response = controller.exportMasseSalarialeMoisExcel("DSI", 2025, 5);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(fileContent);
        assertThat(response.getHeaders().getContentDisposition().toString()).contains("masse_salariale_mois.xlsx");
    }

    @Test
    void exportMasseSalarialeMoisPdf_shouldReturnFile() {
        byte[] fileContent = new byte[]{5, 6, 7};
        when(service.exportMasseSalarialeMoisPdf(any(), any(), any())).thenReturn(fileContent);

        ResponseEntity<byte[]> response = controller.exportMasseSalarialeMoisPdf("DSI", 2025, 5);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(org.springframework.http.MediaType.APPLICATION_PDF);
    }
}
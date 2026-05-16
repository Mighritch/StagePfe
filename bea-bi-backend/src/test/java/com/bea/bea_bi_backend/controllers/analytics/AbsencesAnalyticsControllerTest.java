package com.bea.bea_bi_backend.controllers.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwAbsencesParMotif;
import com.bea.bea_bi_backend.services.analytics.AbsencesAnalyticsService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbsencesAnalyticsControllerTest {

    @Mock
    private AbsencesAnalyticsService service;

    @InjectMocks
    private AbsencesAnalyticsController controller;

    private List<VwAbsencesParMotif> mockData;

    @BeforeEach
    void setUp() {
        mockData = List.of(new VwAbsencesParMotif());
    }

    @Test
    void getAbsencesParMotif_shouldReturnData() {
        when(service.getAbsencesParMotif(any(), any(), any())).thenReturn(mockData);

        ResponseEntity<List<VwAbsencesParMotif>> response = controller.getAbsencesParMotif(null, null, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockData);
        verify(service).getAbsencesParMotif(null, null, null);
    }

    @Test
    void exportExcel_shouldReturnFile() {
        byte[] fileContent = new byte[]{1, 2, 3};
        when(service.exportAbsencesParMotifExcel(any(), any(), any())).thenReturn(fileContent);

        ResponseEntity<byte[]> response = controller.exportAbsencesParMotifExcel(null, null, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(fileContent);
        assertThat(response.getHeaders().getContentDisposition().toString()).contains("absences_par_motif.xlsx");
    }
}
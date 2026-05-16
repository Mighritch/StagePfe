package com.bea.bea_bi_backend.services.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.repositories.analytics.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditAnalyticsServiceTest {

    @Mock
    private VwTopEmprunteursRepository topEmprunteursRepo;
    @Mock
    private ExportService exportService;

    @InjectMocks
    private CreditAnalyticsService service;

    @Test
    void exportTopEmprunteursExcel_shouldSucceed() {
        when(topEmprunteursRepo.findAll()).thenReturn(List.of(new VwTopEmprunteurs()));
        when(exportService.exportToExcel(anyList(), anyString(), any(), any()))
                .thenReturn(new byte[]{10, 20, 30});

        byte[] result = service.exportTopEmprunteursExcel(null, null);

        assertThat(result).isNotNull();
        verify(exportService).exportToExcel(anyList(), eq("Top Emprunteurs"), any(), any());
    }
}
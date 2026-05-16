package com.bea.bea_bi_backend.services.analytics;

import com.bea.bea_bi_backend.entities.analytics.VwAbsencesParMotif;
import com.bea.bea_bi_backend.repositories.analytics.VwAbsencesParMotifRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbsencesAnalyticsServiceTest {

    @Mock
    private VwAbsencesParMotifRepository repo;

    @Mock
    private ExportService exportService;

    @InjectMocks
    private AbsencesAnalyticsService service;

    @Test
    void getAbsencesParMotif_withFilters_shouldCallRepositoryWithFilters() {
        when(repo.findByFiltres("DSI", 2025, "Maladie")).thenReturn(List.of(new VwAbsencesParMotif()));

        List<VwAbsencesParMotif> result = service.getAbsencesParMotif("DSI", 2025, "Maladie");

        assertThat(result).isNotEmpty();
        verify(repo).findByFiltres("DSI", 2025, "Maladie");
    }

    @Test
    void exportToExcel_shouldCallExportService() {
        when(repo.findAll()).thenReturn(List.of(new VwAbsencesParMotif()));
        when(exportService.exportToExcel(anyList(), anyString(), any(), any())).thenReturn(new byte[]{1,2,3});

        byte[] result = service.exportAbsencesParMotifExcel(null, null, null);

        assertThat(result).isNotNull();
        verify(exportService).exportToExcel(anyList(), eq("Absences par Motif"), any(), any());
    }
}
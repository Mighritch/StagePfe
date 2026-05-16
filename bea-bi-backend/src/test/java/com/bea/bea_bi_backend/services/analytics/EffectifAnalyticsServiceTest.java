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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EffectifAnalyticsServiceTest {

    @Mock
    private VwEffectifParServiceRepository effectifParServiceRepo;
    @Mock
    private VwEffectifParGradeRepository effectifParGradeRepo;
    @Mock
    private VwEffectifParAdmtechRepository effectifParAdmtechRepo;
    @Mock
    private VwEffectifParSexeRepository effectifParSexeRepo;
    @Mock
    private VwEffectifEvolutionRepository effectifEvolutionRepo;
    @Mock
    private VwEffectifServiceGradeRepository effectifServiceGradeRepo;
    @Mock
    private VwEffectifParAffectationRepository effectifParAffectationRepo;
    @Mock
    private ExportService exportService;

    @InjectMocks
    private EffectifAnalyticsService service;

    @Test
    void getEffectifParService_shouldCallRepositoryWithFilters() {
        when(effectifParServiceRepo.findByFiltres("DSI", "Ingénieur", 2025))
                .thenReturn(List.of(new VwEffectifParService()));

        List<VwEffectifParService> result = service.getEffectifParService("DSI", "Ingénieur", 2025);

        assertThat(result).isNotEmpty();
        verify(effectifParServiceRepo).findByFiltres("DSI", "Ingénieur", 2025);
    }

    @Test
    void exportEffectifParGradeExcel_shouldCallExportService() {
        when(effectifParGradeRepo.findAll()).thenReturn(List.of(new VwEffectifParGrade()));
        when(exportService.exportToExcel(anyList(), anyString(), any(), any()))
                .thenReturn(new byte[]{1, 2, 3});

        byte[] result = service.exportEffectifParGradeExcel(null, null, null);

        assertThat(result).isNotNull();
        verify(exportService).exportToExcel(anyList(), eq("Effectif par Grade"), any(), any());
    }
}
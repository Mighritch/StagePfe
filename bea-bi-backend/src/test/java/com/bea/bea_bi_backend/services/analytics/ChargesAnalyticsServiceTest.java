package com.bea.bea_bi_backend.services.analytics;

import com.bea.bea_bi_backend.entities.analytics.*;
import com.bea.bea_bi_backend.repositories.analytics.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChargesAnalyticsServiceTest {

    @Mock
    private VwMasseSalarialeMoisRepository masseSalarialeMoisRepo;
    @Mock
    private VwChargesParTypeBulletinRepository chargesParTypeBulletinRepo;
    @Mock
    private VwMasseSalarialeServiceRepository masseSalarialeServiceRepo;
    @Mock
    private VwSalaireParGradeRepository salaireParGradeRepo;
    @Mock
    private VwEvolutionMasseSalarialeRepository evolutionMasseSalarialeRepo;
    @Mock
    private VwTauxChargeServiceRepository tauxChargeServiceRepo;
    @Mock
    private ExportService exportService;

    @InjectMocks
    private ChargesAnalyticsService service;

    private List<VwMasseSalarialeMois> mockMasseData;

    @BeforeEach
    void setUp() {
        mockMasseData = List.of(new VwMasseSalarialeMois());
    }

    @Test
    void getMasseSalarialeMois_withFilters_shouldCallRepository() {
        when(masseSalarialeMoisRepo.findByFiltres("DSI", 2025, 5))
                .thenReturn(mockMasseData);

        List<VwMasseSalarialeMois> result = service.getMasseSalarialeMois("DSI", 2025, 5);

        assertThat(result).isNotNull();
        verify(masseSalarialeMoisRepo).findByFiltres("DSI", 2025, 5);
    }

    @Test
    void exportMasseSalarialeMoisExcel_shouldCallExportService() {
        when(masseSalarialeMoisRepo.findAll()).thenReturn(mockMasseData);
        when(exportService.exportToExcel(anyList(), anyString(), any(), any()))
                .thenReturn(new byte[]{1, 2, 3});

        byte[] result = service.exportMasseSalarialeMoisExcel(null, null, null);

        assertThat(result).isNotNull();
        verify(exportService).exportToExcel(anyList(), eq("Masse Salariale par Mois"), any(), any());
    }
}
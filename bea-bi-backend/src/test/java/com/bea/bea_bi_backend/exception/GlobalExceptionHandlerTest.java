package com.bea.bea_bi_backend.exception;

import com.bea.bea_bi_backend.controllers.analytics.AbsencesAnalyticsController;
import com.bea.bea_bi_backend.services.analytics.AbsencesAnalyticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AbsencesAnalyticsController.class)
@Import(GlobalExceptionHandler.class)   // Important
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbsencesAnalyticsService absencesAnalyticsService;

    @Test
    @WithMockUser(username = "testuser", roles = "USER")   // Bypass security
    void shouldHandleRuntimeException() throws Exception {
        when(absencesAnalyticsService.getAbsencesParMotif(any(), any(), any()))
                .thenThrow(new RuntimeException("Erreur simulée de base de données"));

        mockMvc.perform(get("/api/analytics/absences/par-motif"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Erreur simulée de base de données"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void shouldHandleBadRequest() throws Exception {
        mockMvc.perform(get("/api/analytics/absences/par-motif")
                        .param("annee", "abc"))  // Mauvais type
                .andExpect(status().isBadRequest());
    }
}
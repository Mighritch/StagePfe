SQL> 
SQL> SELECT DBMS_METADATA.GET_DDL('VIEW', VIEW_NAME)
  2  FROM USER_VIEWS
  3  WHERE VIEW_NAME LIKE 'VW_%';
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_ABSENCES_PAR_ANNEE" ("ANNEE", "TYP_C                                                                                                                        
NG", "LIB_SERV", "NB_DEMANDES", "TOTAL_JOURS") AS                                                                                                                                                       
  SELECT                                                                                                                                                                                                
    f.ANNEE_CNG    AS ANNEE,                                                                                                                                                                            
    f.TYP_CNG,                                                                                                                                                                                          
    s.LIB_SERV,                                                                                                                                                                                         
    COUNT(*)       AS NB_DEMANDES,                                                                                                                                                                      
    SUM(f.NBR_JOURS) AS TOTAL_JOURS                                                                                                                                                                     
FROM FAIT_ABSENCES f                                                                                                                                                                                    
JOIN DIM_PERSONNEL p ON f.MAT_PERS  = p.MAT_PERS                                                                                                                                                        
JOIN DIM_SERVICE   s ON p.COD_SERV  = s.COD_SERV                                                                                                                                                        
GROUP BY                                                                                                                                                                                                
    f.ANNEE_CNG,                                                                                                                                                                                        
    f.TYP_CNG,                                                                                                                                                                                          
    s.LIB_SERV                                                                                                                                                                                          
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_ABSENCES_PAR_MOTIF" ("LIB_MOT", "TYP                                                                                                                        
_CNG", "ABRV_MOT", "LIB_SERV", "ANNEE", "NB_DEMANDES", "TOTAL_JOURS") AS                                                                                                                                
  SELECT                                                                                                                                                                                                
    m.LIB_MOT,                                                                                                                                                                                          
    m.TYP_CNG,                                                                                                                                                                                          
    m.ABRV_MOT,                                                                                                                                                                                         
    s.LIB_SERV,                                                                                                                                                                                         
    f.ANNEE_CNG            AS ANNEE,                                                                                                                                                                    
    COUNT(*)               AS NB_DEMANDES,                                                                                                                                                              
    SUM(d.NBR_JOURS)       AS TOTAL_JOURS                                                                                                                                                               
FROM FAIT_ABSENCES     f                                                                                                                                                                                
JOIN DIM_MOTIFABSENCE  m ON f.COD_M    = m.COD_M                                                                                                                                                        
JOIN DIM_PERSONNEL     p ON f.MAT_PERS = p.MAT_PERS                                                                                                                                                     
JOIN DIM_SERVICE       s ON p.COD_SERV = s.COD_SERV                                                                                                                                                     
JOIN DIM_DEMANDECONGE  d ON f.NUM_DCNG = d.NUM_DCNG                                                                                                                                                     
GROUP BY                                                                                                                                                                                                
    m.LIB_MOT,                                                                                                                                                                                          
    m.TYP_CNG,                                                                                                                                                                                          
    m.ABRV_MOT,                                                                                                                                                                                         
    s.LIB_SERV,                                                                                                                                                                                         
    f.ANNEE_CNG                                                                                                                                                                                         
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_ABSENTEISME_SERVICE" ("LIB_SERV", "A                                                                                                                        
NNEE", "NB_EMPLOYES_ABSENTS", "TOTAL_JOURS_ABSENCE", "MOYENNE_JOURS_PAR_EMPLOYE"                                                                                                                        
) AS                                                                                                                                                                                                    
  SELECT s.LIB_SERV,                                                                                                                                                                                    
       f.ANNEE_CNG AS ANNEE,                                                                                                                                                                            
       COUNT(DISTINCT f.MAT_PERS) AS NB_EMPLOYES_ABSENTS,                                                                                                                                               
       SUM(f.NBR_JOURS) AS TOTAL_JOURS_ABSENCE,                                                                                                                                                         
       ROUND(SUM(f.NBR_JOURS) /                                                                                                                                                                         
             COUNT(DISTINCT f.MAT_PERS), 2) AS MOYENNE_JOURS_PAR_EMPLOYE                                                                                                                                
FROM FAIT_ABSENCES f                                                                                                                                                                                    
JOIN DIM_PERSONNEL p ON f.MAT_PERS = p.MAT_PERS                                                                                                                                                         
JOIN DIM_SERVICE s ON p.COD_SERV = s.COD_SERV                                                                                                                                                           
GROUP BY s.LIB_SERV, f.ANNEE_CNG                                                                                                                                                                        
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_CAPITAL_RESTANT_PRET" ("MAT_PERS", "                                                                                                                        
NOM_PERS", "LIB_SERV", "LIB_PRET", "MONTANT_CREDIT", "CAPITAL_RESTANT", "PRT_DAT                                                                                                                        
_FIN", "ANNEE") AS                                                                                                                                                                                      
  SELECT                                                                                                                                                                                                
    p.MAT_PERS,                                                                                                                                                                                         
    p.NOM_PERS,                                                                                                                                                                                         
    s.LIB_SERV,                                                                                                                                                                                         
    tp.LIB_PRET,                                                                                                                                                                                        
    fc.MONTANT_CREDIT,                                                                                                                                                                                  
    SUM(dl.CAP_REST)                    AS CAPITAL_RESTANT,                                                                                                                                             
    pp.PRT_DAT_FIN,                                                                                                                                                                                     
    EXTRACT(YEAR FROM pp.PRT_DAT_DEB)   AS ANNEE                                                                                                                                                        
FROM FAIT_CREDIT fc                                                                                                                                                                                     
JOIN DIM_PRETPERS  pp ON  fc.COD_SOC  = pp.COD_SOC                                                                                                                                                      
                      AND fc.MAT_PERS  = pp.MAT_PERS                                                                                                                                                    
                      AND fc.COD_PRET  = pp.COD_PRET                                                                                                                                                    
JOIN DIM_LIGNEPRET dl ON  fc.COD_SOC  = dl.COD_SOC                                                                                                                                                      
                      AND fc.MAT_PERS  = dl.MAT_PERS                                                                                                                                                    
                      AND fc.COD_PRET  = dl.COD_PRET                                                                                                                                                    
JOIN DIM_TYPEPRET  tp ON  fc.COD_SOC  = tp.COD_SOC                                                                                                                                                      
                      AND fc.TYP_PRET  = tp.TYP_PRET                                                                                                                                                    
                      AND fc.COD_GRP_PRET = tp.COD_GRP_PRET                                                                                                                                             
JOIN DIM_PERSONNEL p  ON  fc.MAT_PERS = p.MAT_PERS                                                                                                                                                      
JOIN DIM_SERVICE   s  ON  p.COD_SERV  = s.COD_SERV                                                                                                                                                      
WHERE dl.CAP_REST > 0                                                                                                                                                                                   
GROUP BY                                                                                                                                                                                                
    p.MAT_PERS, p.NOM_PERS, s.LIB_SERV,                                                                                                                                                                 
    tp.LIB_PRET, fc.MONTANT_CREDIT,                                                                                                                                                                     
    pp.PRT_DAT_FIN,                                                                                                                                                                                     
    EXTRACT(YEAR FROM pp.PRT_DAT_DEB)                                                                                                                                                                   
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_CHARGES_PAR_TYPE_BULLETIN" ("TYPEBUL                                                                                                                        
LETIN_KEY", "COD_TYP_BUL", "LIB_BULL", "TYP_BUL", "SOLDE", "COEF_BUL", "LIB_SERV                                                                                                                        
", "ANNEE", "TOTAL_CHARGES", "NB_LIGNES", "NB_EMPLOYES_UNIQUE") AS                                                                                                                                      
  SELECT                                                                                                                                                                                                
    t.TYPEBULLETIN_KEY,                                                                                                                                                                                 
    t.COD_TYP_BUL,                                                                                                                                                                                      
    t.LIB_BULL,           -- Correction : deux L                                                                                                                                                        
    t.TYP_BUL,                                                                                                                                                                                          
    t.SOLDE,                                                                                                                                                                                            
    t.COEF_BUL,                                                                                                                                                                                         
    s.LIB_SERV,                                                                                                                                                                                         
    EXTRACT(YEAR FROM f.DT_BUL) AS ANNEE,                                                                                                                                                               
    ROUND(SUM(f.MONTANT_CHARGE), 2) AS TOTAL_CHARGES,                                                                                                                                                   
    COUNT(*) AS NB_LIGNES,                                                                                                                                                                              
    COUNT(DISTINCT f.MAT_PERS) AS NB_EMPLOYES_UNIQUE                                                                                                                                                    
FROM FAIT_CHARGESPERS f                                                                                                                                                                                 
JOIN DIM_TYPEBULLETIN t   ON f.COD_TYP_BUL = t.COD_TYP_BUL                                                                                                                                              
JOIN DIM_PERSONNEL p      ON f.MAT_PERS    = p.MAT_PERS                                                                                                                                                 
JOIN DIM_SERVICE s        ON p.COD_SERV     = s.COD_SERV                                                                                                                                                
GROUP BY                                                                                                                                                                                                
    t.TYPEBULLETIN_KEY,                                                                                                                                                                                 
    t.COD_TYP_BUL,                                                                                                                                                                                      
    t.LIB_BULL,                                                                                                                                                                                         
    t.TYP_BUL,                                                                                                                                                                                          
    t.SOLDE,                                                                                                                                                                                            
    t.COEF_BUL,                                                                                                                                                                                         
    s.LIB_SERV,                                                                                                                                                                                         
    EXTRACT(YEAR FROM f.DT_BUL)                                                                                                                                                                         
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_DUREE_MOYENNE_CONGE" ("LIB_MOT", "LI                                                                                                                        
B_SERV", "ANNEE", "DUREE_MOYENNE", "DUREE_MIN", "DUREE_MAX") AS                                                                                                                                         
  SELECT                                                                                                                                                                                                
    m.LIB_MOT,                                                                                                                                                                                          
    s.LIB_SERV,                                                                                                                                                                                         
    f.ANNEE_CNG                    AS ANNEE,                                                                                                                                                            
    ROUND(AVG(d.NBR_JOURS), 2)     AS DUREE_MOYENNE,                                                                                                                                                    
    MIN(d.NBR_JOURS)               AS DUREE_MIN,                                                                                                                                                        
    MAX(d.NBR_JOURS)               AS DUREE_MAX                                                                                                                                                         
FROM FAIT_ABSENCES     f                                                                                                                                                                                
JOIN DIM_MOTIFABSENCE  m ON f.COD_M    = m.COD_M                                                                                                                                                        
JOIN DIM_DEMANDECONGE  d ON f.NUM_DCNG = d.NUM_DCNG                                                                                                                                                     
                        AND f.MAT_PERS = d.MAT_PERS                                                                                                                                                     
                        AND f.COD_SOC  = d.COD_SOC                                                                                                                                                      
JOIN DIM_PERSONNEL     p ON f.MAT_PERS = p.MAT_PERS                                                                                                                                                     
JOIN DIM_SERVICE       s ON p.COD_SERV = s.COD_SERV                                                                                                                                                     
GROUP BY                                                                                                                                                                                                
    m.LIB_MOT,                                                                                                                                                                                          
    s.LIB_SERV,                                                                                                                                                                                         
    f.ANNEE_CNG                                                                                                                                                                                         
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_EFFECTIF_EVOLUTION" ("MOIS", "ANNEE"                                                                                                                        
, "LIB_SERV", "LIB_GRAD", "NB_EMPLOYES") AS                                                                                                                                                             
  SELECT TRUNC(f.DATE_EFF, 'MM') AS MOIS,                                                                                                                                                               
       EXTRACT(YEAR FROM f.DATE_EFF) AS ANNEE,                                                                                                                                                          
       s.LIB_SERV,                                                                                                                                                                                      
       g.LIB_GRAD,                                                                                                                                                                                      
       COUNT(*) AS NB_EMPLOYES                                                                                                                                                                          
FROM FACT_EFFECTIF f                                                                                                                                                                                    
JOIN DIM_SERVICE s ON f.SERVICE_KEY = s.SERVICE_KEY                                                                                                                                                     
JOIN DIM_GRADE g ON f.GRADE_KEY = g.GRADE_KEY                                                                                                                                                           
GROUP BY TRUNC(f.DATE_EFF, 'MM'),                                                                                                                                                                       
         EXTRACT(YEAR FROM f.DATE_EFF),                                                                                                                                                                 
         s.LIB_SERV, g.LIB_GRAD                                                                                                                                                                         
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_EFFECTIF_PAR_ADMTECH" ("LIB_ADM_TECH                                                                                                                        
", "COD_CAT", "LIB_SERV", "ANNEE", "NB_EMPLOYES") AS                                                                                                                                                    
  SELECT t.LIB_ADM_TECH,                                                                                                                                                                                
       t.COD_CAT,                                                                                                                                                                                       
       s.LIB_SERV,                                                                                                                                                                                      
       EXTRACT(YEAR FROM f.DATE_EFF) AS ANNEE,                                                                                                                                                          
       COUNT(*) AS NB_EMPLOYES                                                                                                                                                                          
FROM FACT_EFFECTIF f                                                                                                                                                                                    
JOIN DIM_TYPADMTECH t ON f.ADMTECH_KEY = t.ADMTECH_KEY                                                                                                                                                  
JOIN DIM_SERVICE s ON f.SERVICE_KEY = s.SERVICE_KEY                                                                                                                                                     
GROUP BY t.LIB_ADM_TECH, t.COD_CAT, s.LIB_SERV,                                                                                                                                                         
         EXTRACT(YEAR FROM f.DATE_EFF)                                                                                                                                                                  
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_EFFECTIF_PAR_AFFECTATION" ("LIB_AFFE                                                                                                                        
CT", "REGIME", "JOURS", "HEURES", "LIB_SERV", "ANNEE", "NB_EMPLOYES") AS                                                                                                                                
  SELECT a.LIB_AFFECT,                                                                                                                                                                                  
       a.REGIME,                                                                                                                                                                                        
       a.JOURS,                                                                                                                                                                                         
       a.HEURES,                                                                                                                                                                                        
       s.LIB_SERV,                                                                                                                                                                                      
       EXTRACT(YEAR FROM f.DATE_EFF) AS ANNEE,                                                                                                                                                          
       COUNT(*) AS NB_EMPLOYES                                                                                                                                                                          
FROM FACT_EFFECTIF f                                                                                                                                                                                    
JOIN DIM_AFFECTATION a ON f.AFFECTATION_KEY = a.AFFECTATION_KEY                                                                                                                                         
JOIN DIM_SERVICE s ON f.SERVICE_KEY = s.SERVICE_KEY                                                                                                                                                     
GROUP BY a.LIB_AFFECT, a.REGIME, a.JOURS, a.HEURES,                                                                                                                                                     
         s.LIB_SERV, EXTRACT(YEAR FROM f.DATE_EFF)                                                                                                                                                      
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_EFFECTIF_PAR_GRADE" ("LIB_GRAD", "CO                                                                                                                        
D_CATEG", "LIB_SERV", "ANNEE", "NB_EMPLOYES") AS                                                                                                                                                        
  SELECT g.LIB_GRAD,                                                                                                                                                                                    
       g.COD_CATEG,                                                                                                                                                                                     
       s.LIB_SERV,                                                                                                                                                                                      
       EXTRACT(YEAR FROM f.DATE_EFF) AS ANNEE,                                                                                                                                                          
       COUNT(*) AS NB_EMPLOYES                                                                                                                                                                          
FROM FACT_EFFECTIF f                                                                                                                                                                                    
JOIN DIM_GRADE g ON f.GRADE_KEY = g.GRADE_KEY                                                                                                                                                           
JOIN DIM_SERVICE s ON f.SERVICE_KEY = s.SERVICE_KEY                                                                                                                                                     
GROUP BY g.LIB_GRAD, g.COD_CATEG, s.LIB_SERV,                                                                                                                                                           
         EXTRACT(YEAR FROM f.DATE_EFF)                                                                                                                                                                  
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_EFFECTIF_PAR_SERVICE" ("LIB_SERV", "                                                                                                                        
TYPE_SERV", "LIB_GRAD", "ANNEE", "MOIS", "NB_EMPLOYES") AS                                                                                                                                              
  SELECT                                                                                                                                                                                                
    s.LIB_SERV,                                                                                                                                                                                         
    s.TYPE_SERV,                                                                                                                                                                                        
    g.LIB_GRAD,                                                                                                                                                                                         
    EXTRACT(YEAR FROM f.DATE_EFF)  AS ANNEE,                                                                                                                                                            
    TRUNC(f.DATE_EFF, 'MM')        AS MOIS,                                                                                                                                                             
    COUNT(*)                       AS NB_EMPLOYES                                                                                                                                                       
FROM FACT_EFFECTIF f                                                                                                                                                                                    
JOIN DIM_SERVICE s ON f.SERVICE_KEY = s.SERVICE_KEY                                                                                                                                                     
JOIN DIM_GRADE   g ON f.GRADE_KEY   = g.GRADE_KEY                                                                                                                                                       
GROUP BY                                                                                                                                                                                                
    s.LIB_SERV,                                                                                                                                                                                         
    s.TYPE_SERV,                                                                                                                                                                                        
    g.LIB_GRAD,                                                                                                                                                                                         
    EXTRACT(YEAR FROM f.DATE_EFF),                                                                                                                                                                      
    TRUNC(f.DATE_EFF, 'MM')                                                                                                                                                                             
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_EFFECTIF_PAR_SEXE" ("ROW_ID", "SEXE"                                                                                                                        
, "NB_EMPLOYES", "POURCENTAGE", "LIB_SERV", "LIB_GRAD", "ANNEE") AS                                                                                                                                     
  SELECT                                                                                                                                                                                                
    ROW_NUMBER() OVER (ORDER BY p.SEXE, s.LIB_SERV, g.LIB_GRAD, EXTRACT(YEAR FRO                                                                                                                        
M f.DATE_EFF)) AS ROW_ID,                                                                                                                                                                               
    p.SEXE,                                                                                                                                                                                             
    SUM(f.NB_EFFECTIF) AS NB_EMPLOYES,                                                                                                                                                                  
    ROUND(SUM(f.NB_EFFECTIF) * 100.0 / SUM(SUM(f.NB_EFFECTIF)) OVER(), 2) AS POU                                                                                                                        
RCENTAGE,                                                                                                                                                                                               
    s.LIB_SERV,                                                                                                                                                                                         
    g.LIB_GRAD,                                                                                                                                                                                         
    EXTRACT(YEAR FROM f.DATE_EFF) AS ANNEE                                                                                                                                                              
FROM FACT_EFFECTIF f                                                                                                                                                                                    
JOIN DIM_PERSONNEL p ON p.PERSONNEL_KEY = f.PERSONNEL_KEY                                                                                                                                               
LEFT JOIN DIM_SERVICE s ON s.SERVICE_KEY = f.SERVICE_KEY                                                                                                                                                
LEFT JOIN DIM_GRADE g ON g.GRADE_KEY = f.GRADE_KEY                                                                                                                                                      
GROUP BY p.SEXE, s.LIB_SERV, g.LIB_GRAD, EXTRACT(YEAR FROM f.DATE_EFF)                                                                                                                                  
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_EFFECTIF_SERVICE_GRADE" ("LIB_SERV",                                                                                                                        
 "LIB_GRAD", "ANNEE", "NB_EMPLOYES") AS                                                                                                                                                                 
  SELECT s.LIB_SERV,                                                                                                                                                                                    
       g.LIB_GRAD,                                                                                                                                                                                      
       EXTRACT(YEAR FROM f.DATE_EFF) AS ANNEE,                                                                                                                                                          
       COUNT(*) AS NB_EMPLOYES                                                                                                                                                                          
FROM FACT_EFFECTIF f                                                                                                                                                                                    
JOIN DIM_SERVICE s ON f.SERVICE_KEY = s.SERVICE_KEY                                                                                                                                                     
JOIN DIM_GRADE g ON f.GRADE_KEY = g.GRADE_KEY                                                                                                                                                           
GROUP BY s.LIB_SERV, g.LIB_GRAD,                                                                                                                                                                        
         EXTRACT(YEAR FROM f.DATE_EFF)                                                                                                                                                                  
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_ENCOURS_PAR_TYPE_PRET" ("ID_VUE", "L                                                                                                                        
IB_PRET", "TYP_PRET", "COD_GRP_PRET", "NB_PRETS", "ENCOURS_TOTAL", "MONTANT_MOYE                                                                                                                        
N", "LIB_SERV", "ANNEE") AS                                                                                                                                                                             
  SELECT                                                                                                                                                                                                
    tp.LIB_PRET || '_' || NVL(s.LIB_SERV, 'SANS_SERVICE') || '_2024' AS ID_VUE,                                                                                                                         
    tp.LIB_PRET,                                                                                                                                                                                        
    p.TYP_PRET,                                                                                                                                                                                         
    p.COD_GRP_PRET,                                                                                                                                                                                     
    COUNT(*) AS NB_PRETS,                                                                                                                                                                               
    SUM(p.MONTANT_CREDIT) AS ENCOURS_TOTAL,                                                                                                                                                             
    ROUND(AVG(p.MONTANT_CREDIT), 2) AS MONTANT_MOYEN,                                                                                                                                                   
    NVL(s.LIB_SERV, 'SANS_SERVICE') AS LIB_SERV,                                                                                                                                                        
    2024 AS ANNEE                                                                                                                                                                                       
FROM                                                                                                                                                                                                    
    GRH_DW.FAIT_CREDIT p                                                                                                                                                                                
    JOIN GRH_DW.DIM_TYPEPRET tp                                                                                                                                                                         
        ON p.TYP_PRET = tp.TYP_PRET                                                                                                                                                                     
        AND p.COD_GRP_PRET = tp.COD_GRP_PRET                                                                                                                                                            
        AND p.COD_SOC = tp.COD_SOC                                                                                                                                                                      
    LEFT JOIN GRH_DW.FACT_EFFECTIF fe                                                                                                                                                                   
        ON p.MAT_PERS = fe.PERSONNEL_KEY                                                                                                                                                                
    LEFT JOIN GRH_DW.DIM_SERVICE s                                                                                                                                                                      
        ON fe.SERVICE_KEY = s.SERVICE_KEY                                                                                                                                                               
GROUP BY                                                                                                                                                                                                
    tp.LIB_PRET,                                                                                                                                                                                        
    p.TYP_PRET,                                                                                                                                                                                         
    p.COD_GRP_PRET,                                                                                                                                                                                     
    NVL(s.LIB_SERV, 'SANS_SERVICE')                                                                                                                                                                     
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_EVOLUTION_MASSE_SALARIALE" ("ANNEE",                                                                                                                        
 "MASSE_SALARIALE", "MASSE_PREC", "VARIATION_PCT") AS                                                                                                                                                   
  WITH masse_annuelle AS (                                                                                                                                                                              
    SELECT                                                                                                                                                                                              
        EXTRACT(YEAR FROM DT_BUL) AS ANNEE,                                                                                                                                                             
        SUM(MONTANT_CHARGE) AS MASSE_SALARIALE                                                                                                                                                          
    FROM FAIT_CHARGESPERS                                                                                                                                                                               
    GROUP BY EXTRACT(YEAR FROM DT_BUL)                                                                                                                                                                  
)                                                                                                                                                                                                       
SELECT                                                                                                                                                                                                  
    m.ANNEE,                                                                                                                                                                                            
    m.MASSE_SALARIALE,                                                                                                                                                                                  
    LAG(m.MASSE_SALARIALE) OVER (ORDER BY m.ANNEE) AS MASSE_PREC,                                                                                                                                       
    ROUND(                                                                                                                                                                                              
        CASE                                                                                                                                                                                            
            WHEN LAG(m.MASSE_SALARIALE) OVER (ORDER BY m.ANNEE) = 0                                                                                                                                     
                 OR LAG(m.MASSE_SALARIALE) OVER (ORDER BY m.ANNEE) IS NULL                                                                                                                              
            THEN 0                                                                                                                                                                                      
            ELSE (m.MASSE_SALARIALE - LAG(m.MASSE_SALARIALE) OVER (ORDER BY m.AN                                                                                                                        
NEE))                                                                                                                                                                                                   
                 / LAG(m.MASSE_SALARIALE) OVER (ORDER BY m.ANNEE) * 100                                                                                                                                 
        END, 2                                                                                                                                                                                          
    ) AS VARIATION_PCT                                                                                                                                                                                  
FROM masse_annuelle m                                                                                                                                                                                   
ORDER BY m.ANNEE                                                                                                                                                                                        
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_MASSE_SALARIALE_MOIS" ("MOIS", "ANNE                                                                                                                        
E", "MOIS_NUM", "LIB_SERV", "MASSE_SALARIALE", "NB_EMPLOYES") AS                                                                                                                                        
  SELECT TRUNC(f.DT_BUL, 'MM') AS MOIS,                                                                                                                                                                 
       EXTRACT(YEAR FROM f.DT_BUL) AS ANNEE,                                                                                                                                                            
       EXTRACT(MONTH FROM f.DT_BUL) AS MOIS_NUM,                                                                                                                                                        
       s.LIB_SERV,                                                                                                                                                                                      
       SUM(f.MONTANT_CHARGE) AS MASSE_SALARIALE,                                                                                                                                                        
       COUNT(DISTINCT f.MAT_PERS) AS NB_EMPLOYES                                                                                                                                                        
FROM FAIT_CHARGESPERS f                                                                                                                                                                                 
JOIN DIM_PERSONNEL p ON f.MAT_PERS = p.MAT_PERS                                                                                                                                                         
JOIN DIM_SERVICE s ON p.COD_SERV = s.COD_SERV                                                                                                                                                           
GROUP BY TRUNC(f.DT_BUL, 'MM'),                                                                                                                                                                         
         EXTRACT(YEAR FROM f.DT_BUL),                                                                                                                                                                   
         EXTRACT(MONTH FROM f.DT_BUL),                                                                                                                                                                  
         s.LIB_SERV                                                                                                                                                                                     
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_MASSE_SALARIALE_SERVICE" ("LIB_SERV"                                                                                                                        
, "ANNEE", "MASSE_SALARIALE", "SALAIRE_MOYEN", "NB_EMPLOYES") AS                                                                                                                                        
  SELECT s.LIB_SERV,                                                                                                                                                                                    
       EXTRACT(YEAR FROM f.DT_BUL) AS ANNEE,                                                                                                                                                            
       SUM(f.MONTANT_CHARGE) AS MASSE_SALARIALE,                                                                                                                                                        
       AVG(f.MONTANT_CHARGE) AS SALAIRE_MOYEN,                                                                                                                                                          
       COUNT(DISTINCT f.MAT_PERS) AS NB_EMPLOYES                                                                                                                                                        
FROM FAIT_CHARGESPERS f                                                                                                                                                                                 
JOIN DIM_PERSONNEL p ON f.MAT_PERS = p.MAT_PERS                                                                                                                                                         
JOIN DIM_SERVICE s ON p.COD_SERV = s.COD_SERV                                                                                                                                                           
GROUP BY s.LIB_SERV,                                                                                                                                                                                    
         EXTRACT(YEAR FROM f.DT_BUL)                                                                                                                                                                    
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_PRETS_PAR_DUREE" ("LIB_SERV", "DUREE                                                                                                                        
_REMB", "ANNEE", "NB_PRETS", "MONTANT_MOYEN") AS                                                                                                                                                        
  SELECT                                                                                                                                                                                                
    s.LIB_SERV,                                                                                                                                                                                         
    fc.DUREE_REMB,                                                                                                                                                                                      
    EXTRACT(YEAR FROM pp.PRT_DAT_DEB)   AS ANNEE,                                                                                                                                                       
    COUNT(*)                            AS NB_PRETS,                                                                                                                                                    
    AVG(fc.MONTANT_CREDIT)              AS MONTANT_MOYEN                                                                                                                                                
FROM FAIT_CREDIT fc                                                                                                                                                                                     
JOIN DIM_PRETPERS  pp ON  fc.COD_SOC  = pp.COD_SOC                                                                                                                                                      
                      AND fc.MAT_PERS  = pp.MAT_PERS                                                                                                                                                    
                      AND fc.COD_PRET  = pp.COD_PRET                                                                                                                                                    
JOIN DIM_PERSONNEL p  ON  fc.MAT_PERS = p.MAT_PERS                                                                                                                                                      
JOIN DIM_SERVICE   s  ON  p.COD_SERV  = s.COD_SERV                                                                                                                                                      
GROUP BY                                                                                                                                                                                                
    s.LIB_SERV,                                                                                                                                                                                         
    fc.DUREE_REMB,                                                                                                                                                                                      
    EXTRACT(YEAR FROM pp.PRT_DAT_DEB)                                                                                                                                                                   
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_PRETS_PAR_OBJET" ("OBJET_PRET", "NB_                                                                                                                        
PRETS", "TOTAL_MONTANT", "TAUX_MOYEN", "DUREE_MOYENNE", "LIB_SERV", "ANNEE") AS                                                                                                                         
                                                                                                                                                                                                        
  SELECT                                                                                                                                                                                                
    dp.OBJET_PRET,                                                                                                                                                                                      
    COUNT(fc.ID_CREDIT) AS NB_PRETS,                                                                                                                                                                    
    ROUND(SUM(fc.MONTANT_CREDIT), 3) AS TOTAL_MONTANT,                                                                                                                                                  
    ROUND(AVG(fc.TAUX_INTERET), 2) AS TAUX_MOYEN,                                                                                                                                                       
    ROUND(AVG(fc.DUREE_REMB), 2) AS DUREE_MOYENNE,                                                                                                                                                      
    'Non sp‚cifi‚' AS LIB_SERV,                                                                                                                                                                         
    EXTRACT(YEAR FROM dp.PRT_DAT_DEB) AS ANNEE                                                                                                                                                          
FROM GRH_DW.FAIT_CREDIT fc                                                                                                                                                                              
INNER JOIN GRH_DW.DIM_PRETPERS dp                                                                                                                                                                       
    ON fc.COD_SOC = dp.COD_SOC                                                                                                                                                                          
    AND fc.MAT_PERS = dp.MAT_PERS                                                                                                                                                                       
    AND fc.COD_PRET = dp.COD_PRET                                                                                                                                                                       
WHERE dp.OBJET_PRET IS NOT NULL                                                                                                                                                                         
  AND TRIM(dp.OBJET_PRET) != ''                                                                                                                                                                         
GROUP BY                                                                                                                                                                                                
    dp.OBJET_PRET,                                                                                                                                                                                      
    EXTRACT(YEAR FROM dp.PRT_DAT_DEB)                                                                                                                                                                   
ORDER BY                                                                                                                                                                                                
    ANNEE DESC,                                                                                                                                                                                         
    NB_PRETS DESC                                                                                                                                                                                       
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_PRETS_PAR_SERVICE" ("LIB_SERV", "TYP                                                                                                                        
_PRET", "ANNEE", "NB_PRETS", "TOTAL_CREDIT", "MONTANT_MOYEN") AS                                                                                                                                        
  SELECT                                                                                                                                                                                                
    s.LIB_SERV,                                                                                                                                                                                         
    fc.TYP_PRET,                                                                                                                                                                                        
    EXTRACT(YEAR FROM pp.PRT_DAT_DEB)   AS ANNEE,                                                                                                                                                       
    COUNT(*)                            AS NB_PRETS,                                                                                                                                                    
    SUM(fc.MONTANT_CREDIT)              AS TOTAL_CREDIT,                                                                                                                                                
    AVG(fc.MONTANT_CREDIT)              AS MONTANT_MOYEN                                                                                                                                                
FROM FAIT_CREDIT fc                                                                                                                                                                                     
JOIN DIM_PRETPERS  pp ON  fc.COD_SOC  = pp.COD_SOC                                                                                                                                                      
                      AND fc.MAT_PERS  = pp.MAT_PERS                                                                                                                                                    
                      AND fc.COD_PRET  = pp.COD_PRET                                                                                                                                                    
JOIN DIM_PERSONNEL p  ON  fc.MAT_PERS = p.MAT_PERS                                                                                                                                                      
JOIN DIM_SERVICE   s  ON  p.COD_SERV  = s.COD_SERV                                                                                                                                                      
GROUP BY                                                                                                                                                                                                
    s.LIB_SERV,                                                                                                                                                                                         
    fc.TYP_PRET,                                                                                                                                                                                        
    EXTRACT(YEAR FROM pp.PRT_DAT_DEB)                                                                                                                                                                   
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_SALAIRE_PAR_GRADE" ("LIB_GRAD", "LIB                                                                                                                        
_SERV", "ANNEE", "SALAIRE_MOYEN", "SALAIRE_MIN", "SALAIRE_MAX", "TOTAL_CHARGES")                                                                                                                        
 AS                                                                                                                                                                                                     
  SELECT g.LIB_GRAD,                                                                                                                                                                                    
       s.LIB_SERV,                                                                                                                                                                                      
       EXTRACT(YEAR FROM f.DT_BUL) AS ANNEE,                                                                                                                                                            
       AVG(f.MONTANT_CHARGE) AS SALAIRE_MOYEN,                                                                                                                                                          
       MIN(f.MONTANT_CHARGE) AS SALAIRE_MIN,                                                                                                                                                            
       MAX(f.MONTANT_CHARGE) AS SALAIRE_MAX,                                                                                                                                                            
       SUM(f.MONTANT_CHARGE) AS TOTAL_CHARGES                                                                                                                                                           
FROM FAIT_CHARGESPERS f                                                                                                                                                                                 
JOIN DIM_PERSONNEL p ON f.MAT_PERS = p.MAT_PERS                                                                                                                                                         
JOIN DIM_GRADE g ON p.COD_GRAD = g.COD_GRAD                                                                                                                                                             
JOIN DIM_SERVICE s ON p.COD_SERV = s.COD_SERV                                                                                                                                                           
GROUP BY g.LIB_GRAD, s.LIB_SERV,                                                                                                                                                                        
         EXTRACT(YEAR FROM f.DT_BUL)                                                                                                                                                                    
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_SOLDE_CONGE_TYPE" ("TYP_CNG", "ANNEE                                                                                                                        
", "SOLDE_MOYEN", "INITIAL_MOYEN", "PRIS_MOYEN", "CUMUL_MOYEN") AS                                                                                                                                      
  SELECT TYP_CNG,                                                                                                                                                                                       
       ANNEE_CNG AS ANNEE,                                                                                                                                                                              
       AVG(SOLD_CNG) AS SOLDE_MOYEN,                                                                                                                                                                    
       AVG(INIT_CNG) AS INITIAL_MOYEN,                                                                                                                                                                  
       AVG(PRIS_CNG) AS PRIS_MOYEN,                                                                                                                                                                     
       AVG(CUM_CNG)  AS CUMUL_MOYEN                                                                                                                                                                     
FROM DIM_SOLDECONGE                                                                                                                                                                                     
GROUP BY TYP_CNG, ANNEE_CNG                                                                                                                                                                             
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_TAUX_CHARGE_SERVICE" ("LIB_SERV", "A                                                                                                                        
NNEE", "TAUX_MOYEN", "TAUX_MAX", "TAUX_MIN") AS                                                                                                                                                         
  SELECT s.LIB_SERV,                                                                                                                                                                                    
       EXTRACT(YEAR FROM f.DT_BUL) AS ANNEE,                                                                                                                                                            
       AVG(f.TAUX_CHARGE) AS TAUX_MOYEN,                                                                                                                                                                
       MAX(f.TAUX_CHARGE) AS TAUX_MAX,                                                                                                                                                                  
       MIN(f.TAUX_CHARGE) AS TAUX_MIN                                                                                                                                                                   
FROM FAIT_CHARGESPERS f                                                                                                                                                                                 
JOIN DIM_PERSONNEL p ON f.MAT_PERS = p.MAT_PERS                                                                                                                                                         
JOIN DIM_SERVICE s ON p.COD_SERV = s.COD_SERV                                                                                                                                                           
GROUP BY s.LIB_SERV,                                                                                                                                                                                    
         EXTRACT(YEAR FROM f.DT_BUL)                                                                                                                                                                    
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_TAUX_INTERET_PRET" ("LIB_PRET", "ANN                                                                                                                        
EE", "LIB_SERV", "TAUX_MOYEN", "TAUX_MIN", "TAUX_MAX", "TAUX_REFERENCE") AS                                                                                                                             
  SELECT                                                                                                                                                                                                
    tp.LIB_PRET,                                                                                                                                                                                        
    EXTRACT(YEAR FROM pp.PRT_DAT_DEB)   AS ANNEE,                                                                                                                                                       
    s.LIB_SERV,                                                                                                                                                                                         
    AVG(fc.TAUX_INTERET)                AS TAUX_MOYEN,                                                                                                                                                  
    MIN(fc.TAUX_INTERET)                AS TAUX_MIN,                                                                                                                                                    
    MAX(fc.TAUX_INTERET)                AS TAUX_MAX,                                                                                                                                                    
    AVG(tp.TAUX_INT)                    AS TAUX_REFERENCE                                                                                                                                               
FROM FAIT_CREDIT fc                                                                                                                                                                                     
JOIN DIM_PRETPERS  pp ON  fc.COD_SOC  = pp.COD_SOC                                                                                                                                                      
                      AND fc.MAT_PERS  = pp.MAT_PERS                                                                                                                                                    
                      AND fc.COD_PRET  = pp.COD_PRET                                                                                                                                                    
JOIN DIM_TYPEPRET  tp ON  fc.COD_SOC  = tp.COD_SOC                                                                                                                                                      
                      AND fc.TYP_PRET  = tp.TYP_PRET                                                                                                                                                    
                      AND fc.COD_GRP_PRET = tp.COD_GRP_PRET                                                                                                                                             
JOIN DIM_PERSONNEL p  ON  fc.MAT_PERS = p.MAT_PERS                                                                                                                                                      
JOIN DIM_SERVICE   s  ON  p.COD_SERV  = s.COD_SERV                                                                                                                                                      
GROUP BY                                                                                                                                                                                                
    tp.LIB_PRET,                                                                                                                                                                                        
    EXTRACT(YEAR FROM pp.PRT_DAT_DEB),                                                                                                                                                                  
    s.LIB_SERV                                                                                                                                                                                          
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_TOP_ABSENCES_EMPLOYE" ("MAT_PERS", "                                                                                                                        
NOM_PERS", "PREN_PERS", "LIB_SERV", "ANNEE", "TOTAL_JOURS_ABSENCE", "NB_DEMANDES                                                                                                                        
") AS                                                                                                                                                                                                   
  SELECT                                                                                                                                                                                                
    p.MAT_PERS,                                                                                                                                                                                         
    p.NOM_PERS,                                                                                                                                                                                         
    p.PREN_PERS,                                                                                                                                                                                        
    s.LIB_SERV,                                                                                                                                                                                         
    f.ANNEE_CNG            AS ANNEE,                                                                                                                                                                    
    SUM(f.NBR_JOURS)       AS TOTAL_JOURS_ABSENCE,                                                                                                                                                      
    COUNT(*)               AS NB_DEMANDES                                                                                                                                                               
FROM FAIT_ABSENCES f                                                                                                                                                                                    
JOIN DIM_PERSONNEL p ON f.MAT_PERS = p.MAT_PERS                                                                                                                                                         
JOIN DIM_SERVICE   s ON p.COD_SERV = s.COD_SERV                                                                                                                                                         
GROUP BY                                                                                                                                                                                                
    p.MAT_PERS,                                                                                                                                                                                         
    p.NOM_PERS,                                                                                                                                                                                         
    p.PREN_PERS,                                                                                                                                                                                        
    s.LIB_SERV,                                                                                                                                                                                         
    f.ANNEE_CNG                                                                                                                                                                                         
                                                                                                                                                                                                        
                                                                                                                                                                                                        
  CREATE OR REPLACE FORCE VIEW "GRH_DW"."VW_TOP_EMPRUNTEURS" ("MAT_PERS", "NOM_P                                                                                                                        
ERS", "PREN_PERS", "LIB_SERV", "NB_PRETS", "TOTAL_EMPRUNTE", "TAUX_MOYEN", "ANNE                                                                                                                        
E", "ENCOURS_TOTAL") AS                                                                                                                                                                                 
  SELECT                                                                                                                                                                                                
    p.MAT_PERS,                                                                                                                                                                                         
    p.NOM_PERS,                                                                                                                                                                                         
    p.PREN_PERS,                                  -- Correction : NOM EXACT selo                                                                                                                        
n votre image                                                                                                                                                                                           
    s.LIB_SERV,                                                                                                                                                                                         
    COUNT(fc.ID_CREDIT) AS NB_PRETS,                                                                                                                                                                    
    SUM(fc.MONTANT_CREDIT) AS TOTAL_EMPRUNTE,                                                                                                                                                           
    ROUND(AVG(fc.TAUX_INTERET), 2) AS TAUX_MOYEN,                                                                                                                                                       
    EXTRACT(YEAR FROM per.PRT_DAT_DEB) AS ANNEE,                                                                                                                                                        
    SUM(l.CAP_REST) AS ENCOURS_TOTAL                                                                                                                                                                    
FROM GRH_DW.FAIT_CREDIT fc                                                                                                                                                                              
JOIN GRH_DW.DIM_PRETPERS per                                                                                                                                                                            
    ON fc.COD_SOC = per.COD_SOC                                                                                                                                                                         
   AND fc.MAT_PERS = per.MAT_PERS                                                                                                                                                                       
   AND fc.COD_PRET = per.COD_PRET                                                                                                                                                                       
JOIN GRH_DW.DIM_LIGNEPRET l                                                                                                                                                                             
    ON fc.COD_SOC = l.COD_SOC                                                                                                                                                                           
   AND fc.MAT_PERS = l.MAT_PERS                                                                                                                                                                         
   AND fc.COD_PRET = l.COD_PRET                                                                                                                                                                         
   AND fc.L_PRET = l.L_PRET                                                                                                                                                                             
-- Jointure corrig‚e sur la table personnel                                                                                                                                                             
JOIN GRH_DW.DIM_PERSONNEL p                                                                                                                                                                             
    ON fc.MAT_PERS = p.MAT_PERS                                                                                                                                                                         
LEFT JOIN GRH_DW.FACT_EFFECTIF fe                                                                                                                                                                       
    ON p.PERSONNEL_KEY = fe.PERSONNEL_KEY         -- Utilisation de la cl‚ techn                                                                                                                        
ique pour plus de fiabilit‚                                                                                                                                                                             
LEFT JOIN GRH_DW.DIM_SERVICE s                                                                                                                                                                          
    ON fe.SERVICE_KEY = s.SERVICE_KEY                                                                                                                                                                   
GROUP BY                                                                                                                                                                                                
    p.MAT_PERS,                                                                                                                                                                                         
    p.NOM_PERS,                                                                                                                                                                                         
    p.PREN_PERS,                                  -- Mis … jour ici aussi                                                                                                                               
    s.LIB_SERV,                                                                                                                                                                                         
    EXTRACT(YEAR FROM per.PRT_DAT_DEB)                                                                                                                                                                  
ORDER BY TOTAL_EMPRUNTE DESC                                                                                                                                                                            
                                                                                                                                                                                                        

26 rows selected.

SQL> 
SQL> SPOOL OFF

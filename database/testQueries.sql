/*SELECT Year, startAvgTemp - endAvgTemp AS AvgTemp, CityName
    FROM CityTempObservation
    WHERE CityName IN (SELECT Year, AvgTemp AS startAvgTemp, CityName
            FROM CityTempObservation
            WHERE Year = 2000
            AND CountryName = "Australia"*/
SELECT DISTINCT Year 
    FROM CityTempObservation;
    
SELECT DISTINCT Year 
    FROM StateTempObservation;
    

SELECT Year, AvgTemp, StateName
    FROM StateTempObservation
    WHERE Year = 1960
    AND Country = "Canada";
    
SELECT Year, AvgTemp, StateName
    FROM StateTempObservation
    WHERE Year = 2013
    AND Country = "Canada";

SELECT Year, MinTemp, CityName
    FROM CityTempObservation
    WHERE Year = 2013
    AND CountryName = "Australia";
    
SELECT Year, MinTemp, CityName
    FROM CityTempObservation
    WHERE Year = 2013
    AND CountryName = "Australia";
    
SELECT DISTINCT Year 
    FROM CountryTempObservation c 
    JOIN CityTempObservation t ON c.Year = t.Year 
    WHERE t.CountryName = 'Albania'
    ORDER BY t.Year ASC;
    
SELECT DISTINCT Year 
    FROM StateTempObservation
    WHERE StateName = "Acre"
    ORDER BY Year ASC;

--    
--
--DEEP_DIVE1 DATA TESTING
--
--    
INSERT INTO DeepDive1UserInput (regionInput, countryInput, stateInput, cityInput, yearInput, timePeriodInput) VALUES ("global", "null", "null", "null", "1999", "12");
INSERT INTO DeepDive1UserInput (regionInput, countryInput, stateInput, cityInput, yearInput, timePeriodInput) VALUES ("country", "Australia", "null", "null", "1999", "12");

UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='DeepDive1UserInput';
DELETE FROM DeepDive1UserInput;

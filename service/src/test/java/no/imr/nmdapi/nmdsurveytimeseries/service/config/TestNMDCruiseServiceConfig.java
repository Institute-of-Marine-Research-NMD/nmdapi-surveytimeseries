package no.imr.nmdapi.nmdsurveytimeseries.service.config;

import no.imr.nmdapi.surveytimeseries.service.config.NMDSurveyTimeSeriesServiceConfig;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author kjetilf
 */
public class TestNMDCruiseServiceConfig {

    private NMDSurveyTimeSeriesServiceConfig config = new NMDSurveyTimeSeriesServiceConfig();

    @Test
    public void testGetNMDCruiseService() {
        assertNotNull(config.getNMDCruiseService());
    }

}

package no.imr.nmdapi.surveytimeseries.service.config;

import no.imr.nmdapi.surveytimeseries.service.NMDSurveyTimeSeriesService;
import no.imr.nmdapi.surveytimeseries.service.NMDSurveyTimeSeriesServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This contains all configuration for the reference services.
 *
 * @author kjetilf
 */
@Configuration
public class NMDSurveyTimeSeriesServiceConfig {

    /**
     * Creates the service implementation.
     *
     * @return  A reference service implementation.
     */
    @Bean(name="nmdCruiseService")
    public NMDSurveyTimeSeriesService getNMDCruiseService() {
        return new NMDSurveyTimeSeriesServiceImpl();
    }

}

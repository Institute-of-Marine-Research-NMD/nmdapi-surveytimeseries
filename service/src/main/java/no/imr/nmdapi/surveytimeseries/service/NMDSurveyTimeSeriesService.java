package no.imr.nmdapi.surveytimeseries.service;

import no.imr.nmd.commons.dataset.jaxb.DatasetType;
import no.imr.nmd.commons.dataset.jaxb.DatasetsType;
import no.imr.nmd.commons.surveytimeseries.jaxb.SurveyTimeSeriesType;
import no.imr.nmdapi.generic.response.v1.ListElementType;
import org.springframework.core.io.Resource;

/**
 * Service API for mission data.
 *
 * @author kjetilf
 */
public interface NMDSurveyTimeSeriesService {

    /**
     * Get .
     *
     * @param name
     * @return              Survey time series data.
     */
    Object getData(String name);

    /**
     * Delete
     *
     * @param name
     */
    void deleteData(String name);

    /**
     * Update
     *
     * @param name
     * @param seriesType   Survey time series data.
     */
    void updateData(String name, SurveyTimeSeriesType seriesType);

    /**
     * Insert
     *
     * @param name
     */
    void insertData(String name, SurveyTimeSeriesType seriesType);

    /**
     *
     * @param name
     * @return
     */
    boolean hasData(String name);

    /**
     *
     * @return
     */
    ListElementType list();

    /**
     *
     * @return
     */
    DatasetsType listDatasets();

    /**
     *
     * @param dataset
     */
    void updateDatasets(DatasetType dataset);

    /**
     *
     * @param name
     * @return
     */
    Object getInfo(String name);

    /**
     *
     * @param name
     * @param type
     * @param sampleTime
     * @return
     */
    Resource getDataBySurveySampleTime(String name, String type, String sampleTime);

}

package no.imr.nmdapi.surveytimeseries.controller;

import javax.servlet.http.HttpServletResponse;
import no.imr.framework.logging.slf4j.aspects.stereotype.PerformanceLogging;
import no.imr.nmd.commons.dataset.jaxb.DatasetType;
import no.imr.nmd.commons.dataset.jaxb.DatasetsType;
import no.imr.nmd.commons.surveytimeseries.jaxb.SurveyTimeSeriesType;
import no.imr.nmdapi.exceptions.NotFoundException;
import no.imr.nmdapi.generic.response.v1.ListElementType;
import no.imr.nmdapi.surveytimeseries.service.NMDSurveyTimeSeriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller object for mission requests.
 *
 * @author kjetilf
 */
@Controller
public class SurveyTimeSeriesController {

    /**
     * Url part that defines it as mission.
     */
    public static final String SurveyTimeSeries_URL = "/surveytimeseries";

    /**
     * Class logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SurveyTimeSeriesController.class);

    /**
     * Service layer object for nmd mission queries.
     */
    @Autowired
    private NMDSurveyTimeSeriesService seriesService;

    /**
     * Get data for mission.
     *
     * @param name
     * @return Response object.
     */
    @PerformanceLogging
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object find(@PathVariable(value = "name") String name) {
        LOGGER.info("Start SurveyTimeSeriesController.find");
        return seriesService.getData(name);
    }

    /**
     * Does the mission have data
     *
     * @param httpServletResponse
     * @param name
     */
    @PerformanceLogging
    @RequestMapping(value = "/{name}", method = RequestMethod.HEAD)
    @ResponseBody
    public void hasData(HttpServletResponse httpServletResponse, @PathVariable(value = "name") String name) {
        LOGGER.info("Start SurveyTimeSeriesController.hasData");
        if (seriesService.hasData(name)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Delete biotic data for mission.
     *
     * @param name
     */
    @PerformanceLogging
    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void delete(@PathVariable(value = "name") String name) {
        LOGGER.info("Start SurveyTimeSeriesController.delete");
        seriesService.deleteData(name);
    }

    /**
     * Insert mission data for mission.
     *
     * @param name
     * @param data
     */
    @PerformanceLogging
    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void insert(@PathVariable(value = "name") String name, @RequestBody SurveyTimeSeriesType data) {
        LOGGER.info("Start SurveyTimeSeriesController.insert");
        seriesService.insertData(name, data);
    }

    /**
     * Update mission data for mission.
     *
     * @param name
     * @param data
     */
    @PerformanceLogging
    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void update(@PathVariable(value = "name") String name, @RequestBody SurveyTimeSeriesType data) {
        LOGGER.info("Start SurveyTimeSeriesController.update");
        seriesService.updateData(name, data);
    }

    /**
     * List all cruiseseries.
     *
     * @return
     */
    @PerformanceLogging
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ListElementType list() {
        LOGGER.info("Start SurveyTimeSeriesController.list");
        return seriesService.list();
    }

    /**
     * List all dataset information.
     *
     * @return
     */
    @PerformanceLogging
    @RequestMapping(params = "dataset", value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DatasetsType listDatasets() {
        LOGGER.info("Start SurveyTimeSeriesController.listDatasets");
        return seriesService.listDatasets();
    }

    /**
     * Update dataset information.
     *
     * @param dataset
     */
    @PerformanceLogging
    @RequestMapping(params = "dataset", value = "/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateDatasetInfo(@RequestBody DatasetType dataset) {
        LOGGER.info("Start SurveyTimeSeriesController.updateDatasetInfo");
        seriesService.updateDatasets(dataset);
    }

    /**
     * Get data.
     *
     * @param name
     * @return
     */
    @PerformanceLogging
    @RequestMapping(value = "/{name}", method = RequestMethod.GET, params = {"type=info"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object findInfo(@PathVariable(value = "name") String name) {
        LOGGER.info("Start SurveyTimeSeriesController.findInfo");
        return seriesService.getInfo(name);
    }

    /**
     * Get sam.
     *
     * @param name
     * @param type
     * @param sampleTime
     * @param response
     * @return
     */
    @PerformanceLogging
    @RequestMapping(value = "/{name}/{type}/{sampleTime}", method = RequestMethod.GET, produces = "application/zip")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object getZipForSurveySampleTime(@PathVariable(value = "name") String name, @PathVariable(value = "type") String type, @PathVariable(value = "sampleTime") String sampleTime, HttpServletResponse response) {
        LOGGER.info("Start SurveyTimeSeriesController.findInfo");
        Resource resource = seriesService.getDataBySurveySampleTime(name, type, sampleTime);
        if (resource.exists()) {
            StringBuilder builder = new StringBuilder("attachment; filename=");
            builder.append(name).append("_").append(type).append("_").append(sampleTime).append(".zip");
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", builder.toString());
            return resource;
        } else {
            throw new NotFoundException(resource.getFilename().concat(" was not found."));
        }
    }

}

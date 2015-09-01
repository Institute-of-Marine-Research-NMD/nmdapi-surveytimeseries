package no.imr.nmdapi.surveytimeseries.service;

import java.util.List;
import no.imr.nmd.commons.surveytimeseries.jaxb.SurveyTimeSeriesType;
import no.imr.nmdapi.dao.file.NMDSeriesReferenceDao;
import no.imr.nmdapi.exceptions.BadRequestException;
import no.imr.nmdapi.generic.response.v1.ListElementType;
import no.imr.nmdapi.generic.response.v1.ResultElementType;
import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Survey time series implementation.
 *
 * @author kjetilf
 */
public class NMDSurveyTimeSeriesServiceImpl implements NMDSurveyTimeSeriesService {

    /**
     * Data type.
     */
    private static final String TYPE = "surveytimeseries";

    @Autowired
    private NMDSeriesReferenceDao seriesReferenceDao;

    @Autowired
    private Configuration configuration;

    @Override
    public Object getData(final String name) {
        return seriesReferenceDao.get(name, SurveyTimeSeriesType.class.getPackage().getName());
    }

    @Override
    public void deleteData(final String name) {
        seriesReferenceDao.delete(TYPE, name, true);
    }

   @Override
    public void insertData(final String name, final SurveyTimeSeriesType surveyTimeSeriesType) {
        if (!name.equalsIgnoreCase(surveyTimeSeriesType.getSurveytimeseriesname())) {
            throw new BadRequestException("Cruiseserie name is not equal to value in the data.");
        }
        String readRole = configuration.getString("default.readrole");
        String writeRole = configuration.getString("default.writerole");
        String owner = configuration.getString("default.owner");
        seriesReferenceDao.insert(writeRole, readRole, owner, TYPE, name, surveyTimeSeriesType, true);
    }

    @Override
    public void updateData(final String name, final SurveyTimeSeriesType surveyTimeSeriesType) {
        seriesReferenceDao.update(name, surveyTimeSeriesType);
    }

    @Override
    public ListElementType list() {
        List<String> names = seriesReferenceDao.list();
        ListElementType elementType = new ListElementType();
        for (String name : names) {
            ResultElementType resultElementType = new ResultElementType();
            resultElementType.setResult(name);
            elementType.getElement().add(resultElementType);
        }
        return elementType;
    }

    @Override
    public boolean hasData(String name) {
        return seriesReferenceDao.hasData(name);
    }

}

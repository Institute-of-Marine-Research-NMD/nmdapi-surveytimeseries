package no.imr.nmdapi.surveytimeseries.service;

import java.util.List;
import no.imr.nmd.commons.surveytimeseries.jaxb.SurveyTimeSeriesType;
import no.imr.nmdapi.dao.file.NMDDataDao;
import no.imr.nmdapi.generic.response.v1.ListElementType;
import no.imr.nmdapi.generic.response.v1.ResultElementType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Survey time series implementation.
 *
 * @author kjetilf
 */
public class NMDSurveyTimeSeriesServiceImpl implements NMDSurveyTimeSeriesService {

    @Autowired
    private NMDDataDao nmdDataDao;

    @Override
    public Object getData(final String name) {
        return nmdDataDao.get(name, SurveyTimeSeriesType.class);
    }

    @Override
    public void deleteData(final String name) {
        nmdDataDao.delete(name);
    }

   @Override
    public void insertData(final String name, final SurveyTimeSeriesType seriesType) {
        nmdDataDao.insert(name, seriesType, SurveyTimeSeriesType.class);
    }


    @Override
    public void updateData(final String name, final SurveyTimeSeriesType seriesType) {
        nmdDataDao.update(name, seriesType, SurveyTimeSeriesType.class);
    }

    @Override
    public boolean hasData(String name) {
        return nmdDataDao.hasData(name);
    }

    @Override
    public ListElementType list() {
        List<String> names = nmdDataDao.listSeries();
        ListElementType elementType = new ListElementType();
        for (String name : names) {
            ResultElementType resultElementType = new ResultElementType();
            resultElementType.setResult(name);
            elementType.getElement().add(resultElementType);
        }
        return elementType;
    }

}

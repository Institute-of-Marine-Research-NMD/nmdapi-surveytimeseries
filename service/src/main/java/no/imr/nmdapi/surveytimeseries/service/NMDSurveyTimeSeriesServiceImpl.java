package no.imr.nmdapi.surveytimeseries.service;

import java.util.List;
import no.imr.nmd.commons.dataset.jaxb.DataTypeEnum;
import no.imr.nmd.commons.dataset.jaxb.DatasetType;
import no.imr.nmd.commons.dataset.jaxb.DatasetsType;
import no.imr.nmd.commons.surveytimeseries.jaxb.SurveyTimeSeriesType;
import no.imr.nmdapi.dao.file.NMDSeriesReferenceDao;
import no.imr.nmdapi.exceptions.BadRequestException;
import no.imr.nmdapi.generic.response.v1.ListElementType;
import no.imr.nmdapi.generic.response.v1.OptionKeyValueListType;
import no.imr.nmdapi.generic.response.v1.OptionKeyValueType;
import no.imr.nmdapi.generic.response.v1.ResultElementType;
import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Survey time series implementation.
 *
 * @author kjetilf
 */
public class NMDSurveyTimeSeriesServiceImpl implements NMDSurveyTimeSeriesService {

    @Autowired
    private NMDSeriesReferenceDao seriesReferenceDao;

    @Autowired
    private Configuration configuration;

    @Override
    public Object getData(final String name) {
        return seriesReferenceDao.get(name);
    }

    @Override
    public void deleteData(final String name) {
        seriesReferenceDao.delete(DataTypeEnum.SURVEYTIMESERIES, name, true);
    }

    @Override
    public void insertData(final String name, final SurveyTimeSeriesType surveyTimeSeriesType) {
        if (!name.equalsIgnoreCase(surveyTimeSeriesType.getSurveytimeseriesname())) {
            throw new BadRequestException("Cruiseserie name is not equal to value in the data.");
        }
        String readRole = configuration.getString("default.readrole");
        String writeRole = configuration.getString("default.writerole");
        String owner = configuration.getString("default.owner");
        seriesReferenceDao.insert(writeRole, readRole, owner, DataTypeEnum.SURVEYTIMESERIES, name, surveyTimeSeriesType, true);
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

    @Override
    public DatasetsType listDatasets() {
        return seriesReferenceDao.getDatasets();
    }

    @Override
    public void updateDatasets(DatasetType datasetType) {
        seriesReferenceDao.updateDataset(datasetType);
    }

    @Override
    public Object getInfo(String name) {
        String format = seriesReferenceDao.getRootNamespace(name);
        long checksum = seriesReferenceDao.getChecksum(name);
        long lastModified = seriesReferenceDao.getLastModified(name);
        OptionKeyValueListType keyValueListType = new OptionKeyValueListType();
        keyValueListType.getElement().add(getOptionKeyValueType("format", format));
        keyValueListType.getElement().add(getOptionKeyValueType("checksum", String.valueOf(checksum)));
        keyValueListType.getElement().add(getOptionKeyValueType("lastModified", String.valueOf(lastModified)));
        return keyValueListType;
    }

    private OptionKeyValueType getOptionKeyValueType(String key, String value) {
        OptionKeyValueType formatType = new OptionKeyValueType();
        formatType.setKey(key);
        formatType.setValue(value);
        return formatType;
    }

}

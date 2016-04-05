package no.imr.nmdapi.surveytimeseries.converters.mapper;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 *
 * @author kjetilf
 */
public class SurveyTimeSeriesNamespacePrefixMapper extends NamespacePrefixMapper {

    public static final String BSURVEYTIMESERIES_NS = "http://www.imr.no/formats/surveytimeseries/v1";

    @Override
    public String getPreferredPrefix(String namespaceUri,
                               String suggestion,
                               boolean requirePrefix) {
        return "";
    }

}

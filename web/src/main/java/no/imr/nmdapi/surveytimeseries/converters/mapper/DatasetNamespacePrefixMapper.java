package no.imr.nmdapi.surveytimeseries.converters.mapper;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 *
 * @author kjetilf
 */
public class DatasetNamespacePrefixMapper extends NamespacePrefixMapper {

    public static final String BIOTIC_NS = "http://www.imr.no/formats/nmddataset/v1";

    @Override
    public String getPreferredPrefix(String namespaceUri,
                               String suggestion,
                               boolean requirePrefix) {
        return "";
    }

}

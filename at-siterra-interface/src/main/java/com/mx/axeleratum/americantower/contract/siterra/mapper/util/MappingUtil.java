package com.mx.axeleratum.americantower.contract.siterra.mapper.util;



import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

public class MappingUtil {

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface SiteNumber {
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public static @interface CurrentRentalAmountUOM {
    }

    @SiteNumber
    public String siteNumber(Map<String, String> in) {
        return  in.get("Site Number");
    }

    @CurrentRentalAmountUOM
    public String currentRentalAmountUOM(Map<String, String> in) {


        return  in.get("Current Rental Amount UOM");
    }
}

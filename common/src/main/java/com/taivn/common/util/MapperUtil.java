/**

 *                                                                                
 * Description: The file class                                                 
 *                                                                                
 * Change history:                                                                
 * Date             Defect#             Person             Comments               
 * -------------------------------------------------------------------------------
 * Sep 19, 2022     ********           Taivn            Initialize                  
 *                                                                                
 */
package com.taivn.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class MapperUtils.
 *
 * @author Taivn
 */
public final class MapperUtil {

    /**
     * Instantiates a new mapper utils.
     */
    private MapperUtil() {
        // do nothing.
    }

    /**
     * Object to map.
     *
     * @param obj the object
     * @return the map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> objectToMap(Object obj) {
        ObjectMapper oMapper = new ObjectMapper();

        if (obj != null) {
            return oMapper.convertValue(obj, Map.class);
        }

        return new HashMap<>();
    }

    /**
     * To json.
     *
     * @param obj the obj
     * @return the string
     * @throws JsonProcessingException the json processing exception
     */
    public static String toJson(Object obj) throws JsonProcessingException {
        // Java object to JSON string
        return new ObjectMapper().writeValueAsString(obj);
    }
}

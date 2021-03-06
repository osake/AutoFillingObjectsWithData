package com.afowd.util;

import java.util.ArrayList;
import java.util.Iterator;

import com.afowd.range.filter.RangeNumberFilter;
import com.afowd.sort.ClassPropertyComparator;


public class RangeFilterUtil {
	/**
	 * checks if object value is in range filters
	 * @param alData
	 * @param rangeFilter
	 * @return
	 */
	public static ArrayList<Object> filterRangeDataNumbers (ArrayList<Object> alData, RangeNumberFilter rangeFilter){
		if (alData != null && !alData.isEmpty() && rangeFilter != null && rangeFilter.getClass() != null && rangeFilter.getProperty() != null){
			ArrayList<Object> alFilteredData = new ArrayList<Object>();
			for (Iterator<Object> iterator = alData.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				if (object != null){
					Object oValue = ClassPropertyComparator.getValue(object, rangeFilter.getNameOfTheClass(), rangeFilter.getProperty());
					if (rangeFilter.getAllowNull() && oValue == null){
						alFilteredData.add(object);
						continue;
					} 
					if (oValue != null && (oValue instanceof Integer || oValue instanceof Long || oValue instanceof Short)){
						if (inRangeInteger(oValue, rangeFilter)){
							alFilteredData.add(object);
						}
					} 
				}
			}
			return alFilteredData;
		} else {
			return alData;
		}
	}
	/**
	 * checks if object value is in range filter
	 * @param oValue
	 * @param rangeFilterNumber
	 * @return
	 */
	private static boolean inRangeInteger(Object oValue, RangeNumberFilter rangeFilterNumber) {
		Long lValue = new Long(oValue.toString());
		Long lFilterFrom = rangeFilterNumber.getRangeFrom();
		Long lFilterTo = rangeFilterNumber.getRangeTo();
		boolean bInRange = rangeFilterNumber.getIsInRange();
		boolean bAllowNull = rangeFilterNumber.getAllowNull();
		if ((lFilterFrom != null ? ( lValue != null ? ( bInRange ? lValue.longValue() >= lFilterFrom.longValue() : lValue.longValue() <= lFilterFrom.longValue()) : bAllowNull ) : true)){
			if ((lFilterTo != null ? ( lValue != null ? ( bInRange ? lValue.longValue() <= lFilterTo.longValue() : lValue.longValue() >= lFilterTo.longValue()) : bAllowNull ) : true)){
				return true;
			}
		}
		return false;
	}
	/**
	 * checks if object value is in range filters
	 * @param alData
	 * @param alRangeFilter
	 * @return
	 */
	public static ArrayList<Object> filterRangeDataNumbers(ArrayList<Object> alData, ArrayList<RangeNumberFilter> alRangeFilter) {
		if (alData != null && !alData.isEmpty() && alRangeFilter != null && !alRangeFilter.isEmpty()){
			ArrayList<Object> alFilteredData = new ArrayList<Object>();
			for (Iterator<Object> iterator = alData.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				boolean bIsInRange = true;
				for (Iterator<RangeNumberFilter> iterator2 = alRangeFilter.iterator(); iterator2.hasNext();) {
					RangeNumberFilter rangeNumberFilter = (RangeNumberFilter) iterator2.next();
					if (rangeNumberFilter.getClass() != null && rangeNumberFilter.getProperty() != null){
						Object oValue = ClassPropertyComparator.getValue(object, rangeNumberFilter.getNameOfTheClass(), rangeNumberFilter.getProperty());
						if (!rangeNumberFilter.getAllowNull() && oValue == null){
							bIsInRange = false;
							break;
						}
						if ( oValue != null && (oValue instanceof Integer || oValue instanceof Long || oValue instanceof Short)){
							if (!inRangeInteger(oValue, rangeNumberFilter)){
								bIsInRange = false;
								break;
							}
						}
					}
				}
				if (bIsInRange){
					alFilteredData.add(object);
				}
			}
			return alFilteredData;
		}else {
			return alData;
		}
	}
}

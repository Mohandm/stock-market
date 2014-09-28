package com.misys.stockmarket.mbeans.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseFormBean implements FormBean {

	private Map<String, Object> componentDefaultDataMap = new HashMap<String, Object>();

	private List<String> changedFields = new ArrayList<String>();
	
	private Map<String, String> objectdata = new HashMap<String, String>();

	public void setComponentDefaultDataMap(
			Map<String, Object> componentDefaultDataMap) {
		this.componentDefaultDataMap = componentDefaultDataMap;
	}

	public Map<String, Object> getComponentDefaultDataMap() {
		return componentDefaultDataMap;
	}

	
	public List<String> getChangedFields() {
		return changedFields;
	}

	public void setChangedFields(List<String> changedFields) {
		this.changedFields = changedFields;
	}

	public Map<String, String> getObjectdata() {
		return objectdata;
	}

	public void setObjectdata(Map<String, String> objectdata) {
		this.objectdata = objectdata;
	}

	public BaseFormBean() {
	}

}
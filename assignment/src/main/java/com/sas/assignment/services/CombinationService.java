package com.sas.assignment.services;

import java.util.List;
import java.util.Map;

import com.sas.assignment.bean.MenuItem;

public interface CombinationService {
	public Map<String, List<MenuItem>> findBestCombination();
}

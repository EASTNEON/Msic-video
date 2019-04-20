package com.Msic.mapper;

import java.util.List;

import com.Msic.pojo.SearchRecords;
import com.Msic.utils.MyMapper;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
	
	public List<String> getHotwords();

}
package com.amountaggregator.service;

import com.amountaggregator.api.schema.types.AmountData;
import com.amountaggregator.data.AmountDataAggregationData;

public interface AmountAggregatorService {
	public void loadAmountData(AmountData amountData);
	public AmountDataAggregationData getCurrentAggregationData();
	
}

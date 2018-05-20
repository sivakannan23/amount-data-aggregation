package com.amountaggregator.aggregation.engine;

import com.amountaggregator.api.schema.types.AmountData;
import com.amountaggregator.data.AmountDataAggregationData;

public interface TimeBasedAmountAggregateService {
	public void removeOldData();

	public void doAggregation(AmountData amountData);

	public AmountDataAggregationData getCurrentAggregationData();

}

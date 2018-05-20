package com.amountaggregator.api.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import static com.amountaggregator.Constants.AGGREGATION_TIMEFRAME_MILLIES;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amountaggregator.api.schema.types.AggregationData;
import com.amountaggregator.api.schema.types.AmountData;
import com.amountaggregator.data.AmountDataAggregationData;
import com.amountaggregator.service.AmountAggregatorService;

@RestController
public class AmountDataController {

	/*
	 * This variable is not thread safe. Since, O(N) of loadAmountData is 1, making the
	 * variable threadlocal won't make any significant difference
	 */
	private long timeBeforeAggTimeFrame;

	@Autowired
	private AmountAggregatorService amountAggregatorService;

	@RequestMapping(value = "/transactions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> loadAmountData(@RequestBody AmountData amountData) {
		setTimeBeforeAggTimeFrame();
		if (amountData.getTimestamp() < this.timeBeforeAggTimeFrame) {
			return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
		}
		amountAggregatorService.loadAmountData(amountData);
		return new ResponseEntity<String>("", HttpStatus.CREATED);
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AggregationData> getAmountAggregationData() {
		
		return new ResponseEntity<AggregationData>(convertAggregationData(amountAggregatorService.getCurrentAggregationData()), HttpStatus.OK);
	}

	private void setTimeBeforeAggTimeFrame() {
		ZonedDateTime utcNow = ZonedDateTime.now(ZoneOffset.UTC);
		long timeInMillis = utcNow.toInstant().toEpochMilli();
		
		this.timeBeforeAggTimeFrame =  timeInMillis - AGGREGATION_TIMEFRAME_MILLIES;
	}
	
	private AggregationData convertAggregationData(AmountDataAggregationData amountDataAggregationData)
	{
		AggregationData aggregationData = new AggregationData();
		aggregationData.setAvg(amountDataAggregationData.getAvg());
		aggregationData.setSum(amountDataAggregationData.getSum());
		aggregationData.setCount(amountDataAggregationData.getCount());
		aggregationData.setMax(amountDataAggregationData.getMax());
		aggregationData.setMin(amountDataAggregationData.getMin());
		return aggregationData;
	}
}

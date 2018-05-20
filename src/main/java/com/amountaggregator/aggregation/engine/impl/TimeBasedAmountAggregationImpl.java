package com.amountaggregator.aggregation.engine.impl;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import static com.amountaggregator.Constants.*; 

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.amountaggregator.aggregation.engine.TimeBasedAmountAggregateService;
import com.amountaggregator.api.schema.types.AmountData;
import com.amountaggregator.data.AmountDataAggregationData;

@Service
public class TimeBasedAmountAggregationImpl implements TimeBasedAmountAggregateService{

	private PriorityQueue<AmountData> amountDataQueue = new PriorityQueue<>(
			(amountData1, amountData2) -> amountData1.getTimestamp().compareTo(amountData2.getTimestamp()));

	private SortedSet<AmountData> ascSortedRecords = Collections.synchronizedSortedSet(
			new TreeSet<>((amountData1, amountData2) -> amountData1.getAmount().compareTo(amountData2.getAmount())));
	private SortedSet<AmountData> dscSortedRecords = Collections.synchronizedSortedSet(
			new TreeSet<>((amountData1, amountData2) -> amountData2.getAmount().compareTo(amountData1.getAmount())));

	

	private long timeBeforeAggTimeFrame;

	private volatile double aggregationAmountTotal = 0.0;
	private volatile double aggregationAmountAvg = 0.0;

	private void setTimeBeforeAggTimeFrame() {
		ZonedDateTime utcNow = ZonedDateTime.now(ZoneOffset.UTC);
		long timeInMillis = utcNow.toInstant().toEpochMilli();
		this.timeBeforeAggTimeFrame = timeInMillis - AGGREGATION_TIMEFRAME_MILLIES;
	}

	public synchronized void removeOldData() {
		AmountData leastAmountData = amountDataQueue.peek();
		setTimeBeforeAggTimeFrame();
		
		while (leastAmountData != null && leastAmountData.getTimestamp() < this.timeBeforeAggTimeFrame) {

			this.aggregationAmountTotal = this.aggregationAmountTotal - leastAmountData.getAmount();
			this.aggregationAmountAvg = this.aggregationAmountTotal / (this.amountDataQueue.size() - 1);
			ascSortedRecords.remove(leastAmountData);
			dscSortedRecords.remove(leastAmountData);
			amountDataQueue.poll();
			leastAmountData = amountDataQueue.peek();
		}
	}

	private void updateMinAggregationData(AmountData amountData) {
		boolean isNewDataFitForInsert = ascSortedRecords.stream()
				.anyMatch(data -> amountData.getAmount() < data.getAmount());
		long currentMinBucketSize = ascSortedRecords.size();

		if (currentMinBucketSize < MIN_AGGREGATION_BUCKET_SIZE || isNewDataFitForInsert) {
			ascSortedRecords.add(amountData);
		}

		if (currentMinBucketSize >= MIN_AGGREGATION_BUCKET_SIZE) {
			ascSortedRecords.remove(ascSortedRecords.last());
		}
	}

	private void updateMaxAggregationData(AmountData amountData) {
		boolean isNewDataFitForInsert = dscSortedRecords.stream()
				.anyMatch(data -> amountData.getAmount() < data.getAmount());
		long currentMaxBucketSize = dscSortedRecords.size();

		if (currentMaxBucketSize < MAX_AGGREGATION_BUCKET_SIZE || isNewDataFitForInsert) {
			dscSortedRecords.add(amountData);
		}

		if (currentMaxBucketSize >= MAX_AGGREGATION_BUCKET_SIZE) {
			dscSortedRecords.remove(dscSortedRecords.last());
		}
	}

	private void updateAggregationTotal(AmountData amountData) {
		this.aggregationAmountTotal = this.aggregationAmountTotal + amountData.getAmount();
	}

	private void updateAggregationAvg(AmountData amountData) {
		this.aggregationAmountAvg = this.aggregationAmountTotal / amountDataQueue.size();
	}

	public synchronized void doAggregation(AmountData amountData) {
		if (amountData.getTimestamp() < this.timeBeforeAggTimeFrame) {
			return;
		}
		amountDataQueue.offer(amountData);
		updateMinAggregationData(amountData);
		updateMaxAggregationData(amountData);
		updateAggregationTotal(amountData);
		updateAggregationAvg(amountData);
	}

	public synchronized AmountDataAggregationData getCurrentAggregationData() {
		AmountDataAggregationData aggregationData = null;
		
		if (this.ascSortedRecords.isEmpty())
		{
			aggregationData = new AmountDataAggregationData.AmountDataAggregationDataBuilder().buildWithDefaultValues();
		}
		else
		{
			aggregationData = new AmountDataAggregationData.AmountDataAggregationDataBuilder()
					.sum(this.aggregationAmountTotal).avg(this.aggregationAmountAvg)
					.max(this.dscSortedRecords.first().getAmount()).min(this.ascSortedRecords.first().getAmount())
					.count(this.amountDataQueue.size()).build();
		}
		
		return aggregationData;

	}

}

package com.amountaggregator.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amountaggregator.aggregation.engine.TimeBasedAmountAggregateService;
import com.amountaggregator.api.schema.types.AggregationData;
import com.amountaggregator.api.schema.types.AmountData;
import com.amountaggregator.data.AmountDataAggregationData;
import com.amountaggregator.service.AmountAggregatorService;

@Service
public class AmountAggregatorServiceImpl implements AmountAggregatorService {
	
	private ExecutorService loadDataExecutorService;
	private ScheduledExecutorService scheduledExecutorService;
	private ExecutorService invokeRemoveOldDataExecutorService;
	
	private int  corePoolSize  =   1;
	private int  maxPoolSize   =   1;
	private long keepAliveTime = 5000;
	
	@Autowired
	private TimeBasedAmountAggregateService timeBasedAmountAggregateService;
	
	@PostConstruct
	public void init()
	{
		this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
		this.loadDataExecutorService =
		        new ThreadPoolExecutor(
		                corePoolSize,
		                maxPoolSize,
		                keepAliveTime,
		                TimeUnit.MILLISECONDS,
		                new LinkedBlockingQueue<Runnable>()
		                );
		this.invokeRemoveOldDataExecutorService = Executors.newFixedThreadPool(1);
		Runnable invokeRemoveOldDataRunnable = () -> invokeRemoveOldData();
		invokeRemoveOldDataExecutorService.execute(invokeRemoveOldDataRunnable);
		
	}
	
	@Override
	public void loadAmountData(AmountData amountData) {
		Runnable loadDataRunnable = () -> timeBasedAmountAggregateService.doAggregation(amountData);
		loadDataExecutorService.execute(loadDataRunnable);
	}

	@Override
	public AmountDataAggregationData getCurrentAggregationData() {
		return timeBasedAmountAggregateService.getCurrentAggregationData();
	}
	
	private void invokeRemoveOldData()
	{
		Runnable removeOldAggDataRunnable = () -> timeBasedAmountAggregateService.removeOldData();
		scheduledExecutorService.scheduleAtFixedRate(removeOldAggDataRunnable, 1, 1, TimeUnit.SECONDS);
	}
	
	@PreDestroy
	public void destroy()
	{
		scheduledExecutorService.shutdown();
		loadDataExecutorService.shutdown();
		invokeRemoveOldDataExecutorService.shutdown();
	}
	

}

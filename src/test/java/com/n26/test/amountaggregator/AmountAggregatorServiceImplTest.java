package com.n26.test.amountaggregator;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amountaggregator.Application;
import com.amountaggregator.api.schema.types.AmountData;
import com.amountaggregator.data.AmountDataAggregationData;
import com.amountaggregator.service.AmountAggregatorService;

@RunWith(SpringRunner.class)
@SpringBootTest(
  classes = Application.class)
public class AmountAggregatorServiceImplTest {

	@Autowired
	private AmountAggregatorService amountAggregatorService;
	
	@Test
	public void testLoadAmountData() throws InterruptedException
	{
		long currentTimeinMilies = setTimeBeforeAggTimeFrame();
		
		AmountData amountData1 = new AmountData();
		amountData1.setAmount(1.1);
		amountData1.setTimestamp(currentTimeinMilies);
		amountAggregatorService.loadAmountData(amountData1);
		
		TimeUnit.SECONDS.sleep(30);
		
		AmountData amountData2 = new AmountData();
		amountData2.setAmount(2.2);
		currentTimeinMilies = currentTimeinMilies + 30000;
		amountData2.setTimestamp(currentTimeinMilies);
		amountAggregatorService.loadAmountData(amountData2);
		
		TimeUnit.SECONDS.sleep(1);
		Assert.assertEquals(getMockAmountDataAggregationData(3.3000000000000003, 1.6500000000000001, 1.1, 2.2, 2), amountAggregatorService.getCurrentAggregationData());
		TimeUnit.SECONDS.sleep(30);
		
		AmountData amountData3 = new AmountData();
		amountData3.setAmount(3.3);
		currentTimeinMilies = currentTimeinMilies + 30000;
		amountData3.setTimestamp(currentTimeinMilies);
		amountAggregatorService.loadAmountData(amountData3);
		
		TimeUnit.SECONDS.sleep(1);
		Assert.assertEquals(getMockAmountDataAggregationData(5.5, 2.75, 2.2, 3.3, 2), amountAggregatorService.getCurrentAggregationData());
		/*TimeUnit.SECONDS.sleep(30);
		
		AmountData amountData4 = new AmountData();
		amountData4.setAmount(4.4);
		currentTimeinMilies = currentTimeinMilies + 30000;
		amountData4.setTimestamp(currentTimeinMilies);
		amountAggregatorService.loadAmountData(amountData4);
		
		TimeUnit.SECONDS.sleep(1);
		Assert.assertEquals(getMockAmountDataAggregationData(7.7, 3.85, 3.3, 4.4, 2), amountAggregatorService.getCurrentAggregationData());
		TimeUnit.SECONDS.sleep(30);
		
		AmountData amountData5 = new AmountData();
		amountData5.setAmount(5.5);
		currentTimeinMilies = currentTimeinMilies + 30000;
		amountData5.setTimestamp(currentTimeinMilies);
		amountAggregatorService.loadAmountData(amountData5);
		
		TimeUnit.SECONDS.sleep(1);
		Assert.assertEquals(getMockAmountDataAggregationData(9.9, 4.95, 4.4, 5.5, 2), amountAggregatorService.getCurrentAggregationData());
		TimeUnit.SECONDS.sleep(30);
		
		AmountData amountData6 = new AmountData();
		amountData6.setAmount(6.6);
		currentTimeinMilies = currentTimeinMilies + 30000;
		amountData6.setTimestamp(currentTimeinMilies);
		amountAggregatorService.loadAmountData(amountData6);
		
		TimeUnit.SECONDS.sleep(1);
		Assert.assertEquals(getMockAmountDataAggregationData(12.1, 6.05, 5.5, 6.6, 2), amountAggregatorService.getCurrentAggregationData());
		TimeUnit.SECONDS.sleep(30);
		
		AmountData amountData7 = new AmountData();
		amountData7.setAmount(7.7);
		currentTimeinMilies = currentTimeinMilies + 30000;
		amountData7.setTimestamp(currentTimeinMilies);
		amountAggregatorService.loadAmountData(amountData7);
		
		TimeUnit.SECONDS.sleep(1);
		Assert.assertEquals(getMockAmountDataAggregationData(14.3, 7.15, 6.6, 7.7, 2), amountAggregatorService.getCurrentAggregationData());*/
		
	}
	
	private AmountDataAggregationData getMockAmountDataAggregationData(double sum, double avg, double min, double max,  long count)
	{
		return new AmountDataAggregationData.AmountDataAggregationDataBuilder().sum(sum).avg(avg).min(min).max(max).count(count).build();
	}
	
	private long setTimeBeforeAggTimeFrame() {
		ZonedDateTime utcNow = ZonedDateTime.now(ZoneOffset.UTC);
		return utcNow.toInstant().toEpochMilli();
	}
}

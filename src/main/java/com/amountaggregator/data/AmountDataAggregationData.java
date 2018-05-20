package com.amountaggregator.data;

/**
 * 
 * @author sivakkannanmuthukumar This is an immutable class which is created for
 *         data consistency
 *
 */
final public class AmountDataAggregationData {

	private double sum;
	private double avg;
	private double min;
	private double max;
	private long count;

	public AmountDataAggregationData(AmountDataAggregationDataBuilder aggregationDataBuilder) {
		super();
		this.sum = aggregationDataBuilder.sum;
		this.avg = aggregationDataBuilder.avg;
		this.min = aggregationDataBuilder.min;
		this.max = aggregationDataBuilder.max;
		this.count = aggregationDataBuilder.count;
	}

	private AmountDataAggregationData() {

	}

	public double getSum() {
		return sum;
	}

	public double getAvg() {
		return avg;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

	public long getCount() {
		return count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(avg);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (count ^ (count >>> 32));
		temp = Double.doubleToLongBits(max);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(min);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sum);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AmountDataAggregationData other = (AmountDataAggregationData) obj;
		if (Double.doubleToLongBits(avg) != Double.doubleToLongBits(other.avg))
			return false;
		if (count != other.count)
			return false;
		if (Double.doubleToLongBits(max) != Double.doubleToLongBits(other.max))
			return false;
		if (Double.doubleToLongBits(min) != Double.doubleToLongBits(other.min))
			return false;
		if (Double.doubleToLongBits(sum) != Double.doubleToLongBits(other.sum))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AmountDataAggregationData [sum=" + sum + ", avg=" + avg + ", min=" + min + ", max=" + max + ", count="
				+ count + "]";
	}



	public static class AmountDataAggregationDataBuilder {
		private double sum;
		private double avg;
		private double min;
		private double max;
		private long count;

		public AmountDataAggregationDataBuilder sum(double sum) {
			this.sum = sum;
			return this;
		}

		public AmountDataAggregationDataBuilder avg(double avg) {
			this.avg = avg;
			return this;
		}

		public AmountDataAggregationDataBuilder min(double min) {
			this.min = min;
			return this;
		}

		public AmountDataAggregationDataBuilder max(double max) {
			this.max = max;
			return this;
		}

		public AmountDataAggregationDataBuilder count(long count) {
			this.count = count;
			return this;
		}

		public AmountDataAggregationData build() {
			return new AmountDataAggregationData(this);
		}
		
		public AmountDataAggregationData buildWithDefaultValues() {
			return new AmountDataAggregationData(new AmountDataAggregationData.AmountDataAggregationDataBuilder().sum(0.0).avg(0.0).count(0).max(0.0).min(0.0));
		}
	}
}

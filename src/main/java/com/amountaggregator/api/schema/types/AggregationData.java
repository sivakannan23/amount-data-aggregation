
package com.amountaggregator.api.schema.types;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sum",
    "avg",
    "max",
    "min",
    "count"
})
public class AggregationData {

    /**
     * The Sum Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("sum")
    private Double sum = 0.0;
    /**
     * The Avg Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("avg")
    private Double avg = 0.0D;
    /**
     * The Max Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("max")
    private Double max = 0.0D;
    /**
     * The Min Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("min")
    private Double min = 0.0D;
    /**
     * The Count Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("count")
    private Long count = 0L;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The Sum Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("sum")
    public Double getSum() {
        return sum;
    }

    /**
     * The Sum Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("sum")
    public void setSum(Double sum) {
        this.sum = sum;
    }

    /**
     * The Avg Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("avg")
    public Double getAvg() {
        return avg;
    }

    /**
     * The Avg Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("avg")
    public void setAvg(Double avg) {
        this.avg = avg;
    }

    /**
     * The Max Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("max")
    public Double getMax() {
        return max;
    }

    /**
     * The Max Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("max")
    public void setMax(Double max) {
        this.max = max;
    }

    /**
     * The Min Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("min")
    public Double getMin() {
        return min;
    }

    /**
     * The Min Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("min")
    public void setMin(Double min) {
        this.min = min;
    }

    /**
     * The Count Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("count")
    public Long getCount() {
        return count;
    }

    /**
     * The Count Schema 
     * <p>
     * 
     * 
     */
    @JsonProperty("count")
    public void setCount(Long count) {
        this.count = count;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("sum", sum).append("avg", avg).append("max", max).append("min", min).append("count", count).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(avg).append(min).append(max).append(count).append(sum).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AggregationData) == false) {
            return false;
        }
        AggregationData rhs = ((AggregationData) other);
        return new EqualsBuilder().append(avg, rhs.avg).append(min, rhs.min).append(max, rhs.max).append(count, rhs.count).append(sum, rhs.sum).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}

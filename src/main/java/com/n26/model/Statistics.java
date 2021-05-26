package com.n26.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

/**
 * Statistics
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-23T13:23:28.910Z[GMT]")


public class Statistics   {
  @JsonProperty("sum")
  private String sum = null;

  @JsonProperty("avg")
  private String avg = null;

  @JsonProperty("max")
  private String max = null;

  @JsonProperty("min")
  private String min = null;

  @JsonProperty("count")
  private Long count = null;

  public Statistics sum(String sum) {
    this.sum = sum;
    return this;
  }

  /**
   * Get sum
   * @return sum
   **/
  
    public String getSum() {
    return sum;
  }

  public void setSum(String sum) {
    this.sum = sum;
  }

  public Statistics avg(String avg) {
    this.avg = avg;
    return this;
  }

  /**
   * Get avg
   * @return avg
   **/
  
    public String getAvg() {
    return avg;
  }

  public void setAvg(String avg) {
    this.avg = avg;
  }

  public Statistics max(String max) {
    this.max = max;
    return this;
  }

  /**
   * Get max
   * @return max
   **/
  
    public String getMax() {
    return max;
  }

  public void setMax(String max) {
    this.max = max;
  }

  public Statistics min(String min) {
    this.min = min;
    return this;
  }

  /**
   * Get min
   * @return min
   **/
  
    public String getMin() {
    return min;
  }

  public void setMin(String min) {
    this.min = min;
  }

  public Statistics count(Long count) {
    this.count = count;
    return this;
  }

  /**
   * Get count
   * @return count
   **/
  
    public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Statistics statistics = (Statistics) o;
    return Objects.equals(this.sum, statistics.sum) &&
        Objects.equals(this.avg, statistics.avg) &&
        Objects.equals(this.max, statistics.max) &&
        Objects.equals(this.min, statistics.min) &&
        Objects.equals(this.count, statistics.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sum, avg, max, min, count);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Statistics {\n");
    
    sb.append("    sum: ").append(toIndentedString(sum)).append("\n");
    sb.append("    avg: ").append(toIndentedString(avg)).append("\n");
    sb.append("    max: ").append(toIndentedString(max)).append("\n");
    sb.append("    min: ").append(toIndentedString(min)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

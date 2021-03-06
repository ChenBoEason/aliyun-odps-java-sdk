/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aliyun.odps.io;

import java.io.*;
import java.math.BigDecimal;

public class BigDecimalWritable implements WritableComparable<BigDecimalWritable> {

  private BigDecimal value;

  public BigDecimalWritable() {
    value = BigDecimal.ZERO;
  }

  public BigDecimalWritable(BigDecimal bi) {
    value = bi;
  }

  public void set(BigDecimal value) {
    this.value = value;
  }

  public BigDecimal get() {
    return value;
  }

  @Override
  public int compareTo(BigDecimalWritable o) {
    return value.compareTo(o.get());
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof BigDecimalWritable)) {
      return false;
    }
    return compareTo((BigDecimalWritable)o) == 0;
  }

  @Override
  public String toString() {
    return value.toString();
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    value = new BigDecimal(Text.readString(in));
  }

  @Override
  public void write(DataOutput out) throws IOException {
    Text.writeString(out, value.toPlainString());
  }

  /** A Comparator optimized for BigDecimalWritable. */
  //TODO consider trying to do something a big more optimizied
  public static class Comparator extends WritableComparator {
    private BigDecimalWritable thisValue = new BigDecimalWritable();
    private BigDecimalWritable thatValue = new BigDecimalWritable();

    public Comparator() {
      super(BigDecimalWritable.class);
    }

    @Override
    public int compare(byte[] b1, int s1, int l1,
        byte[] b2, int s2, int l2) {
      try {
        thisValue.readFields(new DataInputStream(new ByteArrayInputStream(b1,s1,l1)));
        thatValue.readFields(new DataInputStream(new ByteArrayInputStream(b2,s2,l2)));
      } catch (IOException e) {
        throw new RuntimeException("Unable to read field from byte array: " + e);
      }
      return thisValue.compareTo(thatValue);
    }
  }

  // register this comparator
  static {
    WritableComparator.define(BigDecimalWritable.class, new Comparator());
  }
}
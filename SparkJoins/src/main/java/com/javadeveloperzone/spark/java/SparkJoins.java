package com.javadeveloperzone.spark.java;

import java.io.FileNotFoundException;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class SparkJoins {

    
    public static void main(String[] args) throws FileNotFoundException {
        
    	JavaSparkContext sparkContext = new JavaSparkContext(new SparkConf().setAppName("Spark Joins").setMaster("local"));
        
        JavaRDD<String> customerInputFile = sparkContext.textFile("/media/bigdataspots/data/prashant/tech-docs/spark/sample-input/2/UserDetails.csv");
        
        JavaPairRDD<String, String> customerPairs = customerInputFile.mapToPair(new PairFunction<String, String, String>() {
            public Tuple2<String, String> call(String s) {
                String[] customerSplit = s.split(",");
                return new Tuple2<String, String>(customerSplit[0], customerSplit[1]+","+customerSplit[2]);
            }
        }).distinct();

        JavaRDD<String> transactionInputFile = sparkContext.textFile("/media/bigdataspots/data/prashant/tech-docs/spark/sample-input/2/AddressDetails.csv");
       
        JavaPairRDD<String, String> transactionPairs = transactionInputFile.mapToPair(new PairFunction<String, String, String>() {
            public Tuple2<String, String> call(String s) {
                String[] transactionSplit = s.split(",");
                return new Tuple2<String, String>(transactionSplit[0], transactionSplit[1]+","+transactionSplit[2]+","+transactionSplit[3]);
            }
        });

        //Default Join operation (Inner join)
        JavaPairRDD<String, Tuple2<String, String>> joinsOutput = customerPairs.join(transactionPairs);
        System.out.println("Joins function Output: "+joinsOutput.collect());
        joinsOutput.saveAsTextFile("/home/bigdataspots/Desktop/InnerJoin");

        //Left Outer join operation
       /* JavaPairRDD<String, Iterable<Tuple2<String, Optional<String>>>> leftJoinOutput = customerPairs.leftOuterJoin(transactionPairs).groupByKey().sortByKey();
        System.out.println("LeftOuterJoins function Output: "+leftJoinOutput.collect());
        leftJoinOutput.saveAsTextFile("/home/ubuntu/Desktop/SparkOutput/Joins/LeftOuterJoin");

        //Right Outer join operation
        JavaPairRDD<String, Iterable<Tuple2<Optional<String>, String>>> rightJoinOutput = customerPairs.rightOuterJoin(transactionPairs).groupByKey().sortByKey();
        System.out.println("LeftOuterJoins function Output: "+rightJoinOutput.collect());
        rightJoinOutput.saveAsTextFile("/home/ubuntu/Desktop/SparkOutput/Joins/RightOuterJoin");

        JavaPairRDD<String, Tuple2<String, Optional<String>>> rddWithJoin = customerPairs.leftOuterJoin(transactionPairs);
    
        // mapping of join result
        JavaPairRDD<String, String> mappedRDD = rddWithJoin
                    .mapToPair(tuple -> {
                        if (tuple._2()._2().isPresent()) {
                            //do your operation and return
                        	
                            return new Tuple2<String, String>(tuple._1(), tuple._2()._1());
                        } else {
                            return new Tuple2<String, String>(tuple._1(), "not present");
                        }
                    });*/
        
     /*   JavaPairRDD<String, String> mappedRDD = rddWithJoin
                .mapToPair(new PairFunction<Tuple2<String,Tuple2<String,Optional<String>>>, String, String>() {

					@Override
					public Tuple2<String, String> call(Tuple2<String, Tuple2<String, Optional<String>>> input)
							throws Exception {
						
						
						input.
						
						
						return null;
					}
				});*/
    
//        mappedRDD.saveAsTextFile("/home/ubuntu/Desktop/SparkOutput/Joins/LeftOuterJoin2");
        
        sparkContext.stop();
        
        sparkContext.close();
        
    }
}

package com.javadeveloperzone.hadoop.reducesidejoin;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;



public class AddressFileMapper extends Mapper<LongWritable,Text,LongWritable,Text>
{

	private static final String fileTag="AD~";
	
	private static final String DATA_SEPERATOR=",";
	
	public void map(LongWritable key, Text value,
			Context context) throws IOException,InterruptedException
	{
		
		String values[] = value.toString().split(DATA_SEPERATOR);
		
		StringBuilder dataStringBuilder = new StringBuilder();
		
		for(int index=0 ; index < values.length; index ++)
		{
			
			if(index != 0)
			{
				
				dataStringBuilder.append(values[index].toString().trim() + DATA_SEPERATOR);
				
			}
			else
			{
				
				dataStringBuilder.append(fileTag);
				
			}

		}
		
		String dataString = dataStringBuilder.toString();
		
		if (dataString != null && dataString.length() > 1) 
		{
			
			dataString = dataString.substring(0, dataString.length() - 1);
			
	    }
		
		dataStringBuilder= null;
		
		context.write(new LongWritable(Long.parseLong(values[0])), new Text(dataString));
		
	}
	
}	
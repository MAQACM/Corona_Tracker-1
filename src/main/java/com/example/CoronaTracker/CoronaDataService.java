package com.example.CoronaTracker;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CoronaDataService {
	private String data_Url="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStats> allStats=new ArrayList<>();
    int totalCases;
    @PostConstruct
    @Scheduled(cron="* * 1 * * *")
	public void coronaData() throws IOException, InterruptedException {
    	List<LocationStats> newStats=new ArrayList<>();
    	int sum=0;
    	HttpClient client=HttpClient.newHttpClient();
    	HttpRequest request= HttpRequest.newBuilder()
    			.uri(URI.create(data_Url))
    			.build();
    	HttpResponse<String> httpResponse= client.send(request, HttpResponse.BodyHandlers.ofString());
    	Reader in = new FileReader(httpResponse.body());
    	Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
    	for (CSVRecord record : records) {
    		LocationStats locationStats=new LocationStats();
    		locationStats.setState(record.get("Province/State"));
    	    locationStats.setCountry(record.get("Country/Region"));
    	    int previous_cases=Integer.parseInt(record.get(record.size()-2));
    	    int today_cases=Integer.parseInt(record.get(record.size()-1));
    	    locationStats.setLatestTotal(today_cases-previous_cases);
    	    sum=sum+Integer.parseInt(record.get(record.size()-1));
    	    newStats.add(locationStats);
    	}
    	this.allStats=newStats;
    	this.totalCases=sum;
    }
	public List<LocationStats> getAllStats() {
		return allStats;
	}
	public int getTotalCases() {
		return totalCases;
	}

}

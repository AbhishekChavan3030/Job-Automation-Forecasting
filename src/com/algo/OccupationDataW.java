package com.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OccupationDataW {
	public static Map<String, Object> row1 = new HashMap<>();
	public static Map<String, Object> row2 = new HashMap<>();
	public static Map<String, Object> results = new HashMap<>();
	public static List<Map<String, Object>> data = new ArrayList<>();

	public static void main(String[] args) {

		row1.put("prob", 0.0081);
		row1.put("average_ann_wage", 49450);
		row1.put("numbEmployed", 49320);
		row1.put("probability", 0.0081);
		row1.put("len", 6);
		// Calculate risk level, growth, wages, volume, polling, and job score
		row2.put("Risk Level", calculateRiskLevelS((double) row1.get("prob")));
		row2.put("Growth", calculateGrowthS((int) row1.get("len")));
		row2.put("Wages", calculateWagesS((int) row1.get("average_ann_wage")));
		row2.put("Volume", calculateVolumeS((int) row1.get("numbEmployed")));
		row2.put("Polling", calculatePollingS((double) row1.get("probability")));
		row2.put("Job Score", calculateJobScoreS(row2));

		// Add the row to the data list
		data.add(row2);

		// Display the data
		double jobScore = 0;
		double growth = 1.5;
		double wages = 60000;
		double volumePolling = 0.8;
		double medianAnnualWage = 55000;
		double employedInMay2016 = 50000;
		double averageAnnualWage = 56000;
		for (Map<String, Object> row : data) {

			System.out.println("Risk Level: " + row.get("Risk Level"));
			System.out.println("Growth: " + row.get("Growth"));
			System.out.println("Wages: " + row.get("Wages"));
			System.out.println("Volume: " + row.get("Volume"));
			System.out.println("Polling: " + row.get("Polling"));
			System.out.println("Job Score: " + row.get("Job Score"));
			System.out.println();
			String job = row.get("Job Score").toString();
			jobScore=Double.parseDouble(job);
		}
		
		// Additional data
		double growthRate = 0.03; // Assuming 3% growth rate per year
		double wagePerHour = 40.0;
		int volume2022 = 50000; // Assuming volume remains constant
		int pollingVotes = 33; // Assuming polling votes remain constant

		// Calculate growth by 2032
		int currentYear = 2022;
		int targetYear = 2032;
		int years = targetYear - currentYear;
		double growthBy2032 = calculateGrowthByYear(growthRate, years);

		// Calculate wages at $40 per hour
		double wagesAt40PerHour = calculateWages(wagePerHour);

		// Calculate volume as of 2022
		String volume2022Str = calculateVolume(volume2022);

		// Calculate polling based on 33 votes
		String polling = calculatePolling(pollingVotes);

		// Display the calculated values
		System.out.println("Growth by 2032: " + growthBy2032);
		System.out.println("Wages at $40 per hour: " + wagesAt40PerHour);
		System.out.println("Volume as of 2022: " + volume2022Str);
		System.out.println("Polling based on 33 votes: " + polling);
		double riskLevelScore = calculateRiskLevelScore(jobScore, growth,
				wagesAt40PerHour, volumePolling, medianAnnualWage, employedInMay2016,
				averageAnnualWage);
		System.out.println("Risk Level Score: " + calculateRiskLevel(growth,wagesAt40PerHour,volumePolling));

	}
	private static double calculateRiskLevel(double growthRate, double wages, double volumePolling) {
        // You can define your own formula for calculating the risk level
        return (growthRate + wages + volumePolling) / 3;
    }
	public static double calculateRiskLevelScore(double jobScore, double growth,
			double wages, double volumePolling, double medianAnnualWage,
			double employedInMay2016, double averageAnnualWage) {
		double riskLevelScore = 0.0;
		// Calculate risk level score based on your criteria
		// For example, you can use a weighted sum of the input parameters
		riskLevelScore = (jobScore * 0.2) + (growth * 0.1) + (wages * 0.1)
				+ (volumePolling * 0.1) + (medianAnnualWage * 0.2)
				+ (employedInMay2016 * 0.1) + (averageAnnualWage * 0.2);

		// You can add more complex logic here to determine the risk level score
		return riskLevelScore;
	}

	private static String calculateRiskLevelS(double prob) {
		if (prob < 0.3) {
			return "Low";
		} else if (prob < 0.7) {
			return "Medium";
		} else {
			return "High";
		}
	}

	private static String calculateGrowthS(int len) {
		if (len < 3) {
			return "Low";
		} else if (len < 5) {
			return "Medium";
		} else {
			return "High";
		}
	}

	private static String calculateWagesS(double average_ann_wage) {
		if (average_ann_wage < 50000) {
			return "Low";
		} else if (average_ann_wage < 100000) {
			return "Medium";
		} else {
			return "High";
		}
	}

	private static String calculateVolumeS(int numbEmployed) {
		if (numbEmployed < 50000) {
			return "Low";
		} else if (numbEmployed < 100000) {
			return "Medium";
		} else {
			return "High";
		}
	}

	private static String calculatePollingS(double probability) {
		if (probability < 0.5) {
			return "Negative";
		} else {
			return "Positive";
		}
	}

	private static double calculateJobScoreS(Map<String, Object> row) {
		// Example calculation: Sum of risk level, growth, wages, and volume
		double riskScore = getScore(row.get("Risk Level"));
		double growthScore = getScore(row.get("Growth"));
		double wagesScore = getScore(row.get("Wages"));
		double volumeScore = getScore(row.get("Volume"));

		return riskScore + growthScore + wagesScore + volumeScore;
	}

	private static double getScore(Object value) {
		switch ((String) value) {
		case "Low":
			return 1;
		case "Medium":
			return 2;
		case "High":
			return 3;
		default:
			return 0;
		}
	}

	private static double calculateGrowthByYear(double growthRate, int years) {
		return Math.pow(1 + growthRate, years);
	}

	private static double calculateWages(double wagePerHour) {
		return wagePerHour * 40 * 52; // Assuming 40 hours per week and 52 weeks
										// per year
	}

	private static String calculateVolume(int volume) {
		return "Volume: " + volume;
	}

	private static String calculatePolling(int votes) {
		return "Polling: " + votes;
	}

}

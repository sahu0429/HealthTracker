import java.util.*;

public class HealthTracker {

    private static Map<String, List<Double>> weeklyRecords = new HashMap<>();
    private static Map<String, List<Double>> monthlyRecords = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nHealth Tracker Menu:");
            System.out.println("1. Calculate Blood Pressure");
            System.out.println("2. Calculate Cholesterol Level");
            System.out.println("3. Calculate Heart Rate");
            System.out.println("4. Calculate Temperature");
            System.out.println("5. Calculate BMI");
            System.out.println("6. Calculate Steps Walked");
            System.out.println("7. View Weekly Report");
            System.out.println("8. View Monthly Averages");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    calculateBloodPressure(scanner);
                    break;
                case 2:
                    calculateCholesterol(scanner);
                    break;
                case 3:
                    calculateHeartRate(scanner);
                    break;
                case 4:
                    calculateTemperature(scanner);
                    break;
                case 5:
                    calculateBMI(scanner);
                    break;
                case 6:
                    calculateStepsWalked(scanner);
                    break;
                case 7:
                    viewWeeklyReport();
                    break;
                case 8:
                    viewMonthlyAverages();
                    break;
                case 9:
                    running = false;
                    System.out.println("Exiting the Health Tracker. Stay healthy!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void recordData(String category, double value) {
        weeklyRecords.computeIfAbsent(category, k -> new ArrayList<>()).add(value);
        monthlyRecords.computeIfAbsent(category, k -> new ArrayList<>()).add(value);

        if (weeklyRecords.get(category).size() > 7) {
            weeklyRecords.get(category).remove(0);
        }

        if (monthlyRecords.get(category).size() > 30) {
            monthlyRecords.get(category).remove(0);
        }
    }

    private static void viewWeeklyReport() {
        System.out.println("\nWeekly Report:");
        for (Map.Entry<String, List<Double>> entry : weeklyRecords.entrySet()) {
            System.out.printf("%s: %s%n", entry.getKey(), entry.getValue());
        }
    }

    private static void viewMonthlyAverages() {
        System.out.println("\nMonthly Averages:");
        for (Map.Entry<String, List<Double>> entry : monthlyRecords.entrySet()) {
            double sum = entry.getValue().stream().mapToDouble(Double::doubleValue).sum();
            double average = sum / entry.getValue().size();
            System.out.printf("%s: %.2f%n", entry.getKey(), average);
        }
    }

    private static void calculateBloodPressure(Scanner scanner) {
        System.out.print("Enter systolic pressure (mmHg): ");
        int systolic = scanner.nextInt();
        System.out.print("Enter diastolic pressure (mmHg): ");
        int diastolic = scanner.nextInt();

        String category;
        if (systolic < 120 && diastolic < 80) {
            category = "Normal";
        } else if (systolic <= 129 && diastolic < 80) {
            category = "Elevated";
        } else if (systolic <= 139 || diastolic <= 89) {
            category = "Hypertension Stage 1";
        } else if (systolic >= 140 || diastolic >= 90) {
            category = "Hypertension Stage 2";
        } else {
            category = "Hypertensive Crisis";
        }
        System.out.println("Blood Pressure: " + category);
        recordData("Blood Pressure", (systolic + diastolic) / 2.0);
    }

    private static void calculateCholesterol(Scanner scanner) {
        System.out.print("Enter total cholesterol level (mg/dL): ");
        int cholesterol = scanner.nextInt();

        String category;
        if (cholesterol < 200) {
            category = "Desirable";
        } else if (cholesterol >= 200 && cholesterol <= 239) {
            category = "Borderline High";
        } else {
            category = "High";
        }
        System.out.println("Cholesterol Level: " + category);
        recordData("Cholesterol", cholesterol);
    }

    private static void calculateHeartRate(Scanner scanner) {
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        System.out.print("Enter your heart rate (bpm): ");
        int heartRate = scanner.nextInt();

        int maxHeartRate = 220 - age;
        double targetHeartRateLower = 0.5 * maxHeartRate;
        double targetHeartRateUpper = 0.85 * maxHeartRate;

        System.out.printf("Your maximum heart rate should be around %d bpm.%n", maxHeartRate);
        System.out.printf("Your target heart rate zone is between %.0f bpm and %.0f bpm.%n", targetHeartRateLower, targetHeartRateUpper);

        String category;
        if (heartRate < targetHeartRateLower) {
            category = "Below target zone";
        } else if (heartRate > targetHeartRateUpper) {
            category = "Above target zone";
        } else {
            category = "Within target zone";
        }
        System.out.println("Heart Rate: " + category);
        recordData("Heart Rate", heartRate);
    }

    private static void calculateTemperature(Scanner scanner) {
        System.out.print("Enter body temperature (°F): ");
        double temperature = scanner.nextDouble();

        String category;
        if (temperature < 97.0) {
            category = "Below Normal";
        } else if (temperature >= 97.0 && temperature <= 99.0) {
            category = "Normal";
        } else {
            category = "Fever";
        }
        System.out.println("Temperature: " + category);
        recordData("Temperature", temperature);
    }

    private static void calculateBMI(Scanner scanner) {
        System.out.print("Enter weight (kg): ");
        double weight = scanner.nextDouble();
        System.out.print("Enter height (m): ");
        double height = scanner.nextDouble();

        double bmi = weight / (height * height);
        System.out.printf("Your BMI is: %.2f%n", bmi);

        String category;
        if (bmi < 18.5) {
            category = "Underweight";
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            category = "Normal weight";
        } else if (bmi >= 25.0 && bmi <= 29.9) {
            category = "Overweight";
        } else {
            category = "Obesity";
        }
        System.out.println("BMI Category: " + category);
        recordData("BMI", bmi);
    }

    private static void calculateStepsWalked(Scanner scanner) {
        System.out.print("Enter the number of steps walked today: ");
        int steps = scanner.nextInt();

        String category;
        if (steps < 5000) {
            category = "Sedentary";
        } else if (steps >= 5000 && steps <= 7499) {
            category = "Lightly Active";
        } else if (steps >= 7500 && steps <= 9999) {
            category = "Moderately Active";
        } else {
            category = "Very Active";
        }
        System.out.println("Steps Category: " + category);
        recordData("Steps", steps);
    }
}
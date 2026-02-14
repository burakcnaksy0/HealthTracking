package com.burakcanaksoy.healthtracking.service;

import java.math.BigDecimal;

import com.burakcanaksoy.healthtracking.data.WeeklyStats;
import com.burakcanaksoy.healthtracking.dto.DailySummary;
import com.burakcanaksoy.healthtracking.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PromptService {
    private final DailySummaryService dailySummaryService;
    private final WorkoutLogService workoutLogService;

    public String createCoachingPrompt(User user) {
        DailySummary dailySummary = dailySummaryService.generateDailySummary(user);
        BigDecimal bmr = workoutLogService.calculateBMH(user);

        String bmrString = (bmr != null) ? bmr.toPlainString() : "Unknown";
        String goal = (user.getGoal() != null) ? user.getGoal().name() : "General Fitness";

        return String.format(
                "You are an expert nutrition and fitness coach. Analyze the user's daily health data and provide personalized guidance.\n\n"
                        +
                        "User Profile:\n" +
                        "- Age: %d\n" +
                        "- Weight: %s kg\n" +
                        "- Goal: %s\n" +
                        "- Basal Metabolic Rate (BMR): %s kcal\n\n" +
                        "Daily Summary:\n" +
                        "- Calories Consumed: %s kcal\n" +
                        "- Calories Burned: %s kcal\n" +
                        "- Protein Intake: %s g\n" +
                        "- Carbohydrates Intake: %s g\n" +
                        "- Fat Intake: %s g\n" +
                        "- Water Intake: %d ml\n" +
                        "- Workout Duration: %d minutes\n\n" +
                        "Instructions:\n" +
                        "- Evaluate calorie balance relative to the user's goal and BMR.\n" +
                        "- Comment on macro balance (protein, carbs, fat).\n" +
                        "- Check hydration and activity level.\n" +
                        "- Provide 3 short recommendations that are:\n" +
                        "  * Motivational\n" +
                        "  * Practical\n" +
                        "  * Corrective if needed\n" +
                        "- Each recommendation must be 1–2 sentences maximum.\n" +
                        "- Use a friendly, supportive tone.\n" +
                        "- Do NOT repeat the numeric data in the response.\n",
                user.getAge(),
                user.getWeight(),
                goal,
                bmrString,
                dailySummary.getTotalCaloriesIn(),
                dailySummary.getTotalCaloriesOut(),
                dailySummary.getTotalProteinIn(),
                dailySummary.getTotalCarbIn(),
                dailySummary.getTotalFatIn(),
                dailySummary.getTotalWaterIntakeIn(),
                dailySummary.getTotalDailyWorkout());
    }

    public String createReminderPrompt(User user) {
        DailySummary dailySummary = dailySummaryService.generateDailySummary(user);

        boolean noFood = dailySummary.getTotalCaloriesIn().compareTo(BigDecimal.ZERO) == 0;
        boolean noWater = dailySummary.getTotalWaterIntakeIn() == 0;

        if (!noFood && !noWater) {
            return null;
        }

        String missingItem;
        if (noFood && noWater) {
            missingItem = "food and water";
        } else if (noFood) {
            missingItem = "food";
        } else {
            missingItem = "water";
        }

        return String.format(
                "You are a friendly but responsible health assistant.\n\n" +
                        "User Context:\n" +
                        "- Username: %s\n" +
                        "- Current Time: 12:00 PM\n" +
                        "- Missing Logs: %s (no entries recorded so far today)\n\n" +
                        "Task:\n" +
                        "- Write exactly ONE short sentence.\n" +
                        "- Gently remind the user to log their %s.\n" +
                        "- The tone must be caring, slightly firm, and motivating.\n" +
                        "- Speak directly to the user.\n" +
                        "- Encourage immediate action.\n" +
                        "- Do NOT include emojis.\n",
                user.getUsername(),
                missingItem,
                missingItem);
    }

    public String generateReportText(User user,
                                     WeeklyStats stats,
                                     LocalDateTime startDate, LocalDateTime endDate) {
        return String.format(
                "You are an expert health and fitness coach. Analyze the user's weekly health data and generate a structured, detailed weekly report.\n\n" +

                        "USER PROFILE:\n" +
                        "- Age: %d\n" +
                        "- Weight: %s kg\n" +
                        "- Goal: %s\n\n" +

                        "WEEKLY SUMMARY (%s - %s):\n" +
                        "Overall Statistics:\n" +
                        "- Total Calories Consumed: %s kcal\n" +
                        "- Total Calories Burned: %s kcal\n" +
                        "- Average Daily Protein Intake: %s g\n" +
                        "- Average Daily Carbohydrate Intake: %s g\n" +
                        "- Average Daily Fat Intake: %s g\n" +
                        "- Average Daily Water Intake: %s ml\n" +
                        "- Total Workout Duration: %d minutes\n" +
                        "- Active Days: %d/7\n" +
                        "- Workout Days: %d/7\n\n" +

                        "INSTRUCTIONS:\n" +
                        "Create a structured weekly report that includes the following sections:\n\n" +

                        "1) GENERAL EVALUATION (2–3 sentences)\n" +
                        "- Summarize the user’s overall weekly performance.\n" +
                        "- Highlight key achievements and important areas that need attention.\n\n" +

                        "2) NUTRITION ANALYSIS (3–4 sentences)\n" +
                        "- Evaluate calorie balance in relation to the user's goal.\n" +
                        "- Analyze macronutrient distribution (protein, carbohydrates, fat).\n" +
                        "- Comment on consistency and noticeable patterns.\n\n" +

                        "3) ACTIVITY & EXERCISE (2–3 sentences)\n" +
                        "- Evaluate workout frequency and duration.\n" +
                        "- Comment on calorie expenditure and activity level.\n\n" +

                        "4) HYDRATION (1–2 sentences)\n" +
                        "- Assess whether the user's water intake is sufficient.\n\n" +

                        "5) RECOMMENDATIONS FOR NEXT WEEK (4–5 bullet points)\n" +
                        "- Provide specific and practical suggestions.\n" +
                        "- Tailor recommendations according to the user’s goal.\n" +
                        "- Focus on sustainable and realistic improvements.\n\n" +

                        "TONE:\n" +
                        "- Professional, supportive, motivating, and friendly.\n" +
                        "- Do NOT repeat numeric values exactly.\n" +
                        "- Focus on guidance, interpretation, and actionable advice.",

                user.getAge(),
                user.getWeight(),
                user.getGoal() != null ? user.getGoal().name() : "General Health",
                startDate.toLocalDate(),
                endDate.toLocalDate(),
                stats.getTotalCaloriesIn(),
                stats.getTotalCaloriesOut(),
                stats.getAvgProtein(),
                stats.getAvgCarbs(),
                stats.getAvgFat(),
                stats.getAvgWater(),
                stats.getTotalWorkoutMinutes(),
                stats.getActiveDays(),
                stats.getWorkoutDays()
        );
    }
}

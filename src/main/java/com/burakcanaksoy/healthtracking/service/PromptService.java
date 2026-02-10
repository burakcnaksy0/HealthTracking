package com.burakcanaksoy.healthtracking.service;

import com.burakcanaksoy.healthtracking.dto.DailySummary;
import com.burakcanaksoy.healthtracking.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PromptService {
    private final DailySummaryService dailySummaryService;

    public String createCoachingPrompt(User user, DailySummary dailySummary, BigDecimal bmr) {
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
                missingItem
        );
    }
}

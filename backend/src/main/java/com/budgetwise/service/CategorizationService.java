package com.budgetwise.service;

import com.budgetwise.dto.CategorizationSuggestionDto;
import com.budgetwise.entity.Category;
import com.budgetwise.entity.Transaction;
import com.budgetwise.entity.User;
import com.budgetwise.repository.CategoryRepository;
import com.budgetwise.repository.TransactionRepository;
import com.budgetwise.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategorizationService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final GeminiService geminiService;

    public CategorizationService(CategoryRepository categoryRepository, UserRepository userRepository,
            TransactionRepository transactionRepository, GeminiService geminiService) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.geminiService = geminiService;
    }

    // Keyword dictionary for categorization
    private static final Map<String, List<String>> CATEGORY_KEYWORDS = new HashMap<>() {
        {
            put("Food & Dining", Arrays.asList("restaurant", "cafe", "coffee", "pizza", "burger", "food", "dining",
                    "mcdonald", "starbucks", "subway", "chipotle", "domino", "kfc", "taco","wendy"));put("Groceries",Arrays.asList("grocery","supermarket","walmart","target","costco","safeway","kroger","whole foods","trader joe","aldi","market","vegetable","fruit","milk","bread"));put("Transportation",Arrays.asList("uber","lyft","taxi","gas","fuel","shell","chevron","exxon","bp","parking","metro","transit","bus","train"));put("Rent",Arrays.asList("rent","lease","apartment","housing","landlord"));put("Utilities",Arrays.asList("electric","water","gas","internet","phone","utility","verizon","at&t","comcast","spectrum","t-mobile"));put("Healthcare",Arrays.asList("doctor","hospital","pharmacy","medical","health","clinic","cvs","walgreens","medicine","prescription"));put("Entertainment",Arrays.asList("movie","cinema","netflix","spotify","hulu","disney","game","theater","concert","ticket","entertainment"));put("Shopping",Arrays.asList("amazon","ebay","shop","store","mall","clothing","fashion","nike","adidas","zara","h&m"));put("Education",Arrays.asList("school","university","college","course","tuition","book","education","learning","udemy","coursera"));put("Travel",Arrays.asList("hotel","flight","airline","airbnb","booking","expedia","travel","vacation","trip","airport"));put("Insurance",Arrays.asList("insurance","policy","premium","geico","state farm","allstate"));put("Salary",Arrays.asList("salary","paycheck","wages","income","bonus","earnings","stipend"));put("Side Hustle",Arrays.asList("freelance","upwork","fiverr","side hustle","contract","gig"));put("Savings",Arrays.asList("savings","save","deposit","contribution","goal","fund","invest"));put("Personal Care",Arrays.asList("saloon","salon","barber","haircut","spa","massage","grooming","cosmetics","makeup"));}};
                        .reason(String.format("Matched keywords in description: '%s'", description))
                        .build();
    }}

    // 2. If no keyword match, use Gemini AI (Smart & Flexible)
    return suggestWithAI(description,categories,userId);}

    private boolean matchesKeywords(String description, String categoryName) {
        List<String> keywords = CATEGORY_KEYWORDS.getOrDefault(categoryName, new ArrayList<>());
        for (String keyword : keywords) {
            if (description.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private CategorizationSuggestionDto suggestWithAI(String description, List<Category> categories, Long userId) {
        String suggestedCategoryName = geminiService.generateContent(prompt).trim();

        // Clean up response (remove quotes, extra spaces)
        suggestedCategoryName = suggestedCategoryName.replace("\"", "").replace("'", "").replace(".", "");

        // 1. Check if it matches an existing category (Fuzzy Match)
        Category matchedCategory = findBestMatchingCategory(suggestedCategoryName, categories);

        if (matchedCategory != null) {
            return CategorizationSuggestionDto.builder()
                    .categoryId(matchedCategory.getId())
                    .categoryName(matchedCategory.getName())
                    .confidence(85.0)
                    .reason("AI Analysis based on description context")
                    .build();
        }

        // 2. If no match, CREATE a new category
        return createNewCategory(suggestedCategoryName, userId);

    }catch(

    Exception e)
    {
        System.err.println("AI Categorization failed: " + e.getMessage());
    }

    // Fallback if AI fails or returns invalid category
    return

    getDefaultSuggestion(categories);
    }

    private CategorizationSuggestionDto createNewCategory(String categoryName, Long userId) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            Category newCategory = new Category();
            newCategory.setName(categoryName);
            newCategory.setUser(user);
            newCategory.setType(Category.CategoryType.EXPENSE); // Default to Expense
            newCategory.setIsSystem(false);
            newCategory.setIcon("help_outline"); // Default icon
            newCategory.setColor("#808080"); // Default color

            Category savedCategory = categoryRepository.save(newCategory);

            return CategorizationSuggestionDto.builder()
                    .categoryId(savedCategory.getId())
                    .categoryName(savedCategory.getName())
                    .confidence(80.0)
                    .reason("AI suggested and created new category: " + categoryName)
                    .build();
        } catch (Exception e) {
            System.err.println("Failed to create new category: " + e.getMessage());
            return null;
        }
    }

    private Category findBestMatchingCategory(String suggestedName, List<Category> categories) {
        String normalizedSuggestion = suggestedName.toLowerCase().trim();

        // 1. Exact match (case-insensitive)
        for (Category category : categories) {
            if (category.getName().equalsIgnoreCase(normalizedSuggestion)) {
                return category;
            }
        }

        // 2. Partial match (contains)
        for (Category category : categories) {
            String normalizedCategory = category.getName().toLowerCase();
            if (normalizedCategory.contains(normalizedSuggestion)
                    || normalizedSuggestion.contains(normalizedCategory)) {
                return category;
            }
        }

        // 3. Levenshtein distance (optional, but simple contains check usually suffices
        // for this scale)
        return null;
    }

    private CategorizationSuggestionDto getDefaultSuggestion(Long userId) {
        List<Category> categories = categoryRepository.findByUserIdOrIsSystemTrue(userId);
        return getDefaultSuggestion(categories);
    }

    private CategorizationSuggestionDto getDefaultSuggestion(List<Category> categories) {
        Category defaultCategory = categories.stream()
                .filter(c -> c.getName().equals("Other Expense"))
                .findFirst()
                .orElse(null);

        if (defaultCategory != null) {
            return CategorizationSuggestionDto.builder()
                    .categoryId(defaultCategory.getId())
                    .categoryName(defaultCategory.getName())
                    .confidence(30.0)
                    .reason("No specific keywords matched, using default category")
                    .build();
        }

        return null;
    }

    public void learnFromCorrection(String description, Long categoryId, Long userId) {
        // Placeholder for future learning
    }
}

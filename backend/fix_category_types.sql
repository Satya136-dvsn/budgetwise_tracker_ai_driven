-- Fix Category Types - Mark common expense categories as EXPENSE type
-- Currently only 1 out of 24 categories is marked as EXPENSE

UPDATE categories SET category_type = 'EXPENSE' 
WHERE LOWER(name) IN (
    'food', 'groceries', 'dining', 'restaurants',
    'transport', 'transportation', 'fuel', 'gas', 'car',
    'shopping', 'clothes', 'clothing', 'fashion',
    'utilities', 'electricity', 'water', 'internet',
    'entertainment', 'movies', 'games',
    'health', 'healthcare', 'medical', 'pharmacy',
    'education', 'books', 'courses',
    'housing', 'rent', 'mortgage', 
    'insurance',
    'subscriptions', 'netflix', 'spotify',
    'personal care', 'beauty',
    'travel', 'vacation', 'hotel',
    'gifts', 'donations',
    'misc', 'miscellaneous', 'other', 'others'
);

-- Mark income-related categories as INCOME type
UPDATE categories SET category_type = 'INCOME'
WHERE LOWER(name) IN (
    'salary', 'wages', 'income', 'earnings',
    'bonus', 'freelance', 'business',
    'investment', 'interest', 'dividend',
    'savings', 'refund', 'gift received'
);

-- Check the results
SELECT category_type, COUNT(*) as count 
FROM categories 
GROUP BY category_type;

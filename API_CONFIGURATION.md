# API Configuration Guide

## Gemini API Key Setup

The BudgetWise application uses the Google Gemini API for AI-powered financial insights.

### Setup Instructions

1. **Get Your Gemini API Key:**
   - Visit [Google AI Studio](https://aistudio.google.com/app/apikey)
   - Create a new API key or use an existing one

2. **Configure the API Key:**

   **Option A: Using .env file (Development)**

   ```bash
   # Create a file: backend/src/main/resources/application-secrets.properties
   GEMINI_API_KEY=your_actual_api_key_here
   ```

   **Option B: Using Environment Variable (Production)**

   ```bash
   # Linux/Mac
   export GEMINI_API_KEY="your_actual_api_key_here"
   
   # Windows PowerShell
   $env:GEMINI_API_KEY="your_actual_api_key_here"
   
   # Windows CMD
   set GEMINI_API_KEY=your_actual_api_key_here
   ```

### Security Notes

⚠️ **NEVER commit API keys to GitHub!**

The following files are already in `.gitignore` to protect your keys:

- `backend/src/main/resources/application-secrets.properties`
- `.env` files
- Any file with `*secret*`, `*password*`, `*credentials*` in the name

### Verification

To verify your API key is configured correctly:

1. Start the backend server
2. Check logs for: `Gemini API initialized successfully`
3. Test the AI Assistant feature in the application

### Troubleshooting

**Problem:** Application won't start

- **Solution:** Make sure `GEMINI_API_KEY` environment variable is set

**Problem:** AI features don't work

- **Solution:** Verify API key is valid at [Google AI Studio](https://aistudio.google.com)

# Chart Export Issue - Summary

## Problem

PDF exports show placeholder text "(Charts available in Excel export)" instead of embedded Recharts visualizations.

## Root Cause Found

POST requests to `/api/export/analytics` and `/api/export/dashboard` return **400 Bad Request** BEFORE reaching the ExportController.

### Evidence

- Frontend successfully captures charts as Base64 images (2-3MB each)
- POST request made with images in request body
- **Backend shows ZERO logs** - request never reaches controller
- Error returned as Blob, hiding actual error message

## Likely Causes

1. **Request payload size exceeds limit** - Base64 images are very large
2. **Content-Type issue** - JSON with large strings
3. **Jackson deserialization failure** - Maybe can't handle large strings in Map
4. **Endpoint routing issue** - POST endpoints not properly registered

## Next Steps

1. Extract and log actual error message from Blob response
2. Check total payload size
3. Create simple test POST endpoint to verify routing works
4. Consider alternative: Compress images or use different transport method

# Security Best Practices Guide

## For Developers

### 1. Authentication & Authorization

#### JWT Token Handling

- **Never** log JWT tokens
- **Never** store tokens in query parameters or URLs
- Store access tokens in memory (not localStorage for XSS protection)
- Store refresh tokens in HTTP-only cookies (recommended) or localStorage
- Always validate tokens on the backend for every protected endpoint

#### Password Security

- **Always** hash passwords using BCrypt with work factor  ≥ 12
- **Never** store plaintext passwords
- Enforce password strength requirements (use `PasswordValidator`)
- Implement password history to prevent reuse
- Consider password expiration policies for high-security applications

#### Session Management

- Implement session timeouts (15 minutes for access tokens)
- Provide "logout from all devices" functionality
- Log security-critical actions (login, logout, password changes)
- Monitor for suspicious activity (multiple failed logins, unusual locations)

### 2. Rate Limiting

#### Current Implementation

- 5 requests/minute for authentication endpoints
- IP-based tracking

#### Recommendations

- Add progressive delays for repeated failures
- Consider account-based rate limiting in addition to IP-based
- For production, use Redis for distributed rate limiting
- Implement CAPTCHA after threshold breaches

### 3. Input Validation

#### Always Validate

- Email format and length
- Password strength
- User input for SQL injection prevention
- File uploads (type, size, content)

#### Use Parameterized Queries

```java
// GOOD
String query = "SELECT * FROM users WHERE email = ?";
PreparedStatement stmt = connection.prepareStatement(query);
stmt.setString(1, email);

// BAD
String query = "SELECT * FROM users WHERE email = '" + email + "'";
```

### 4. API Security

#### Endpoint Protection

- Use `@PreAuthorize` for role-based access control
- Default deny: Require authentication unless explicitly public
- Validate user ownership before allowing operations
- Rate limit all public endpoints

#### Response Security

- Never expose stack traces in production
- Return generic error messages externally
- Log detailed errors server-side only
- Implement proper CORS policies

### 5. Data Protection

#### Sensitive Data

- Encrypt sensitive data at rest
- Use TLS/HTTPS for all communications
- Implement field-level encryption for highly sensitive data
- Regularly rotate encryption keys

#### Audit Logging

- Log all security-critical operations
- Include user ID, action, timestamp, IP address
- Implement log retention policies
- Monitor logs for suspicious patterns

### 6. Frontend Security

#### XSS Prevention

- Sanitize all user inputs
- Use React's built-in XSS protection (JSX escaping)
- Set Content-Security-Policy headers
- Avoid `dangerouslySetInnerHTML` unless absolutely necessary

#### CSRF Protection

- Use CSRF tokens for state-changing operations
- Implement SameSite cookie attributes
- Validate origin headers

### 7. Dependency Management

#### Regular Updates

```bash
# Check for vulnerabilities
npm audit
mvn dependency-check:check

# Update dependencies
npm update
mvn versions:use-latest-releases
```

#### Best Practices

- Review dependencies before adding
- Use dependency scanning tools (Snyk, Dependabot)
- Keep frameworks and libraries up to date
- Monitor security advisories

### 8. Environment Configuration

#### Secrets Management

- **Never** commit secrets to version control
- Use environment variables for sensitive configuration
- Rotate secrets regularly
- Use secret management tools (HashiCorp Vault, AWS Secrets Manager)

#### Environment-Specific Settings

```properties
# Development
jwt.secret=${JWT_SECRET:dev-secret-change-me}
jwt.expiration=900000

# Production (use environment variables)
jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION:900000}
```

### 9. Testing Security

#### Security Test Checklist

- [ ] Authentication bypass attempts
- [ ] Authorization escalation tests
- [ ] SQL injection attempts
- [ ] XSS vulnerability scans
- [ ] CSRF protection verification
- [ ] Rate limiting effectiveness
- [ ] Password strength enforcement
- [ ] Token expiration handling
- [ ] Session management
- [ ] Input validation

#### Tools

- OWASP ZAP for penetration testing
- Burp Suite for security testing
- SonarQube for code security analysis
- npm audit / Snyk for dependency scanning

### 10. Incident Response

#### Preparation

1. Maintain security contact information
2. Document incident response procedures
3. Establish communication channels
4. Define severity levels

#### Response Steps

1. **Detect** - Monitor logs and alerts
2. **Contain** - Isolate affected systems
3. **Investigate** - Determine scope and cause
4. **Recover** - Restore normal operations
5. **Learn** - Document lessons learned

---

## Security Checklist for Production

### Pre-Deployment

- [ ] All dependencies updated and scanned
- [ ] Security headers configured
- [ ] Rate limiting enabled
- [ ] HTTPS enforced
- [ ] Secrets in environment variables
- [ ] Error handling returns generic messages
- [ ] Audit logging enabled
- [ ] Password policies enforced
- [ ] Session timeouts configured
- [ ] CORS properly configured

### Post-Deployment

- [ ] Monitor authentication failures
- [ ] Review audit logs regularly
- [ ] Set up alerts for suspicious activity
- [ ] Perform regular security scans
- [ ] Update dependencies promptly
- [ ] Conduct periodic security reviews

---

## Common Vulnerabilities to Avoid

### 1. SQL Injection

**Bad:**

```java
String query = "SELECT * FROM users WHERE id = " + userId;
```

**Good:**

```java
String query = "SELECT * FROM users WHERE id = ?";
PreparedStatement stmt = conn.prepareStatement(query);
stmt.setLong(1, userId);
```

### 2. Insecure Direct Object References

**Bad:**

```java
@DeleteMapping("/users/{id}")
public void deleteUser(@PathVariable Long id) {
    userService.delete(id); // Any user can delete any user!
}
```

**Good:**

```java
@DeleteMapping("/users/{id}")
public void deleteUser(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal currentUser) {
    if (!id.equals(currentUser.getId()) && !currentUser.hasRole("ADMIN")) {
        throw new ForbiddenException();
    }
    userService.delete(id);
}
```

### 3. Weak Password Storage

**Bad:**

```java
user.setPassword(password); // Plaintext!
```

**Good:**

```java
user.setPassword(passwordEncoder.encode(password)); // BCrypt with salt
```

### 4. Missing Input Validation

**Bad:**

```java
@PostMapping("/register")
public User register(@RequestBody User user) {
    return userService.save(user); // No validation!
}
```

**Good:**

```java
@PostMapping("/register")
public User register(@Valid @RequestBody RegisterRequest request) {
    PasswordValidator.ValidationResult result = PasswordValidator.validate(request.getPassword());
    if (!result.isValid()) {
        throw new ValidationException(result.getErrorMessage());
    }
    return userService.save(request);
}
```

---

## Resources

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [JWT Best Practices](https://tools.ietf.org/html/rfc8725)
- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)
- [NIST Password Guidelines](https://pages.nist.gov/800-63-3/)
- [CWE Top 25](https://cwe.mitre.org/top25/)

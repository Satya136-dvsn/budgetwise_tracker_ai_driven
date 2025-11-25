package com.budgetwise.service;

import com.budgetwise.dto.AdminStatsDto;
import com.budgetwise.entity.AuditLog;
import com.budgetwise.entity.User;
import com.budgetwise.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final AuditLogRepository auditLogRepository;
    private final BudgetRepository budgetRepository;
    private final SavingsGoalRepository savingsGoalRepository;
    private final BillRepository billRepository;
    private final InvestmentRepository investmentRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final ScheduledReportRepository scheduledReportRepository;
    private final UserProfileRepository userProfileRepository;

    public AdminStatsDto getSystemStats() {
        long totalUsers = userRepository.count();
        long totalTransactions = transactionRepository.count();
        long totalCategories = categoryRepository.count();
        long activeUsers = userRepository.countByIsActive(true);
        BigDecimal revenue = transactionRepository
                .sumTotalByType(com.budgetwise.entity.Transaction.TransactionType.INCOME);

        return AdminStatsDto.builder()
                .totalUsers(totalUsers)
                .totalTransactions(totalTransactions)
                .totalCategories(totalCategories)
                .activeUsers(activeUsers)
                .revenue(revenue != null ? revenue : BigDecimal.ZERO)
                .build();
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void logAdminAction(Long adminUserId, String actionType, Long targetUserId,
            String targetResource, String details, String ipAddress) {
        AuditLog log = AuditLog.builder()
                .adminUserId(adminUserId)
                .actionType(actionType)
                .targetUserId(targetUserId)
                .targetResource(targetResource)
                .details(details)
                .ipAddress(ipAddress)
                .build();
        auditLogRepository.save(log);
    }

    public Page<AuditLog> getAuditLogs(Pageable pageable) {
        return auditLogRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public void updateUserStatus(Long userId, Boolean isActive) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsActive(isActive);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        // 1. Handle Community Data (Posts, Comments, Likes)
        // A. Delete likes on user's comments (by others)
        List<com.budgetwise.entity.Comment> userComments = commentRepository.findByUserId(userId);
        List<Long> userCommentIds = userComments.stream().map(com.budgetwise.entity.Comment::getId).toList();
        if (!userCommentIds.isEmpty()) {
            likeRepository.deleteByCommentIdIn(userCommentIds);
        }

        // B. Delete user's comments (safe now)
        commentRepository.deleteByUserId(userId);

        // C. Delete likes and comments on user's posts (by others)
        List<com.budgetwise.entity.Post> userPosts = postRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<Long> userPostIds = userPosts.stream().map(com.budgetwise.entity.Post::getId).toList();
        if (!userPostIds.isEmpty()) {
            likeRepository.deleteByPostIdIn(userPostIds);
            commentRepository.deleteByPostIdIn(userPostIds);
        }

        // D. Delete user's posts
        postRepository.deleteByUserId(userId);

        // E. Delete user's likes (on others' posts/comments)
        likeRepository.deleteByUserId(userId);

        // 2. Handle Financial Data
        transactionRepository.deleteByUserId(userId);
        budgetRepository.deleteByUserId(userId);
        savingsGoalRepository.deleteByUserId(userId);
        billRepository.deleteByUserId(userId);
        investmentRepository.deleteByUserId(userId);

        // 3. Handle Other Data
        scheduledReportRepository.deleteByUserId(userId);
        userProfileRepository.deleteByUserId(userId);
        categoryRepository.deleteByUserId(userId); // Custom categories

        // 4. Delete User
        userRepository.deleteById(userId);
    }
}

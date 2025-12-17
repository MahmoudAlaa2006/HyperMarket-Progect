package services;

import models.*;
import utils.IDGenerator;

import java.util.ArrayList;
import java.util.List;

public class MarketingServices extends BaseServices{

    private final List<Promotion> promotions = new ArrayList<>();
    private final NotificationServices notifications = new NotificationServices();

    public void createPromotion(User user, Promotion promotion) {
        authorize(user, UserRole.MARKETING);
        promotions.add(promotion);

        notifications.send(new Notification(
                IDGenerator.generateNotificationId(),
                NotificationType.PROMOTION_ALERT,
                "New promotion created"
        ));
    }

    public List<Promotion> listAllPromotions(User user) {
        authorize(user, UserRole.MARKETING);
        return promotions;
    }

    public Report generateInventoryReport(User user, String title) {
        authorize(user, UserRole.MARKETING);
        return new Report(IDGenerator.generateReportId(), title,
                "Inventory report generated");
    }
}

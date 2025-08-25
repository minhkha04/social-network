package com.minhkha.notification.strategy;

import com.minhkha.notification.enums.Chanel;
import com.minhkha.notification.expection.AppException;
import com.minhkha.notification.expection.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NotificationStrategyFactory {
    List<NotificationStrategy> strategies;

    public NotificationStrategy getStrategy(Chanel chanel) {
        return strategies.stream()
                .filter(strategy -> strategy.getChanel().equals(chanel))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.CHANEL_NOT_EXISTED));
    }
}

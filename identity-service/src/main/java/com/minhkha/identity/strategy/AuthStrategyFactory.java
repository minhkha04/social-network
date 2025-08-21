package com.minhkha.identity.strategy;

import com.minhkha.identity.eums.AuthProvider;
import com.minhkha.identity.expection.AppException;
import com.minhkha.identity.expection.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthStrategyFactory {

    List<AuthStrategy> strategies;

    public AuthStrategy getStrategy(AuthProvider authProvider) {

        return  strategies.stream()
                .filter(strategy -> strategy.getProvider().equals(authProvider))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_AUTH_PROVIDER));
    }

}

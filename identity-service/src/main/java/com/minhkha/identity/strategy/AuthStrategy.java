package com.minhkha.identity.strategy;

import com.minhkha.identity.dto.request.AuthRequest;
import com.minhkha.identity.dto.response.AuthenticationResponse;
import com.minhkha.identity.eums.AuthProvider;

public interface AuthStrategy {
    AuthenticationResponse login(AuthRequest request);
    AuthProvider getProvider();
}

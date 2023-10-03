/*
 * Moderated meetings.
 *
 * Copyright @ 2023 - present 8x8, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jitsi.moderated;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.*;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.*;
import org.jitsi.moderated.jwt.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

import javax.annotation.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final JsonKeyProvider keyProvider = new JsonKeyProvider();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain chain)
            throws ServletException, IOException {
        String token = request.getParameter("jwt");
        DecodedJWT decodedJWT;
        if (token != null) {
            try {
                Algorithm algorithm = Algorithm.RSA256(keyProvider);

                Map<String, Object> ops = Config.getJwtVerify();

                Verification verification = JWT.require(algorithm);

                if (ops.get("issuer") != null) {
                    verification = verification.withIssuer((String)ops.get("issuer"));
                }

                if (ops.get("audience") != null) {
                    verification = verification.withAudience((String)ops.get("audience"));
                }

                decodedJWT = verification.build().verify(token);

                if (decodedJWT != null) {
                    SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(decodedJWT));
                }
            } catch (JWTVerificationException exception){
                // Invalid signature/claims
                logger.error("Invalid signature/claims: "
                        + (exception.getCause() != null ? exception.getCause().getMessage() : exception));
            }
        }

        chain.doFilter(request, response);
    }
}

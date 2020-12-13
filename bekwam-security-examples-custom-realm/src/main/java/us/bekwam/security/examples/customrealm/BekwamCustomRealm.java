package us.bekwam.security.examples.customrealm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.security.auth.SupportLevel;
import org.wildfly.security.auth.server.RealmIdentity;
import org.wildfly.security.auth.server.RealmUnavailableException;
import org.wildfly.security.auth.server.SecurityRealm;
import org.wildfly.security.authz.AuthorizationIdentity;
import org.wildfly.security.authz.MapAttributes;
import org.wildfly.security.credential.Credential;
import org.wildfly.security.evidence.Evidence;
import org.wildfly.security.evidence.PasswordGuessEvidence;

import java.security.Principal;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BekwamCustomRealm implements SecurityRealm {

    private Logger log = LoggerFactory.getLogger(BekwamCustomRealm.class);

    @Override
        public SupportLevel getCredentialAcquireSupport(Class<? extends Credential> aClass, String s, AlgorithmParameterSpec algorithmParameterSpec) throws RealmUnavailableException {
        if( log.isDebugEnabled() ) {
            log.debug("[GET CRED]");
        }
        return SupportLevel.UNSUPPORTED;
    }

    @Override
    public SupportLevel getEvidenceVerifySupport(Class<? extends Evidence> evidenceType, String s) throws RealmUnavailableException {
        if( log.isDebugEnabled() ) {
            log.debug("[GET EVID]");
        }
        return PasswordGuessEvidence.class.isAssignableFrom(evidenceType) ? SupportLevel.POSSIBLY_SUPPORTED : SupportLevel.UNSUPPORTED;
    }

    @Override
    public RealmIdentity getRealmIdentity(Principal principal) throws RealmUnavailableException {
        if( log.isDebugEnabled() ) {
            log.debug("[GET REALM] principal=" + principal.getName());
        }
        if ("myadmin".equals(principal.getName())) { // identity "myadmin" will have password "mypassword"
            return new RealmIdentity() {
                public Principal getRealmIdentityPrincipal() {
                    return principal;
                }

                public SupportLevel getCredentialAcquireSupport(Class<? extends Credential> credentialType,
                                                                String algorithmName, AlgorithmParameterSpec parameterSpec) throws RealmUnavailableException {
                    return SupportLevel.UNSUPPORTED;
                }

                public <C extends Credential> C getCredential(Class<C> credentialType) throws RealmUnavailableException {
                    return null;
                }

                public SupportLevel getEvidenceVerifySupport(Class<? extends Evidence> evidenceType, String algorithmName)
                        throws RealmUnavailableException {
                    return PasswordGuessEvidence.class.isAssignableFrom(evidenceType) ? SupportLevel.SUPPORTED : SupportLevel.UNSUPPORTED;
                }

                // evidence will be accepted if it is password "mypassword"
                public boolean verifyEvidence(Evidence evidence) throws RealmUnavailableException {
                    if (evidence instanceof PasswordGuessEvidence) {
                        PasswordGuessEvidence guess = (PasswordGuessEvidence) evidence;
                        try {
                            return Arrays.equals("mypassword".toCharArray(), guess.getGuess());

                        } finally {
                            guess.destroy();
                        }
                    }
                    return false;
                }

                public boolean exists() throws RealmUnavailableException {
                    return true;
                }

                @Override
                public AuthorizationIdentity getAuthorizationIdentity() throws RealmUnavailableException {
                    Set<String> groups = new HashSet<>();
                    groups.add("user");
                    return AuthorizationIdentity.basicIdentity(new MapAttributes(Collections.singletonMap("groups", groups)));
                }
            };
        }

        return RealmIdentity.NON_EXISTENT;
    }
}

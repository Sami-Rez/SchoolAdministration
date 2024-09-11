package htl.SchoolAdministration.service;/*

package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.persistence.TokenRepository;
import at.spengergasse.sj2324seedproject.tools.DateTimeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    private final LocalDateTime now = LocalDateTime.now();
    private TokenService tokenService;
    private  @Mock TokenRepository tokenRepository;
    private  DateTimeFactory dateTimeFactory;

    @BeforeEach
            void setup() {
        assertThat(tokenRepository).isNotNull();
        dateTimeFactory = mock(DateTimeFactory.class);
        when(dateTimeFactory.now()).thenReturn(now);
        tokenService = new TokenService(tokenRepository);
    }

}

*/

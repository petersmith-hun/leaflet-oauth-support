package hu.psprog.leaflet.oauth.frontend.filter.device;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link DeviceIDFilter}.
 *
 * @author Peter Smith
 */
@ExtendWith(MockitoExtension.class)
public class DeviceIDFilterTest {

    private static final String DEVICE_ID_HEADER = "X-Device-ID";
    private static final String CLIENT_ID_HEADER = "X-Client-ID";
    private static final UUID DEVICE_ID = UUID.randomUUID();
    private static final UUID CLIENT_ID = UUID.randomUUID();

    @Mock
    private DeviceIDGenerator deviceIDGenerator;

    @Mock
    private ServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private DeviceIDFilter deviceIDFilter;

    private MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

    @BeforeEach
    public void setup() {
        mockHttpServletRequest = new MockHttpServletRequest();
    }

    @Test
    public void shouldSetDeviceIDInRequestAndContinueFilterChain() throws IOException, ServletException {

        // given
        given(deviceIDGenerator.getID()).willReturn(DEVICE_ID);
        deviceIDFilter.setClientId(CLIENT_ID);

        // when
        deviceIDFilter.doFilter(mockHttpServletRequest, response, filterChain);

        // then
        assertThat(mockHttpServletRequest.getAttribute(DEVICE_ID_HEADER), equalTo(DEVICE_ID));
        assertThat(mockHttpServletRequest.getAttribute(CLIENT_ID_HEADER), equalTo(CLIENT_ID));
        verify(filterChain).doFilter(mockHttpServletRequest, response);
    }
}

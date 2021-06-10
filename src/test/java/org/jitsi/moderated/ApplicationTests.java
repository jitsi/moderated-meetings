package org.jitsi.moderated;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jitsi.moderated.controller.ModeratedRoomFactory;
import org.jitsi.moderated.model.JoinInfo;
import org.jitsi.moderated.model.ModeratedRoom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
class ApplicationTests {

	/**
	 * Test to validate the format of the generated URLs.
	 *
	 * @throws NoSuchAlgorithmException Thrown if JWT token generation fails.
	 * @throws MalformedURLException Thrown if any of the generated URLs are malformed.
	 */
	@Test
	void generatedDataFormat() throws NoSuchAlgorithmException, MalformedURLException {
		final ModeratedRoomFactory factory = ModeratedRoomFactory.factory();
		final ModeratedRoom moderatedRoom = factory.getModeratedRoom();
		final JoinInfo joinInfo = factory.getJoinInfo(moderatedRoom);

		// These URLs should pass common tests, so creating an array of it
		final String[] urls = {
				joinInfo.getJoinUrl(),
				joinInfo.getModeratorUrl()
		};

		for (String url: urls) {
			// URLs must be valid
			AtomicReference<URL> u = new AtomicReference<>();

			Assertions.assertDoesNotThrow(() -> {
				u.set(new URL(url));
			});

			String path = u.get().getPath();

			// A common mistake is to have double // after the host:port
			Assertions.assertFalse(path.startsWith("//"));

			// Path must have a fixed format
			Assertions.assertTrue(path.matches(new StringBuilder("/")
					.append(Config.getTargetTenant())
					.append("/")
					.append(joinInfo.getRoomName())
					.toString()));

			// Both URLs should start with the deployment URL
			Assertions.assertTrue(url.startsWith(Config.getDeploymentUrl()));
		}

		// We expect the room name and the moderated link room name segment to be of equal size
		Assertions.assertEquals(moderatedRoom.getMeetingId().length(), joinInfo.getRoomName().length());
	}

	/**
	 * Test to validate that the generated JWT contains all the right information.
	 *
	 * @throws NoSuchAlgorithmException Thrown if JWT token generation fails.
	 * @throws URISyntaxException Thrown if any of the generated URLs are malformed.
	 * @throws MalformedURLException Thrown if any of the generated URLs are malformed.
	 */
	@Test
	void jwtFormat() throws NoSuchAlgorithmException, URISyntaxException, MalformedURLException {
		final ModeratedRoomFactory factory = ModeratedRoomFactory.factory();
		final ModeratedRoom moderatedRoom = factory.getModeratedRoom();
		final JoinInfo joinInfo = factory.getJoinInfo(moderatedRoom);
		final URI moderatorURI = new URI(joinInfo.getModeratorUrl());
		String jwt = null;

		List<NameValuePair> params = URLEncodedUtils.parse(moderatorURI, Charset.defaultCharset());

		Iterator<NameValuePair> iterator = params.iterator();
		while(iterator.hasNext() && jwt == null) {
			final NameValuePair param = iterator.next();

			if ("jwt".equals(param.getName())) {
				jwt = param.getValue();
			}
		}

		// We have to have a JWT token in the moderator join URL
		Assertions.assertNotNull(jwt);

		// The token must contain all required fields
		DecodedJWT decodedJWT = JWT.decode(jwt);
		Assertions.assertEquals(decodedJWT.getIssuer(), Constants.JWT_ISSUER);
		Assertions.assertEquals(decodedJWT.getSubject(), Config.getTargetTenant());
		Assertions.assertEquals(decodedJWT.getAudience().get(0), Constants.JWT_AUDIENCE);
		Assertions.assertEquals(decodedJWT.getKeyId(), Config.getPrivateKeyId());
		Assertions.assertEquals(joinInfo.getRoomName(), decodedJWT.getClaim(Constants.JWT_CLAIM_ROOM).asString());
		Assertions.assertEquals(Config.getTargetTenant(), decodedJWT.getClaim(Constants.JWT_CLAIM_CONTEXT).asMap().get(Constants.JWT_CLAIM_CONTEXT_GROUP));
	}

}

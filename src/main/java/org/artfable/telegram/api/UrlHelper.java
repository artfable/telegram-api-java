package org.artfable.telegram.api;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/**
 * @author artfable
 *         22.01.17
 */
public class UrlHelper {
    public static URI getUri(String url, Map<String, String> urlParams, Map<String, Object> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        if (queryParams != null) {
            queryParams.forEach(builder::queryParam);
        }

        return builder.buildAndExpand(urlParams).encode().toUri();
    }

    public static URI getUri(String url, Map<String, String> urlParams) {
        return getUri(url, urlParams, null);
    }
}

package com.rta.carstore.generated.web.rest.util;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Utility class for handling pagination.
 *
 * <p>
 * Pagination uses the same principles as the <a href="https://developer.github.com/v3/#pagination">Github API</api>,
 * and follow <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link header)</a>.
 * </p>
 */
public class PaginationUtil {

    private static final String PAGE = "?page=";

    private static final String SIZE = "&size=";
    
    private PaginationUtil() {
        //const.
    }

    public static HttpHeaders generatePaginationHttpHeaders(Page<?> page, String baseUrl)
        throws URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + Long.toString(page.getTotalElements()));
        if ((page.getNumber() + 1) < page.getTotalPages()) {
            headers.add("LINK-NEXT", "<" + (new URI(baseUrl + PAGE + (page.getNumber() + 1) + SIZE + page.getSize())).toString() + ">");
        }

        if ((page.getNumber()) > 0) {
            headers.add("LINK-PREV", "<" + (new URI(baseUrl + PAGE + (page.getNumber() - 1) + SIZE + page.getSize())).toString() + ">");
        }

        headers.add("LINK-LAST", "<" + (new URI(baseUrl + PAGE + (page.getTotalPages() - 1) + SIZE + page.getSize())).toString() + ">");
        headers.add("LINK-FIRST", "<" + (new URI(baseUrl + PAGE + 0 + SIZE + page.getSize()).toString()) + ">");
        return headers;
    }
}

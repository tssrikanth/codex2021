/*
 * TSLib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */

package cloud.thoughtspotstaging.champagne.http.request;

import cloud.thoughtspotstaging.champagne.http.Headers;
import cloud.thoughtspotstaging.champagne.utilities.FileWrapper;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Class to wrap file and headers to be sent as part of a multipart request.
 */
public class MultipartFileWrapper {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FileWrapper fileWrapper;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Headers headers;

    /**
     * Initialization constructor.
     * @param fileWrapper FileWrapper instance
     * @param headers Headers for wrapping
     */
    public MultipartFileWrapper(FileWrapper fileWrapper, Headers headers) {
        this.fileWrapper = fileWrapper;
        this.headers = headers;
    }

    /**
     * Getter for file wrapper.
     * @return FileWrapper instance
     */
    public FileWrapper getFileWrapper() {
        return fileWrapper;
    }

    /**
     * Getter for headers.
     * @return Headers
     */
    public Headers getHeaders() {
        return headers;
    }
}

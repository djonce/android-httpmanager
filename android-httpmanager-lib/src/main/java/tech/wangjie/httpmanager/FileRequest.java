package tech.wangjie.httpmanager;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by wangjie on 2016/11/1 0001
 */

public class FileRequest extends HttpRequest {

    private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    private File file;

    public FileRequest(String url) {
        super(url);
    }

    public FileRequest(Request request) {
        super(request);
    }

    public FileRequest(Object apiParam) {
        super(apiParam);
    }

    public FileRequest(String url, Map<String, String> params, Map<String, String> headers) {
        super(url, params, headers);
    }

    public FileRequest(String url, Map<String, String> params, Map<String, String> headers, File file) {
        super(url, params, headers);
        this.file = file;
    }

    @Override
    protected RequestBody buildRequestBody() {

        return RequestBody.create(MEDIA_TYPE_STREAM, file);
    }
}

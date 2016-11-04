package tech.wangjie.httpmanager;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by wangjie on 2016/11/1 0001
 */

public class FileRequest extends HttpRequest {

    private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    private File file;
    private long contentLength;
    private HttpListener callback;

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

        return new ContentTypeOverridingRequestBody(RequestBody.create(MEDIA_TYPE_STREAM, file), MEDIA_TYPE_STREAM);
    }

    @Override
    protected void buildRequest(HttpListener callback) {
        this.callback = callback;
    }

    protected class ContentTypeOverridingRequestBody extends RequestBody {
        private final RequestBody delegate;
        private final MediaType contentType;

        protected CountingSink countingSink;

        ContentTypeOverridingRequestBody(RequestBody delegate, MediaType contentType) {
            this.delegate = delegate;
            this.contentType = contentType;
        }

        @Override
        public MediaType contentType() {
            return contentType;
        }

        @Override
        public long contentLength() throws IOException {
            contentLength = delegate.contentLength();
            return delegate.contentLength();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            countingSink = new CountingSink(sink);
            BufferedSink bufferedSink = Okio.buffer(countingSink);

            delegate.writeTo(bufferedSink);
            bufferedSink.flush();
        }
    }

    protected final class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;

            Log.e("HttpManager", "bytesWritten : " + bytesWritten);

            if (callback != null) {
                CallbackDelivery.get().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.inProgress(bytesWritten * 1.0f / contentLength , contentLength );
                    }
                });
            }
        }

    }

}

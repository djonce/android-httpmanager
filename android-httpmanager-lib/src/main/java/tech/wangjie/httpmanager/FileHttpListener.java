package tech.wangjie.httpmanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by wangjie on 2016/11/1 0001
 */

public abstract class FileHttpListener extends HttpListener<File> {
    private HttpConfig httpConfig;
    private String fileName;
    private String fileDir;

    public FileHttpListener() {
        this.httpConfig = HttpManager.getInstance().getDefaultConfig();
    }

    public FileHttpListener(String fileName) {
        this.fileName = fileName;
    }

    public FileHttpListener(String fileName, String fileDir) {
        this.fileName = fileName;
        this.fileDir = fileDir;
    }

    @Override
    public File parseNetworkResponse(Response response) throws Exception {
        return handleResponseToFile(response);
    }

    private File handleResponseToFile(Response response) throws IOException {
        InputStream in = null;
        FileOutputStream out = null;
        byte[] buf = new byte[2048];
        int length = 0;

        try {
//            if (fileName == null) {
//                fileName = httpConfig.getRandName();
//            }
//
//            if (fileDir == null) {
//                fileDir = httpConfig.getFileFolder();
//            }

            // 检验文件夹，及文件名称
            File dir = new File(fileDir);
            if (!dir.exists()) {
                // 创建父路径
                dir.mkdirs();
            }

            File targetFile = new File(dir, fileName);

            long sum = 0;
            final long total = response.body().contentLength();

            in = response.body().byteStream();
            out = new FileOutputStream(targetFile);
            while ((length = in.read(buf)) != -1) {
                sum += length;
                out.write(buf, 0, length);
                final long tempSum = sum;

                CallbackDelivery.get().execute(new Runnable() {
                    @Override
                    public void run() {
                        inProgress(tempSum * 1.0f / total, total);
                    }
                });
            }
            out.flush();
            return targetFile;
        } finally {
            try {
                response.body().close();
                if (in != null) in.close();
            } catch (IOException e) {
            }
            try {
                if (out != null) out.close();
            } catch (IOException e) {
            }
        }
    }
}

package com.xunmeng.pinduoduo.alive.base.ability.impl.provider;

import android.net.Uri;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.fpUtils.IStreamHandle;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.Base64Utils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.IoUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.StorageApi;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: k.class */
public class k {
    private static final String a = null;
    private static final int b = 0;
    private static final Lock c = new ReentrantLock();
    private static AtomicBoolean d = new AtomicBoolean(false);
    private static final String e = null;
    private static final String f = null;
    private static final String g = null;

    /* loaded from: k$a.class */
    public class a implements a.AnonymousClass1 {
        private Uri a;
        private String b;
        private String c;
        private String d;

        public String getUpdatedStreamMd5() {
            return this.d;
        }

        public boolean close() {
            if (this.b != null) {
                try {
                    boolean a = b.a(new File(this.b));
                    Logger.i("LVBA.AliveModule.Provider.StreamUtils", "delete temp file success: %s, %s", new Object[]{this.b, Boolean.valueOf(a)});
                    return a;
                } catch (Exception e) {
                    Logger.e("LVBA.AliveModule.Provider.StreamUtils", "exception to delete temp file: " + this.b, e);
                    com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw.h.a("deleteTempFile", e);
                    return false;
                }
            }
            return true;
        }

        public Uri getOriginStreamUri() {
            return this.a;
        }

        public String getTempStreamFilePath() {
            return this.b;
        }

        public String getOriginStreamMd5() {
            return this.c;
        }

        public void setUpdatedStreamMd5(String str) {
            this.d = str;
        }

        public a(Uri uri, String str, String str2) {
            this.a = uri;
            this.b = str;
            this.c = str2;
        }
    }

    private static boolean c(IStreamHandle iStreamHandle) {
        try {
            OutputStream openOutputStream = StrategyFramework.getFrameworkContext().getContentResolver().openOutputStream(iStreamHandle.getOriginStreamUri());
            if (null == openOutputStream) {
                Logger.i("LVBA.AliveModule.Provider.StreamUtils", "writeStream false since os is null");
                return false;
            }
            FileInputStream fileInputStream = new FileInputStream(iStreamHandle.getTempStreamFilePath());
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bArr = new byte[67108864];
            int i = 0;
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read <= 0) {
                    iStreamHandle.setUpdatedStreamMd5(a(messageDigest.digest()));
                    openOutputStream.flush();
                    openOutputStream.close();
                    fileInputStream.close();
                    Logger.i("LVBA.AliveModule.Provider.StreamUtils", "writeStream: uri: %s, total length: %s, tempFile: %s, originMd5: %s, updatedMd5: %s", new Object[]{iStreamHandle.getOriginStreamUri().getPath(), Integer.valueOf(i), iStreamHandle.getTempStreamFilePath(), iStreamHandle.getOriginStreamMd5(), iStreamHandle.getUpdatedStreamMd5()});
                    return true;
                }
                i += read;
                messageDigest.update(bArr, 0, read);
                openOutputStream.write(bArr, 0, read);
            }
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.StreamUtils", "fail to write stream", e2);
            com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw.h.a("writeStream", e2);
            IoUtils.instance().closeQuietly((Closeable) null);
            IoUtils.instance().closeQuietly((Closeable) null);
            return false;
        }
    }

    private static a b(Uri uri, String str, boolean z, boolean z2) {
        FileOutputStream fileOutputStream = null;
        MessageDigest messageDigest = null;
        String str2 = null;
        try {
            InputStream openInputStream = StorageApi.instance().openInputStream(StrategyFramework.getFrameworkContext().getContentResolver(), uri);
            if (openInputStream == null) {
                throw new RuntimeException("null input stream");
            }
            if (z) {
                if (str == null) {
                    String str3 = StorageApi.instance().getFilesDir().getAbsoluteFile() + "/fp4237";
                    File file = new File(str3);
                    if (!file.exists()) {
                        Logger.i("LVBA.AliveModule.Provider.StreamUtils", "tempDirPath not exist, makeDir: %s", new Object[]{str3});
                        file.mkdir();
                    } else if (Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_fp_delete_cache_file_59400", "false"))) {
                        b(str3);
                    }
                    str = str3 + "/" + UUID.randomUUID().toString() + "_" + c(uri);
                }
                fileOutputStream = new FileOutputStream(str);
            }
            if (z2) {
                messageDigest = MessageDigest.getInstance("MD5");
            }
            byte[] bArr = new byte[67108864];
            int i = 0;
            while (true) {
                int read = openInputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                i += read;
                if (z2) {
                    messageDigest.update(bArr, 0, read);
                }
                if (z) {
                    fileOutputStream.write(bArr, 0, read);
                }
            }
            if (z2) {
                str2 = a(messageDigest.digest());
            }
            Logger.i("LVBA.AliveModule.Provider.StreamUtils", "openStream: uri: %s, total length: %s, md5: %s, tempFile: %s", new Object[]{uri.getPath(), Integer.valueOf(i), str2, str});
            if (z) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            openInputStream.close();
            return new a(uri, str, str2);
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.StreamUtils", "fail to openStream", e2);
            com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw.h.a("openStream", e2);
            IoUtils.instance().closeQuietly((Closeable) null);
            IoUtils.instance().closeQuietly((Closeable) null);
            throw e2;
        }
    }

    private static String c(Uri uri) {
        String encode;
        String path = uri.getPath();
        if (path == null) {
            return "";
        }
        String[] split = path.split("/");
        return (split.length == 0 || (encode = Base64Utils.instance().encode(split[split.length - 1].getBytes())) == null) ? "" : encode;
    }

    public static String c(String str) {
        String[] split = str.split("_");
        return split.length <= 1 ? "" : split[split.length - 1];
    }

    public static boolean b(IStreamHandle iStreamHandle) {
        a a2 = a(iStreamHandle.getOriginStreamUri(), false, true);
        if (a2 == null) {
            Logger.i("LVBA.AliveModule.Provider.StreamUtils", "fail to get origin stream");
            return false;
        } else if (!TextUtils.equals(a2.getOriginStreamMd5(), iStreamHandle.getOriginStreamMd5())) {
            Logger.i("LVBA.AliveModule.Provider.StreamUtils", "origin stream md5 changed from %s to %s", new Object[]{a2.getOriginStreamMd5(), iStreamHandle.getOriginStreamMd5()});
            return false;
        } else {
            boolean c2 = c(iStreamHandle);
            Logger.i("LVBA.AliveModule.Provider.StreamUtils", "writeSuccess: %s, updatedMd5: %s", new Object[]{Boolean.valueOf(c2), iStreamHandle.getUpdatedStreamMd5()});
            if (!c2 || TextUtils.isEmpty(iStreamHandle.getUpdatedStreamMd5())) {
                return false;
            }
            for (int i = 0; i < 3; i++) {
                a a3 = a(iStreamHandle.getOriginStreamUri(), false, true);
                if (a3 != null) {
                    Logger.i("LVBA.AliveModule.Provider.StreamUtils", "reCheckStream md5: %s, expectedMd5: %s", new Object[]{a3.getOriginStreamMd5(), iStreamHandle.getUpdatedStreamMd5()});
                    if (TextUtils.equals(a3.getOriginStreamMd5(), iStreamHandle.getUpdatedStreamMd5())) {
                        Logger.i("LVBA.AliveModule.Provider.StreamUtils", "reCheckStream success");
                        return true;
                    }
                    Logger.i("LVBA.AliveModule.Provider.StreamUtils", "write stream retry: %d", new Object[]{Integer.valueOf(i)});
                    c(iStreamHandle);
                }
            }
            return false;
        }
    }

    private static String a(byte[] bArr) {
        return String.format("%32s", new BigInteger(1, bArr).toString(16)).replace(' ', '0');
    }

    public static boolean a(IStreamHandle iStreamHandle) {
        c.lock();
        try {
            return b(iStreamHandle);
        } finally {
            c.unlock();
        }
    }

    public static a b(Uri uri) {
        return a(uri, true, true);
    }

    public static a a(Uri uri) {
        return b(uri, null, true, true);
    }

    public static a a(a aVar, String str) {
        return a(Uri.parse(aVar.a.toString() + str).buildUpon().build(), aVar.getTempStreamFilePath() + str, true, true);
    }

    public static a a(Uri uri, boolean z, boolean z2) {
        return a(uri, null, z, z2);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.provider.k$1, java.lang.Runnable] */
    private static void b(final String str) {
        ThreadPool.instance().ioTask(ThreadBiz.CS, "StreamUtils#deleteCacheFile", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.provider.k.1
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                try {
                    if (!k.d.compareAndSet(false, true)) {
                        Logger.i("LVBA.AliveModule.Provider.StreamUtils", "busy handling, return");
                        return;
                    }
                    File file = new File(str);
                    long parseLong = NumberUtils.instance().parseLong(RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_fp_file_cache_time_in_min_59400", "5"), 0L) * 0 * 0;
                    File[] listFiles = file.listFiles();
                    if (listFiles != null && listFiles.length != 0) {
                        int i = 0;
                        long j = 0;
                        HashSet hashSet = new HashSet();
                        StringBuilder sb = new StringBuilder();
                        for (File file2 : listFiles) {
                            if (System.currentTimeMillis() - file2.lastModified() > parseLong) {
                                Logger.i("LVBA.AliveModule.Provider.StreamUtils", "file : %s, current time is %s, last modified time is %s", new Object[]{file2.getName(), Long.valueOf(System.currentTimeMillis()), Long.valueOf(file2.lastModified())});
                                sb.append(file2.getName()).append("\n");
                                j += file2.length();
                                i++;
                                String c2 = k.c(file2.getName());
                                if (!TextUtils.isEmpty(c2) && !hashSet.contains(c2)) {
                                    hashSet.add(c2);
                                }
                                b.a(file2);
                            }
                        }
                        Logger.i("LVBA.AliveModule.Provider.StreamUtils", "delete files count : %s, total size : %s", new Object[]{Integer.valueOf(i), Long.valueOf(j)});
                        if (i != 0 && Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_track_delete_fp_cache_file_59600", "false"))) {
                            HashMap hashMap = new HashMap();
                            hashMap.put("file_size", String.valueOf(j));
                            hashMap.put("file_count", String.valueOf(i));
                            hashMap.put("file_list", sb.toString());
                            if (!hashSet.isEmpty()) {
                                hashMap.put("file_type", hashSet.toString());
                            }
                            j.a("StreamUtils", "StreamUtils", (Map) hashMap, true, true);
                        }
                    }
                } catch (Exception e2) {
                    Logger.e("LVBA.AliveModule.Provider.StreamUtils", "failed to delete cache file", e2);
                } finally {
                    Logger.i("LVBA.AliveModule.Provider.StreamUtils", "reset handling event status");
                    k.d.set(false);
                }
            }
        });
    }

    private static a a(Uri uri, String str, boolean z, boolean z2) {
        try {
            return b(uri, str, z, z2);
        } catch (Exception e2) {
            return null;
        }
    }
}

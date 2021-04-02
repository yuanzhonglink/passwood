package com.pass.util.inetwork;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class KeyUtil {
    static String s = "/u3+7QAAAAIAAAABAAAAAgAOY2VydGlmaWNhdGVrZXkAAAFfdla9vQAFWC41MDkAAAOMMIIDiDCCAnACCQDRVhq5hsm8hDANBgkqhkiG9w0BAQUFADCBhTELMAkGA1UEBhMCQ04xETAPBgNVBAgMCFNIQU5HSEFJMREwDwYDVQQHDAhTSEFOR0hBSTEQMA4GA1UECgwHSEVOR1FJTjEQMA4GA1UECwwHSEVOR1FJTjEWMBQGA1UEAwwNKi5lLWhxaW5zLmNvbTEUMBIGCSqGSIb3DQEJARYFYWRtaW4wHhcNMTcwNjEzMDkwNzAwWhcNMjAwMzA5MDkwNzAwWjCBhTELMAkGA1UEBhMCQ04xETAPBgNVBAgMCFNIQU5HSEFJMREwDwYDVQQHDAhTSEFOR0hBSTEQMA4GA1UECgwHSEVOR1FJTjEQMA4GA1UECwwHSEVOR1FJTjEWMBQGA1UEAwwNKi5lLWhxaW5zLmNvbTEUMBIGCSqGSIb3DQEJARYFYWRtaW4wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCr6p3Id3wY0wf4JEnVOeczuFbrHOYuL+j/NHqJAnw5a5mnIwnbcazbCj3UobjS8e2QxCPE7SBAy+fAD5UBRB0+c2m5sOFihIHdWa1qv4QBVQ39Jas37LWTdBdkrBpD+7sQOjygUANA6m+Zqjbn+538BxTQ289tH6stb/M2q55AFDP6dL1JYkR4UtxcXGvyOoBS1zPu35kZUs2sI/7bsGUbSXZp7b1NzrIlAPngVi3TTCPz+nc6/sDXpHdjIj19Vn9K5cJNNcgsWhY1tca/Vmd1rbpiMH3qA9yPZrYJ76M0A7V4QPHTnH8kzNoTAV7jQ4y0H/wa9jfFvm2JeLJvU9AjAgMBAAEwDQYJKoZIhvcNAQEFBQADggEBAGkN8YZtsWMtrSWtubLs5AyIXKgqW+VESzEiye0KqGOTlDffkWjtfoPoKZmeVe6sIe27mcr0P6OR92k+K/4yMfHGwdr+DFYx67+mp7XrdFdzedTYBbuvb2L4YbRPr/aWtOUF9aTrCbEoDpdR6w18nAGC6Z296AJIE9X0n8JQJ4XfGqdwqfTTUwyyc1ONe16lyXc6tpgrNCyCkjOHY3bP4kgwWelP+ioVuFRabO38Ixs+Fqn/KqEC3K3WJdl7jFT1XixZGPIaNz915EaSWCY8iRMGP3+gkx/DBwwDkIWLk2HF9QRttomD0gpmWF+Wnv+WJsAYBTP1N4YQ66lqgxaWoFqUn047oo9U5W00OKS1ONlFQLt9Ag==";

    public KeyUtil() {
    }

    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return (new BASE64Encoder()).encode(buffer);
    }

    public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
        byte[] buffer = (new BASE64Decoder()).decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    public static File getJks() throws Exception {
        File file = new File("./hqtruststore.jks");
        byte[] buffer = (new BASE64Decoder()).decodeBuffer(s);
        FileOutputStream out = new FileOutputStream(file);
        out.write(buffer);
        out.close();
        return file;
    }
}

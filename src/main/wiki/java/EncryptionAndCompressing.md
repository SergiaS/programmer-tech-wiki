# Encryption
> Алгоритм «AES» является сокращением от «AES/ECB/NoPadding».<br>
> AES ([Advanced Encryption Standard](https://en.wikipedia.org/wiki/Advanced_Encryption_Standard)) algorithm used 128-bit key size and [block size](https://en.wikipedia.org/wiki/Block_cipher), with the ECB mode of operation and no padding

> `CipherOutputStream`, `doFinal()` should only be used on the final block you encrypt. Use `update()` until the last block.

## Пример кодирования и сжатия данных через WebSockets в файл
```java
private final static String baseEndpoint = "wss://stream.binance.com:9443/ws";
private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
private static final String ALGORITHM = "AES";
private static final byte[] KEY_VALUE = new byte[]{'0','2','3','4','5','6','7','9','8','2','3','4','6','7','9','8'};

public static void main(String[] args) {
    String filename = dtf.format(LocalDateTime.now()) + ".txt";
    Key key = new SecretKeySpec(KEY_VALUE, ALGORITHM);
    Cipher cipher = null;
    try {
        cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
        e.printStackTrace();
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
         BufferedWriter writer2 = new BufferedWriter(new FileWriter(dtf.format(LocalDateTime.now()) + "_2" + ".txt"));
         CipherOutputStream cipherOut = new CipherOutputStream(new FileOutputStream(filename), cipher);
         GZIPOutputStream gzip = new GZIPOutputStream(cipherOut);
        ) {
        WebSocketClientEndpoint clientEndpoint = new WebSocketClientEndpoint(new URI(baseEndpoint));
        clientEndpoint.addMessageHandler(new WebSocketClientEndpoint.MessageHandler() {
            @Override
            public void handleMessage(String message) {
                try {
                    gzip.write((message + "\n").getBytes());
                    
                    writer2.write(message + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        String json = "{\n" +
                "\"method\": \"SUBSCRIBE\",\n" +
                "\"params\":\n" +
                "[\n" +
                "\"btcusdt@aggTrade\"\n" +
                "],\n" +
                "\"id\": 1\n" +
                "}";
        clientEndpoint.sendMessage(json);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    } catch (URISyntaxException | IOException e) {
        e.printStackTrace();
    }
}
```

## Пример чтения зашифрованного и сжатого файла
```java
private static final String ALGORITHM = "AES";
private static final byte[] KEY_VALUE = new byte[]{'0','2','3','4','5','6','7','9','8','2','3','4','6','7','9','8'};

private static void decompressAndDecrypt(String filename) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
    Key key = new SecretKeySpec(KEY_VALUE, ALGORITHM);
    Cipher cipherInstance = Cipher.getInstance(ALGORITHM);
    cipherInstance.init(Cipher.DECRYPT_MODE, key);
    try (FileInputStream fis = new FileInputStream(filename);
         CipherInputStream cis = new CipherInputStream(fis, cipherInstance);
         GZIPInputStream gis = new GZIPInputStream(cis);
    ) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8));
        StringBuilder res = new StringBuilder();
        String sTmp;
        while ((sTmp = bufferedReader.readLine()) != null) {
            res.append(sTmp).append("\n");
        }
        System.out.println(res);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

# Compressing

## GZIP Error
> __Java: Error creating a GZIPInputStream: Not in GZIP format__
> 
> Mixing String and byte[]; that does never fit. And only works on the the same OS with same encoding. Not every byte[] can be converted to a String, and the conversion back could give other bytes.
> > Source: [stackoverflow](https://stackoverflow.com/a/14467099) 

## GZIP examples
### Examples with a files
```java
// Compress example:
private static void gzipCompress(String filename) {
    try (FileInputStream fis = new FileInputStream(filename);
         FileOutputStream fos = new FileOutputStream("comp_gzip.txt");
         GZIPOutputStream gos = new GZIPOutputStream(fos);) {

        byte[] buffer = new byte[1024];
        for (int len; (len = fis.read(buffer)) != -1; ) {
            gos.write(buffer, 0, len);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```
```java
// Decompress example:
private static void gzipDecompress(String filename) {
    try (FileInputStream fis = new FileInputStream(filename);
         GZIPInputStream gis = new GZIPInputStream(fis);
         FileOutputStream fos = new FileOutputStream("uncomp_gzip.txt");) {

        byte[] bytes = new byte[1024];
        for (int len; (len = gis.read(bytes)) > 0; ) {
            fos.write(bytes, 0, len);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

### Пример чтения построчно и побайтово
```java
// построчно
private static void decompressAndDecrypt(String filename) {
    try (FileInputStream fis = new FileInputStream(filename);
         CipherInputStream cis = decryptStream(fis);
         GZIPInputStream gis = decompressStream(cis);
    ) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis));
        StringBuilder res = new StringBuilder();
        String sTmp;
        while ((sTmp = bufferedReader.readLine()) != null) {
            res.append(sTmp).append("\n");
        }
        System.out.println(res);
    } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
        e.printStackTrace();
    }
}
```
```java
// побайтово
private static void decompressAndDecrypt(String filename) {
    try (FileInputStream fis = new FileInputStream(filename);
         CipherInputStream cis = decryptStream(fis);
         GZIPInputStream gis = decompressStream(cis);
    ) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = gis.read(buffer)) > 0) {
            baos.write(buffer, 0, len);
        }
    } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
        e.printStackTrace();
    }
}
```

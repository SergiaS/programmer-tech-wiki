# SMTP

* [Как использовать бесплатный SMTP Сервер от Google](https://www.hostinger.ru/rukovodstva/kak-ispolzovat-smtp-server)
* [Ограничения в Google Workspace на отправку электронных писем из Gmail](https://support.google.com/a/answer/166852?hl=ru)


## Отправка email ukr.net с использованием сервера SMTP на JAVA 

### Настройка ukr.net
1. Нужно иметь почту `ukr.net`.
2. В целях безопасности лучше создать пароль для сторонних приложений:
   [Керування IMAP-доступом](https://mail.ukr.net/desktop#security/appPasswords)
   
3. Все настройки по `ukr.net` доступны [здесь](https://wiki.ukr.net/ManageIMAPAccess)


### Код
Сам код основан на статье [How To Send Email In Java Using Gmail SMTP?](https://netcorecloud.com/tutorials/send-email-in-java-using-gmail-smtp/).

Для простоты используется один почтовый ящик, т.е. с него мы будет отправлять сообщения, и он же будет их принимать.
Основные настройки находятся в файле `config.properties`.
```properties
# config.properties
mail.send.login=bobsinger@ukr.net
mail.send.password=CvTI7ZZi2m2KFf1
mail.send.to=bobsinger@ukr.net
mail.send.from=bobsinger@ukr.net

mail.smtp.host=smtp.ukr.net
mail.smtp.port=465
mail.smtp.ssl.enable=true
mail.smtp.auth=true
```

В коде на Java, загружаем эти свойства:
```java
private static final Properties properties = new Properties();
static {
    try(InputStream input = new FileInputStream("config.properties")) {
        properties.load(input);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

Приложение простое. Оно может отправлять простой текст, html-текст или простой текст с вложением. Остальной код:
```java
public static void main(String[] args) {
    // Get the Session object.// and pass username and password
    Session session = Session.getInstance(properties, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(
                    properties.getProperty("mail.send.login"),
                    properties.getProperty("mail.send.password"));
        }
    });

    // Used to debug SMTP issues
    session.setDebug(true);

    // Recipient's email ID needs to be mentioned.
    String to = properties.getProperty("mail.send.to");

    // Sender's email ID needs to be mentioned
    String from = properties.getProperty("mail.send.from");

    try {
        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(from));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // Set Subject: header field
        message.setSubject("This is the Subject Line!");

        // Now set the actual message
//            addTextToMessage(message, "This is actual message");
//            addHTMLToMessage(message, "<h1>This is actual message embedded in HTML tags</h1>. Hello <b>Bob</b>!");
        addAttachment(message, "This is actual message", "README.md");

        System.out.println("sending...");
        // Send message
        Transport.send(message);
        System.out.println("Sent message successfully....");
    } catch (MessagingException mex) {
        mex.printStackTrace();
    }

}

private static void addTextToMessage(MimeMessage message, String text) throws MessagingException {
    message.setText(text);
}

private static void addHTMLToMessage(MimeMessage message, String html) throws MessagingException {
    message.setContent(html, "text/html");
}

private static void addAttachment(MimeMessage message, String text, String filePath) throws MessagingException {
    Multipart multipart = new MimeMultipart();
    MimeBodyPart attachmentPart = new MimeBodyPart();
    MimeBodyPart textPart = new MimeBodyPart();

    try {
        File f = new File(filePath);
        attachmentPart.attachFile(f);
        textPart.setText(text);
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(attachmentPart);
    } catch (IOException | MessagingException e) {
        e.printStackTrace();
    }

    message.setContent(multipart);
}
```

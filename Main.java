import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
class Elit {
// Elit üyeler bu classta kaydediliyor.
    private String ad;
    private String soyad;
    private String mail;
    public Elit(String ad, String soyad, String mail) {
        this.ad = ad;
        this.soyad = soyad;
        this.mail = mail;
    }
    public String getAd() {
        return ad;
    }

    public String getSoyad() {
        return soyad;
    }


    public String getMail() {
        return mail;
    }

}
class Genel {
    // Genel üyeler bu classta kaydediliyor.

    private String ad;
    private String soyad;
    private String mail;
    public Genel(String ad, String soyad, String mail) {
        this.ad = ad;
        this.soyad = soyad;
        this.mail = mail;
    }
    public String getAd() {
        return ad;
    }


    public String getSoyad() {
        return soyad;
    }


    public String getMail() {
        return mail;
    }

}

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("Kullanıcılar.txt");
        file.createNewFile();

        FileWriter fWriter = new FileWriter(file, false);
        FileWriter fWriter2 = new FileWriter(file, true);

        BufferedWriter bWriter = new BufferedWriter(fWriter);
        BufferedWriter bWriter2 = new BufferedWriter(fWriter2);
        String ad, soyad, mail;
        Elit[] elits = new Elit[5];
        Genel[] genels = new Genel[5];
        Scanner scan = new Scanner(System.in);
        int secim, secim2 = 0, i = 0, j = 0, genel_sayi = 0, elit_sayi = 0;

        do {
            System.out.println("1.Elit Üye Ekleme");
            System.out.println("2.Genel Üye Ekleme");
            System.out.println("3.Mail Gönderme");
            secim = scan.nextInt();
            switch (secim) {
                case 1:
                    System.out.println("İsim girin:");
                    ad = scan.next();
                    System.out.println("Soyisim girin:");
                    soyad = scan.next();
                    System.out.println("Mail girin:");
                    mail = scan.next();
                    elits[i] = new Elit(ad, soyad, mail);
                    i++;
                    elit_sayi++;
                    break;

                case 2:
                    System.out.println("İsim girin:");
                    ad = scan.next();
                    System.out.println("Soyisim girin:");
                    soyad = scan.next();
                    System.out.println("Mail girin:");
                    mail = scan.next();
                    genels[j] = new Genel(ad, soyad, mail);
                    j++;
                    genel_sayi++;
                    break;

                case 3:
                    System.out.println("1.Elit Üyelere Mail");
                    System.out.println("2.Genel Üyelere Mail");
                    System.out.println("3.Herkese Mail");
                    secim2 = scan.nextInt();
                    break;

                default:
                    System.out.println("Hatalı giriş yaptınız.");
            }
        }
        while (secim != 3);
        bWriter.write("Elit Üyeler" + "\n");
        for (i = 0; i < elit_sayi; i++) {
            bWriter.write(elits[i].getAd() + "\t");
            bWriter.write(elits[i].getSoyad() + "\t");
            bWriter.write(elits[i].getMail());
            bWriter.newLine();
        }
        bWriter.newLine();
        bWriter.close();

        bWriter2.write("Genel Üyeler" + "\n");
        for (j = 0; j < genel_sayi; j++) {
            bWriter2.write(genels[j].getAd() + "\t");
            bWriter2.write(genels[j].getSoyad() + "\t");
            bWriter2.write(genels[j].getMail());
            bWriter2.newLine();
        }
        bWriter2.close();
        Main nesne = new Main();
        System.out.println("Mailinizi girin(Outlook):");
        String email = scan.next();
        scan.nextLine();
        System.out.println("Şifrenizi girin:");
        String password = scan.next();
        scan.nextLine();
        System.out.println("Mailin başlığı ne olsun:");
        String header = scan.nextLine();
        System.out.println("Mesajınız nedir:");
        String message = scan.nextLine();
        if (secim2 == 1) {
            for (i = 0; i < elit_sayi; i++) {
                nesne.mailGonder(email, password, elits[i].getMail(), header, message);
            }
        }
        if(secim2 == 2) {
            for (i = 0; i < genel_sayi; i++) {
                nesne.mailGonder(email, password, genels[i].getMail(), header, message);
            }
        }
        if(secim==3){
            if(elit_sayi==genel_sayi) {
                for (i = 0; i < elit_sayi; i++) {
                    nesne.mailGonder(email, password, elits[i].getMail(), header, message);
                    nesne.mailGonder(email, password, genels[i].getMail(), header, message);
                }
            }
            else{
                for (i = 0; i < elit_sayi; i++) {
                    nesne.mailGonder(email, password, elits[i].getMail(), header, message);
                }
                for (i = 0; i < genel_sayi; i++) {
                    nesne.mailGonder(email, password, genels[i].getMail(), header, message);
                }

            }
        }
        System.out.println("Mailler başarıyla gönderildi.");
        }
    public  void mailGonder(String gonderen, String sifre, String alici, String baslik, String mesaj) {
        // Gmailde sorun olduğu için outlook hostu, portu ve protokolu ayarlanmıştır.
        // Gönderilecek kişinin maili
        String to = alici;

        // Gönderecek kişinin maili
        String from = gonderen;

        // Outlook host serveri
        String host = "smtp.office365.com";

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // mail ve şifreyle giriş yapılıyor
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(gonderen, sifre);

            }

        });

        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(baslik);

            message.setText(mesaj);

            System.out.println("Gönderiliyor...");
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
        }






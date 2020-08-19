import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
interface IUpdateTime{
    void changeDate(String date);
    void changeTime(String time);
    String getDateObject();
    Date parseDateObject();
    String lastTime(Date parse);
}

@SuppressWarnings("BusyWait")
public class UpdateTime implements IUpdateTime{

    public void changeDate(String date){
        try {
            Runtime.getRuntime().exec("cmd /c date " + date);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeTime(String time){
        try {
            Runtime.getRuntime().exec("cmd /c time " + time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDateObject(){
        URLConnection urlConnection = null;
        try {
            urlConnection = new URL("http://www.baidu.com").openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String date = urlConnection != null ? urlConnection.getHeaderField("Date") : null;
        int count = 0;
        while (date == null && count < 10) {
//            System.out.println("获取不到date数据，正在重试中……");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            date = urlConnection != null ? urlConnection.getHeaderField("Date") : null;
        }
        return date;
    }

    public Date parseDateObject(){
        String date = getDateObject();

        SimpleDateFormat format = new SimpleDateFormat("EEE, ddd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("........");
    }

    public String lastTime(Date parse) {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        return time.format(parse);
    }

    private String lastDate(Date parse) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        return time.format(parse);
    }

    public static void main(String[] args) throws Exception {
        Runtime.getRuntime().exec("cmd /c start /wait taskkill /F /IM explorer.exe & explorer");
        int count=0;
        UpdateTime updateTime = new UpdateTime();
        while (updateTime.getDateObject()==null&&count<20){
            Thread.sleep(2000);
            count++;
        }
        Date dateObject = updateTime.parseDateObject();
        String date = updateTime.lastDate(dateObject);
        String time = updateTime.lastTime(dateObject);
        updateTime.changeTime(time);
        Thread.sleep(20);
        updateTime.changeDate(date);

    }
}

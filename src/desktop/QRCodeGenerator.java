// https://www.callicoder.com/generate-qr-code-in-java-using-zxing/
package desktop;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.net.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Enumeration;

public class QRCodeGenerator {

    private static final String QR_CODE_IMAGE_PATH = "./qr.png";
    private static final String PORT = "6666";

    private static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
    // https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
    private static String getServerIP(String networkID) {
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while(networkInterfaces.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration addresses = n.getInetAddresses();
                while (addresses.hasMoreElements())
                {
                    InetAddress i = (InetAddress) addresses.nextElement();

                    String adr = i.getHostAddress();

                    // Chercher l'ip correspondant au réseau
                    // TODO : Adapter pour des réseaux avec un masque pas "rond", peut-être une range ?
                    if(adr.substring(0, networkID.length()).equals(networkID)) {
                        return adr;
                    }
                }
            }
            return null;
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        try {
            generateQRCodeImage(getServerIP("10.192.93") + ":" + PORT, 350, 350, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }
}

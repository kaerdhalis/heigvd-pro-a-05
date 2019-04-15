// https://www.callicoder.com/generate-qr-code-in-java-using-zxing/

/* How to use
    try {
        generateQRCodeImage(getServerIPs() + "\nPort\n" + PORT, 350, 350, QR_CODE_IMAGE_PATH);
    } catch (WriterException e) {
        System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
    } catch (IOException e) {
        System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
    }
*/

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
    private static String getServerIPs() {
        String serverIPs = "IPs\n";
        // TODO : Enlever les IPs spéciales (APIPA, Loopback ...) et IPv6
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while(networkInterfaces.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration addresses = n.getInetAddresses();
                while (addresses.hasMoreElements())
                {
                    InetAddress i = (InetAddress) addresses.nextElement();
                    serverIPs += i.getHostAddress() + "\n";
                }
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        return serverIPs;
    }
}
